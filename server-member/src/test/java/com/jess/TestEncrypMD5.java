package com.jess;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhongxuexi on 2018/7/21.
 */
public class TestEncrypMD5 {
    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        //根据MD5算法生成MessageDigest对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }


    public static void main(String args[]) throws NoSuchAlgorithmException{
        String msg = "zhongxuexi";
        TestEncrypMD5 md5 = new TestEncrypMD5();
        byte[] resultBytes = md5.eccrypt(msg);

        System.out.println("密文是：" + new String(resultBytes));
        System.out.println("明文是：" + msg);
    }

}
