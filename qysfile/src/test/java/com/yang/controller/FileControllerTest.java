package com.yang.controller;

import com.yang.pojo.CodeMsg;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {

    @Test
    public void contextLoads() {
    }

    /**
     * 注入属性
     * war == new FileController()
     */

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    //在每次测试执行前构建mvc环境
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 测试上传文件接口
     */
    @Test
    public void UploadFile() {
        try {
            String result = mockMvc.perform(
                    MockMvcRequestBuilders
                            //发送请求
                            .fileUpload("/file/upload")
                            //生成模拟上传的文件
                            .file(
                                    new MockMultipartFile("file", "test.txt", ",multipart/form-data", "hello upload".getBytes("UTF-8"))
                            )
            ).andExpect(status().isOk())
                    //返回响应结果
                    .andReturn().getResponse().getContentAsString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试下载文件接口
     */
    @Test
    public void DownLoadFile() throws Exception {
        mockMvc.perform(
                /**
                 * 发送get请求
                 * 上传参数uuid
                 * 获取返回的文件流
                 * 下载对应文件
                 */
                MockMvcRequestBuilders
                        .get("/file/download")
                        .param("uuid", "51b797387f8d4605b9dcc398777a4561")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                           @Override
                           public void handle(MvcResult mvcResult) throws Exception {
                               //保存为文件
                               File file = new File("/downloadFile/");
                               file.delete();
                               //输出流，输出要保存的文件
                               FileOutputStream fout = new FileOutputStream(file);
                               //输出流  即响应成功返回的文件流
                               ByteArrayInputStream bin = new ByteArrayInputStream(mvcResult.getResponse().getContentAsByteArray());
                               //复制文件，模拟下载
                               StreamUtils.copy(bin, fout);
                               //关闭输出
                               fout.close();
                               //打印文件是否存在和文件大小信息
                               System.out.println("is exist:" + file.exists());
                               System.out.println("file length:" + file.length());
                           }
                       }
                );
    }

    /**
     * 测试获取原数据接口
     */
    @Test
    public void getFileInfo(){
        try {
            String result = mockMvc.perform(
                    MockMvcRequestBuilders
                            //发送get请求
                            .get("/file/getFileInfo")
                            //上传参数
                            .param("uuid",
                                   "51b797387f8d4605b9dcc398777a4561"
                            )
            ).andExpect(status().isOk())
                    //返回响应结果
                    .andReturn().getResponse().getContentAsString();
            //在控制台打印结果
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}




