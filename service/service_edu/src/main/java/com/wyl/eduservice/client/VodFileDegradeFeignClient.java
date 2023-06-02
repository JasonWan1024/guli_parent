package com.wyl.eduservice.client;

import com.wyl.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-04-13 09:11
 **/
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R deleteVodVideo(String videoId) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
