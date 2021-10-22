package com.yang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    private String id;
    private long size;
    private String type;
    private String originalFilename;
    private String createTime;
    private String filepath;
}
