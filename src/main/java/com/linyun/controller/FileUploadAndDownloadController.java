package com.linyun.controller;

import com.linyun.common.Rest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author linyun
 * @date 2023/04/01/11:08
 */
@RestController
@RequestMapping("/common")
public class FileUploadAndDownloadController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 图片上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Rest<String> upload(MultipartFile file) throws IOException {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成临时文件就会删除
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名
        String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        File dir = new File(basePath);
        //判断时候存在这个文件夹，没有存在就创建
        if (!dir.exists()){
            //创建目录
            dir.mkdirs();
        }

        file.transferTo(new File(basePath+fileName));
        return Rest.success(fileName);
    }

    /**
     * 图片下载
     * @param name
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws Exception{


            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();


    }
}
