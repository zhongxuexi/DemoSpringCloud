package com.jess.member;

import org.junit.Test;

/**
 * Created by zhongxuexi on 2018/8/11.
 */
public class Test1 {
    @Test
    public void test11(){
        // TODO Auto-generated method stub
        //测试可变长度的参数列表与数组传参有什么不同........
        myTest("aaa","bbb","ccc","ddd");
        //打印结果 aaa bbb ccc ddd
        String[] arr = new String[]{"111","222","333"} ;
        myTest(arr);
        //打印结果 111 222 333
    }

    public static void myTest(String... strs){
        for(String str : strs){
            System.out.println(str);
        }
    }
    @Test
    public void test12(){
        //测试可变长度的参数列表与数组传参有什么不同........
        myTest1("aaa","bbb","ccc","ddd");
    }

    public static void myTest1(String str1 ,String... strs){
        for(String str : strs){
            System.out.println(str);
        }
        System.out.println(str1);
    }
    //结果为 bbb ccc ddd  aaa

}
