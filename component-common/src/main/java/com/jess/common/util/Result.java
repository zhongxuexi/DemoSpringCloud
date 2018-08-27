package com.jess.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhongxuexi on 2018/7/15.
 */

public class Result<T> {
    private int code;
    private String msg;
    private Integer count;
    private T data;

    {
        this.code = 0;
        this.msg = "成功";
        this.count = 0;
        System.out.println("初始化...");
    }

    private Result(T data,Integer count) {
        this.count = count;
        this.data = data;
    }
    private Result(CodeMsg cm) {
        if(cm == null){
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }
    /**
     * 成功时候的调用
     * @return
     */
    public static <T> Result<T> success(T data,Integer count){
        return new Result<T>(data,count);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(){
        return (Result<T>) success("",1);
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param cm
     * @param msg
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm,String msg){

        if(StringUtils.contains(cm.getMsg(),":")){
            String str = cm.getMsg().split(":")[0];
            cm.setMsg(str+":"+msg);
        }else {
            cm.setMsg(cm.getMsg()+":"+msg);
        }
        return new Result<T>(cm);
    }
    public T getData() {
        return data;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
