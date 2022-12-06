package com.company.demo.dao;

import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CompanyDAO {
    int batchAdd(@Param("list") List<CompanyDO> companyDOs);

    List<CompanyDO> findByIds(@Param("ids") List<String> ids);

    int add(CompanyDO companyDO);

    int update(CompanyDO companyDO);

    int delete(@Param("id") String id);

    List<CompanyDO> findAll();

    CompanyDO findByCompanyName(@Param("companyName") String name);

    List<CompanyDO> query(CompanyDO companyDO);


    List<CompanyDO> search(@Param("name") String keyWord);
}
