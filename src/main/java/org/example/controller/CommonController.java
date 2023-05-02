package org.example.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.common.CustomException;
import org.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Maplerain
 * @date 2023/4/16 23:55
 **/
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${default-value.file-path}")
    private String basePath;

    /**
     * 文件上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        log.info(file.toString());

        String fileName = file.getOriginalFilename();

        int pos = fileName.lastIndexOf(".");
        if (pos == -1) {
            fileName = UUID.randomUUID() + "";
        } else {
            fileName = UUID.randomUUID() + fileName.substring(pos);
        }

        File f = new File(basePath + "\\" + fileName);
        File baseDir = new File(basePath);

        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        try {
            file.transferTo(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.success(f.getName());
    }

    /**
     * 文件下载接口
     *
     * @param name
     * @return
     */
    @GetMapping("/download")
    public Result download(String name, HttpServletResponse response) {
        try {
            FileInputStream fis = new FileInputStream(basePath + "\\" + name);
            ServletOutputStream ops = response.getOutputStream();
            byte[] bytes = new byte[1024];
            while (fis.read(bytes) != -1) {
                ops.write(bytes);
                ops.flush();
            }
            ops.close();
            fis.close();
        }catch (IOException e){
            throw new CustomException("图片加载失败！");
        }
        return Result.success("图片加载成功！");
    }
}
