package com.company.demo.service;

import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Result;
import com.company.demo.model.User;

import java.util.List;

public interface UserService {
    public Result<User> register(UserDO userDO);

    public Result<User> login(String userName, String pwd);

    public List<User> queryUser(List<Long> userIds);
    public User getById(Long id);
}
