package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ruController")
public class ResourcesUploadController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${fasthome}")
    private String dfsHome;

    @RequestMapping("/uploadPng")
    public String uploadPng(MultipartFile file){
        System.out.println(file);
        System.out.println(file.getOriginalFilename());

        try {
            String fileExtName = FilenameUtils.getExtension(file.getOriginalFilename());
            // 把文件上传到FastDFS上面
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), fileExtName, null);

            // 获取文件上传的路径
            String fullPath = storePath.getFullPath();
            return  dfsHome+fullPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
