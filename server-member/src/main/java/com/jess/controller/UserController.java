package com.jess.controller;
import com.jess.entity.RegisterUser;
import com.jess.entity.User;
import com.jess.service.FileUploadService;
import com.jess.service.UserService;
import com.jess.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Created by zhongxuexi on 2018/6/5.
 */
@RestController
@CrossOrigin
public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadService fileUploadService;

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
        Integer count = 0;
        try{
            count =  userService.deleteUser(id);
        }catch (Exception e){
            //EmailUtil.sendHtmlMail("jess.zhong@aliyun.com","异常报告",this.getClass()+"类的deleteUser方法报错，信息："+e.getMessage());
            String message = "异常报告:deleteUser方法报错，信息---"+e.getMessage();
            //System.out.println(message);
            LogUtil.getLogger(this.getClass()).error(message);
        }
        return getResult(count);
    }

    /**
     * 通过主键ID批量删除用户
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchDelete")
    public Result batchDelete(String ids) throws Exception {
       Integer count = userService.batchDelete(ids);
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

    @RequestMapping(value = "/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        if (null != file) {
            String fileName = file.getOriginalFilename();// 文件原名称
            // 判断文件类型
            String type = fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;// 文件类型
                if (type!=null) {// 判断文件类型是否为空
                    if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                        Map<String, Object> map = fileUploadService.upload(file);
                        return map;
                    }else {
                        System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    }
                }
        }else{
            System.out.println("文件为空");
        }
        return null;
    }

    /**
     * 用户注册
     * @param registerUser
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register")
    public Result register(@RequestBody RegisterUser registerUser) throws Exception {

        return getResult(1);
    }

    @RequestMapping(value = "/test")
    public User test(){
        return new User();
    }

}
