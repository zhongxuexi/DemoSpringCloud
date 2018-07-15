package com.jess.controller;

import com.google.common.collect.Maps;
import com.jess.entity.User;
import com.jess.service.UserService;
import com.jess.util.CodeMsg;
import com.jess.util.PageBean;
import com.jess.util.Result;
import com.jess.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by zhongxuexi on 2018/6/5.
 */
@RestController
@CrossOrigin
public class UserController extends BaseController{
    @Value("${web.upload-path}")
    private String path;
    @Autowired
    private UserService userService;

    /**
     *分页功能(集成mybatis的分页插件pageHelper实现)
     * @param currentPage       :当前页数
     * @param pageSize          :每页显示的总记录数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPage")
    public Result getAll(@RequestParam(required = false,value = "keyword",defaultValue = "") String keyword,
                                    @RequestParam(required = false,value = "currentPage",defaultValue = "1") Integer currentPage,
                                     @RequestParam(required = false,value = "pageSize",defaultValue = "5") Integer pageSize) throws Exception {
        PageBean<User> page = userService.findByPage(keyword, currentPage, pageSize);
        return Result.success(page.getItems(),page.getTotalNum());
    }

    /**
     * 新增用户
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    public Result addUser(@RequestBody User user) throws Exception {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        Integer count = userService.addUser(user);
        return getResult(count);
    }

    /**
     * 修改用户
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    public Result updateUser(@RequestBody User user) throws Exception {
        user.setUpdateTime(new Date());
        Integer count = userService.updateUser(user);
        return getResult(count);
    }

    /**
     * 通过主键ID删除用户
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public Result deleteUser(Long id) throws Exception {
        Integer count = userService.deleteUser(id);
        return getResult(count);
    }

    /**
     * 根据用户主键ID查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findUserById")
    public Result findUserById(Long id) throws Exception {
        User user = userService.findUserById(id);
        return getResult(user);
    }

    /**
     * 根据用户名字模糊查询用户信息
     * @param userName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findUserByName")
    public Result findUserByName(String userName) throws Exception {
        List<User> users = userService.findUserByName(userName);
        return getResult(users);
    }

    /**
     * 根据用户年龄查询用户信息
     * @param age
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findUserByAge")
    public Result findUserByName(Byte age) throws Exception {
        List<User> users = userService.findUserByAge(age);
        return getResult(users);
    }

    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        if (null != file) {
            String fileName = file.getOriginalFilename();// 文件原名称
            // 判断文件类型
            String type = fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;// 文件类型
                if (type!=null) {// 判断文件类型是否为空
                    if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                        // 自定义的文件名称
                        String trueFileName = fileName;//+String.valueOf(System.currentTimeMillis());
                        try {
                            File localFile = new File(path,trueFileName);
                            if (!localFile.exists()) { //如果不存在 则创建
                                //先得到文件的上级目录，并创建上级目录，在创建文件
                                localFile.getParentFile().mkdirs();
                                //创建文件
                                localFile.createNewFile();
                            }
                            file.transferTo(localFile);// 转存文件

                            //数据库存储的相对路径
                            WebApplicationContext webApplicationContext = (WebApplicationContext) SpringContextUtils.applicationContext;
                            ServletContext servletContext = webApplicationContext.getServletContext();
                            String projectPath = servletContext.getContextPath();
                            String contextpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+projectPath;
                            String jdbcurl = contextpath + "/upload/"+trueFileName;
                            System.out.println("相对路径:"+jdbcurl);
                            return jdbcurl;//path;
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    }
                }
        }else{
            System.out.println("文件为空");
        }
        return "";
    }

    @RequestMapping(value = "/test")
    public String test(){
        return "这是接口测试";
    }

}
