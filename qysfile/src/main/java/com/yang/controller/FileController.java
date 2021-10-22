package com.yang.controller;


import com.yang.mapper.FileInfoMapper;
import com.yang.pojo.CodeMsg;
import com.yang.pojo.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileInfoMapper fileInfoMapper;


     //上传文件接口
    @PostMapping("/upload")
    public String uploading(@RequestParam("file") MultipartFile file) {
        //获取日志输出对象
        Logger logger = LoggerFactory.getLogger(FileController.class);

        //获取当前日期，设置目录格式"yyyyMMdd"
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String filepath = simpleDateFormat.format(date)+"/";

        //如果目录不存在，则创建
        File targetPath = new File(filepath);
        if (!targetPath.exists()) {
            targetPath.mkdir();
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 获得文件原始名称
        String fileName = file.getOriginalFilename();
        // 获得文件后缀名称
        if (!fileName.equals("")) {
            //获取文件大小、类型、原始名称、创建时间（根据上传时间决定） 文件在服务器上的路径
            long fileSize = file.getSize();
            String fileType = file.getContentType();
            String OriginalFilename = file.getOriginalFilename();
            //获取当前日期
            date = new Date();
            //日期格式化
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = simpleDateFormat.format(date);
            //获取文件路径
            String filePath = filepath;
            //将文件信息封装成文件信息对象
            FileInfo fileInfo = new FileInfo(uuid,fileSize,fileType,OriginalFilename,createTime,filePath);
            //将数据插入到数据库
            fileInfoMapper.addFileInfo(fileInfo);

            //获取文件后缀
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            // 生成最新的uuid文件名称
            String newFileName = uuid + "." + suffixName;
            try{
                //文件输出流
                FileOutputStream out = new FileOutputStream(filepath + newFileName);
                //写入数据
                out.write(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("文件上传失败!");
                return  uuid;
            }
            //输出日志信息，返回uuid
            logger.info("文件上传成功!");
            return uuid;
        }
        else{
            //输出日志信息
            logger.error("文件不存在，请重新上传!");
            //返回uuid
            return uuid;
        }
    }


  //下载文件接口
  @GetMapping("/download")
    public String downLoad(@RequestParam("uuid") String uuid, HttpServletResponse response) throws UnsupportedEncodingException {
      //获取文件信息
      FileInfo fileInfo = fileInfoMapper.getFileInfoById(uuid);
      //判断数据库中是否存在uuid对应的文件
      if (fileInfo != null) {
          //获取文件后缀，例如.txt、.pdf
          String suffixName = fileInfo.getOriginalFilename().substring(fileInfo.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
          //获取文件名和文件路径
          String filename = fileInfo.getId() + "." + suffixName;
          String filePath = fileInfo.getFilepath();
          //拿到文件
          File file = new File(filePath + filename);
          System.out.println(filePath + filename);
          //文件存在则输出，文件不存在直接返回异常状态码410
          if (file.exists()) {
              //设置响应数据
              response.setContentType("application/octet-stream");
              response.setHeader("content-type", "application/octet-stream");
              response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf8"));
              byte[] buffer = new byte[1024];
              //输出流
              OutputStream os = null;
              //文件输出流
              try (FileInputStream fis = new FileInputStream(file);
                   BufferedInputStream bis = new BufferedInputStream(fis);) {
                  //文件输出流
                  os = response.getOutputStream();
                  int i = bis.read(buffer);
                  while (i != -1) {
                      //写入数据
                      os.write(buffer);
                      //继续读取数据
                      i = bis.read(buffer);
                  }

              } catch (Exception e) {
                  e.printStackTrace();
                  //直接返回异常状态码410
                  return "410";
              }
          }
          //直接返回异常状态码410
          return "410";
      }else{
          //直接返回异常状态码410
          return "410";
      }
  }

    //获取文件元数据接口
    @GetMapping("/getFileInfo")
    public CodeMsg getFileInfo(@RequestParam("uuid") String uuid) throws UnsupportedEncodingException{
        //获取文件信息
        FileInfo fileInfo = fileInfoMapper.getFileInfoById(uuid);

        if(fileInfo!=null) {
            //装配Json数据信息---成功
            CodeMsg codeMsg = new CodeMsg(fileInfo);
            System.out.println(codeMsg);
            //返回数据
            return codeMsg;
        }else{
            //装配Json数据信息---失败
            CodeMsg codeMsg = new CodeMsg(400,"failure");
            //返回数据
            return codeMsg;
        }


    }


}
