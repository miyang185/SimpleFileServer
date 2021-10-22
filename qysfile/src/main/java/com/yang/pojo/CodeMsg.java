package com.yang.pojo;

public class CodeMsg {
    private int code;// 业务自定义状态码

    private String msg;// 请求状态描述，调试用

    private FileInfo data;// 请求数据，对象或数组均可

    public CodeMsg() { }

    /**
     * 成功的构造函数
     */
    public CodeMsg(FileInfo data){
        this.code = 200;//默认200是成功
        this.msg = "SUCCESS";
        this.data = data;
    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FileInfo getData() {
        return data;
    }

    public void setData(FileInfo data) {
        this.data = data;
    }

    //自定义Json格式
    @Override
    public String toString() {
        return "{" +
                "code=" + code + '\n' +
                ", msg=" + msg + '\n' +
                ", data={" + data.getId() + '\n' +
                data.getSize()+ '\n' +
                data.getType()+ '\n' +
                data.getOriginalFilename()+ '\n' +
                data.getCreateTime()+ '\n' +
                data.getFilepath()+ '\n' +
                '}'+'\n' +
                '}';
    }
}
