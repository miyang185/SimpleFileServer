package com.yang.mapper;

import com.yang.pojo.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//mapper注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface FileInfoMapper {


    //增加上传文件信息
    int addFileInfo(FileInfo fileInfo);

    //根据id查询文件信息，这里的id也就是用户请求的uuid
    FileInfo getFileInfoById(String id);
}
