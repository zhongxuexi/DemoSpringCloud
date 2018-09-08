package com.jess.member.service.impl;
import com.google.common.collect.Maps;
import com.jess.common.util.DateUtil;
import com.jess.member.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/7/22.
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${web.upload-path}")
    private String path;
    @Value("${web.upload-url}")
    private String url;
    @Override
    public Map<String, Object> upload(MultipartFile file) {
        // 自定义的文件名称
        String trueFileName = file.getOriginalFilename();// 文件原名称;
        String dbName = "/"+ DateUtil.getDays()+"/"+trueFileName;
        try {
            File localFile = new File(path,dbName);
            if (!localFile.getParentFile().exists()) { //如果不存在 则创建
                //先创建目录，在创建文件
                localFile.getParentFile().mkdirs();
                //创建文件
                localFile.createNewFile();
                System.out.println(localFile.exists());
            }
            file.transferTo(localFile);// 转存文件
            //数据库存储的相对路径
            Map<String, Object> map = Maps.newHashMap();
            map.put("fileName",trueFileName);
            map.put("path",dbName);
            map.put("url",url+dbName);
            return map;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
