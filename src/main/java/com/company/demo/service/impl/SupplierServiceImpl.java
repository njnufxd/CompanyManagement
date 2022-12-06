package com.company.demo.service.impl;

import com.company.demo.dao.SupplierDAO;
import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.SupplierDO;
import com.company.demo.model.Result;
import com.company.demo.service.SupplierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDAO supplierDAO;
    @Override
    public Result<SupplierDO> update(SupplierDO supplierDO) {
        Result<SupplierDO> result=new Result<>();
        if (supplierDO.getId()==null){
            result.setCode("600");
            result.setMessage("id不能为空");
            return result;
        }
        if (StringUtils.isEmpty(supplierDO.getName())){
            result.setCode("601");
            result.setMessage("供应商名字不能为空");
            return result;
        }
        SupplierDO supplierDO1=supplierDAO.findByName(supplierDO.getName());
        if (supplierDO1!=null&&!supplierDO1.getId().equals(supplierDO.getId())){
            result.setCode("602");
            result.setMessage("该公司名已被其他占用");
            return result;
        }
        supplierDAO.update(supplierDO);
        result.setSuccess(true);
        result.setData(supplierDO);
        return result;
    }

    @Override
    public Result<SupplierDO> add(SupplierDO supplierDO) {
        Result<SupplierDO> result=new Result<>();
        if (StringUtils.isEmpty(supplierDO.getName())){
            result.setCode("600");
            result.setMessage("供应商名不能为空");
            return result;
        }
        SupplierDO supplierDO1=supplierDAO.findByName(supplierDO.getName());
        if (supplierDO1!=null){
            result.setCode("601");
            result.setMessage("该供应商已存在");
            return result;
        }
        result.setSuccess(true);
        result.setData(supplierDO);
        return result;
    }
}
