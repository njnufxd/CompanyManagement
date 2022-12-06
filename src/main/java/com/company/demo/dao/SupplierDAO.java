package com.company.demo.dao;

import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.ContactDO;
import com.company.demo.dataobject.SupplierDO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplierDAO {

    List<SupplierDO> findAll();
    List<SupplierDO> query(SupplierDO supplierDO);
    int add(SupplierDO supplierDO);
    int update(SupplierDO supplierDO);
    List<SupplierDO> findByIds(@Param("ids") List<Long> ids);
    int delete(@Param("id") Long id);
    SupplierDO findByName(@Param("name") String name);
}
