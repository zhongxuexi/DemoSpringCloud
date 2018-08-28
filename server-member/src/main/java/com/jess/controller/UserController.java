package com.jess.controller;
import com.jess.common.util.PageBean;
import com.jess.common.util.Result;
import com.jess.entity.User;
import com.jess.service.FileUploadService;
import com.jess.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value="用户管理")
@RequestMapping("/user")
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
    @ApiOperation(value="分页查询", notes="分页查询")
    @GetMapping(value = "/findByPage")
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
    @ApiOperation(value="新增用户")
    @PostMapping(value = "/add")
    public Result addUser(@ModelAttribute User user) throws Exception {
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
    @ApiOperation(value="修改用户")
    @PostMapping(value = "/update")
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
    @ApiOperation(value="通过主键ID删除用户")
    @GetMapping(value = "/delete")
    public Result deleteUser(Long id) throws Exception {
        Integer count = userService.deleteUser(id);
        return getResult(count);
    }

    /**
     * 通过主键ID批量删除用户
     * @param ids
     * @return
     * @throws Exception
     */
    @ApiOperation(value="通过主键ID批量删除用户")
    @GetMapping(value = "/batchDelete")
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
    @ApiOperation(value="根据用户主键ID查询用户信息")
    @GetMapping(value = "/findUserById")
    public Result findUserById(@RequestParam(value = "id")Long id,
                               @RequestParam(required = false,value = "field",defaultValue = "")String field) throws Exception {
        Map<String,Object> map = userService.findUserById(id,field);
        return getResult(map);
    }

    /**
     * 根据用户名字模糊查询用户信息
     * @param userName
     * @return
     * @throws Exception
     */
    @ApiOperation(value="根据用户名字模糊查询用户信息")
    @GetMapping(value = "/findUserByName")
    public Result findUserByName(@RequestParam(required = false,value = "userName",defaultValue = "")String userName,
                                 @RequestParam(required = false,value = "field",defaultValue = "")String field) throws Exception {
        List<Map<String,Object>> users = userService.findUserByName(userName,field);
        return getResult(users);
    }

    /**
     * 根据用户年龄查询用户信息
     * @param age
     * @return
     * @throws Exception
     */
    @ApiOperation(value="根据用户年龄查询用户信息")
    @GetMapping(value = "/findUserByAge")
    public Result findUserByName(Byte age) throws Exception {
        List<User> users = userService.findUserByAge(age);
        return getResult(users);
    }

    @ApiOperation(value="文件上传")
    @PostMapping(value = "/upload")
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
     * @param
     * @return
     * @throws Exception
     */
    @ApiOperation(value="用户注册")
    @PostMapping(value = "/register")
    public Result register() throws Exception {

        return getResult(1);
    }

    @ApiOperation(value="测试接口")
    @GetMapping(value = "/test")
    public String test(@RequestParam("desc") String desc) throws Exception{
        return "this is member server:"+desc;
    }

}
