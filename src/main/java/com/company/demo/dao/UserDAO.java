package com.company.demo.dao;

import com.company.demo.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserDAO {

    int batchAdd(@Param("list") List<UserDO> userDOs);

    List<UserDO> findByIds(@Param("ids") List<Long> ids);

    int add(UserDO userDO);

    int update(UserDO userDO);

    int delete(@Param("id") long id);

    List<UserDO> findAll();

    UserDO findByUserName(@Param("userName") String name);

    List<UserDO> query(UserDO userDO);

    List<UserDO> search(@Param("keyWord") String keyWord, @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
}
