package com.wyl.vodtest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @program: guli_parent
 * @description: 测试vod
 * @author: wyl
 * @create: 2023-04-10 08:36
 **/
public class TestVod {

    public static void main(String[] args) throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tGewMjMoCGU44GWbApf", "koVNyNDqpqIzp319K7mFJ08R0oIX1B");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("fe0c3000d73a71ed80667035d0b20102");
        response = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
    }

    public static void getVideoUrl() throws ClientException {
        //通过视频ID获取视频地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tGewMjMoCGU44GWbApf", "koVNyNDqpqIzp319K7mFJ08R0oIX1B");
        //创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //request中设置ID
        request.setVideoId("fe0c3000d73a71ed80667035d0b20102");
        //调用初始化方法 传递对象 获取数据
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
