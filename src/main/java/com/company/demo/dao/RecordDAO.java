package com.company.demo.dao;

import com.company.demo.dataobject.ContactDO;
import com.company.demo.dataobject.RecordDO;
import com.company.demo.dataobject.SupplierDO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordDAO {
    int add(RecordDO recordDO);

    List<RecordDO> findByContactId(@Param("contactId")String contactId);
    int update(RecordDO recordDO);
    List<RecordDO> findAll();
    List<RecordDO> query(@Param("ids")List<String> ids,@Param("content") String content);
    List<RecordDO> findByIds(@Param("ids") List<String> ids);

    int delete(@Param("id") String id);
    int delByContactIds(@Param("ids") List<String> ids);
    int delBySupplierId(@Param("id") Long id);
}
