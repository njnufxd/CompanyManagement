package com.company.demo.dao;

import com.company.demo.dataobject.ContactDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
@Mapper
public interface ContactDAO {
    int batchAdd(@Param("list") List<ContactDO> contactDOs);

    List<ContactDO> findByIds(@Param("ids") List<String> ids);
    List<ContactDO> findByCompanyId(@Param("id")String id);


    int add(ContactDO contactDO);

    int update(ContactDO contactDO);

    int delete(@Param("id") String id);

    int batchDel(@Param("ids") List<String> ids);

    int delByCompanyId(@Param("id") String id);

    List<ContactDO> findAll();

    ContactDO findByContactName(@Param("contactName") String name);

    List<ContactDO> advanceQuery(@Param("contact") ContactDO contactDO,@Param("companyIds")List<String> ids);

    List<ContactDO> normalQuery( ContactDO contactDO);


    List<ContactDO> search(@Param("keyWord") String keyWord, @Param("startTime") LocalDateTime startTime,
                           @Param("endTime") LocalDateTime endTime);

    List<ContactDO> findBySupplierId(@Param("id")Long id);

    int updateSupplierId(@Param("ids") List<String> ids);
}
