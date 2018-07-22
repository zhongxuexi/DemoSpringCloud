package com.jess.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/7/22.
 */
public interface FileUploadService {

    Map<String,Object> upload(MultipartFile file);

}
