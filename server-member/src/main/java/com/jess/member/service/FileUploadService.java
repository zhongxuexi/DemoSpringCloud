package com.jess.member.service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by zhongxuexi on 2018/7/22.
 */
public interface FileUploadService {

    Map<String,Object> upload(MultipartFile file);

}
