package com.company.demo.service.impl;

import com.company.demo.dao.UserDAO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Result;
import com.company.demo.model.User;
import com.company.demo.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public Result<User> register(UserDO userDO) {
        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(userDO.getUserName())) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(userDO.getPwd())) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }

        UserDO userDO1 = userDAO.findByUserName(userDO.getUserName());
        if (userDO1 != null) {
            result.setCode("602");
            result.setMessage("用户名已经存在");
            return result;
        }

        String saltPwd = userDO.getPwd() + "lcr";
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        userDO.setPwd(md5Pwd);

        userDAO.add(userDO);

        result.setSuccess(true);

        result.setData(userDO.toModel());

        return result;
    }

    @Override
    public Result<User> login(String userName, String pwd) {

        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(userName)) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(pwd)) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }

        UserDO userDO = userDAO.findByUserName(userName);
        if (userDO == null) {
            result.setCode("602");
            result.setMessage("用户名不存在");
            return result;
        }

        String saltPwd = pwd + "lcr";
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        if (!StringUtils.equals(md5Pwd, userDO.getPwd())) {
            result.setCode("603");
            result.setMessage("密码不对");
            return result;
        }

        result.setSuccess(true);

        result.setData(userDO.toModel());

        return result;
    }

    @Override
    public List<User> queryUser(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return null;
        }
        List<UserDO> userDOS = userDAO.findByIds(userIds);
        List<User> users = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userDOS)) {
            for (UserDO userDO : userDOS) {
                users.add(userDO.toModel());
            }
        }
        return users;
    }

    @Override
    public User getById(Long id) {
        if (id==null){
            return null;
        }
        List<Long> ids=new ArrayList<>();
        ids.add(id);
        UserDO userDO=userDAO.findByIds(ids).get(0);
        if (userDO==null){
            return null;
        }
        return userDO.toModel();

    }
}
