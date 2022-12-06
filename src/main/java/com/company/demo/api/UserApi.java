package com.company.demo.api;

import com.company.demo.dao.UserDAO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Paging;
import com.company.demo.model.Result;
import com.company.demo.model.User;
import com.company.demo.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/api/user/login")
    @ResponseBody
    public Result<User> login(@RequestParam String userName, @RequestParam String pwd, HttpServletRequest request) {
        Result<User> result = userService.login(userName, pwd);
        if (result.isSuccess()) {
            request.getSession().setAttribute("userId", result.getData().getId());
            request.getSession().setAttribute("loginTime", LocalDateTime.now());
        }
            return result;
    }

    @GetMapping("/api/user/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request) {
        Result result = new Result();
        request.getSession().removeAttribute("userId");
        result.setSuccess(true);
        return result;
    }
    @PostMapping("/api/user/paging")
    @ResponseBody
    public Result<Paging<UserDO>> query(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                         @RequestBody UserDO userDO) {
        Result<Paging<UserDO>> result = new Result();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        // 设置当前页数为1，以及每页3条记录
        Page<UserDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> userDAO.query(userDO));
        result.setSuccess(true);
        result.setData(
                new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult()));
        return result;
    }

    @PostMapping("/api/user/add")
    @ResponseBody
    public Result<User> add(@RequestBody UserDO userDO) {
        Result<User> result=userService.register(userDO);
        if (!result.isSuccess()){
            return result;
        }
        userDO.setPwd(result.getData().getPwd());
        result.setData(userDO.toModel());
        return result;
    }

    @PostMapping("/api/user/batchAdd")
    @ResponseBody
    public List<UserDO> batchAdd(@RequestBody List<UserDO> userDOs) {
        userDAO.batchAdd(userDOs);
        return userDOs;
    }

    @PostMapping("/api/user/update")
    @ResponseBody
    public Result<UserDO> update(@RequestBody UserDO userDO, HttpServletRequest request) {
        Result<UserDO> result=new Result<>();
        HttpSession session=request.getSession();
        userDO.setId((Long) session.getAttribute("userId"));
        if (userDO.getId()==0){
            result.setCode("602");
            result.setMessage("admin用户信息不可修改");
        }
        User user=userService.getById(userDO.getId());
        if (user==null){
            result.setCode("600");
            result.setMessage("该用户不存在");
            return result;
        }
        if (StringUtils.isEmpty(userDO.getPwd())){
            userDO.setPwd(user.getPwd());
        }else {
            String saltPwd = userDO.getPwd() + "lcr";
            String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();
            userDO.setPwd(md5Pwd);
        }
        if (userDAO.update(userDO)==0){
            result.setCode("601");
            result.setMessage("修改失败");
            return result;
        }
        result.setSuccess(true);
        userDO.setPwd(null);
        result.setData(userDO);
        return result;
    }

    @GetMapping("/api/user/get")
    @ResponseBody
    public Result<UserDO> get( HttpServletRequest request)
    {
           Result<UserDO> result=new Result<>();
           Long id= (Long) request.getSession().getAttribute("userId");
           List<Long> ids=new ArrayList<>();
           ids.add(id);
           result.setData(userDAO.findByIds(ids).get(0));
           result.setSuccess(true);
           return result;
    }
    @GetMapping("/api/user/del")
    @ResponseBody
    public Result del(@RequestParam("id") Long id) {
        Result result=new Result();
        if (id==0) {
            result.setCode("600");
            result.setMessage("admin用户不能删除");
            return result;
        }
        if (userDAO.delete(id)>0){
            result.setSuccess(true);
        }else {
            result.setMessage("删除失败");
        }
        return result;
    }

    @GetMapping("/user/findByUserName")
    @ResponseBody
    public UserDO findByUserName(@RequestParam("userName") String userName) {
        return userDAO.findByUserName(userName);
    }
}
