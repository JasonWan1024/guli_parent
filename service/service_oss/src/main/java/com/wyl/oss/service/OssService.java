package com.wyl.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: guli_parent
 * @description:
 * @author: wyl
 * @create: 2023-03-25 16:06
 **/
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
