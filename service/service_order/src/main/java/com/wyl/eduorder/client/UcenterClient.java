package com.wyl.eduorder.client;

import com.wyl.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @PostMapping("/educenter/member/getUserInfoById/{id}")
    public UcenterMemberOrder getUserInfoById(@PathVariable("id") String id);
}
