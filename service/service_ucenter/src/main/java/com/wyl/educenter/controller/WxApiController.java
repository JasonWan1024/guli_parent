package com.wyl.educenter.controller;

import com.google.gson.Gson;
import com.wyl.commonutils.JwtUtils;
import com.wyl.educenter.entity.UcenterMember;
import com.wyl.educenter.service.UcenterMemberService;
import com.wyl.educenter.utils.ConstantWxUtils;
import com.wyl.educenter.utils.HttpClientUtils;
import com.wyl.servicebase.exceptionhandler.GuliException;
import lombok.SneakyThrows;
import org.apache.http.annotation.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-27 08:25
 **/
@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("/login")
    public String getWxQrCode() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (
                UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");
        return "redirect:" + qrcodeUrl;
    }

    @SneakyThrows
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
//            System.out.println("accessTokenInfo = " + accessTokenInfo);

            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            UcenterMember member = memberService.getOpenidMember(openid);
            if (member == null) {
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
//                System.out.println("userInfo = " + userInfo);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + token;
        } catch (Exception e) {
            throw new GuliException(20001, "登录失败");
        }
    }

}
