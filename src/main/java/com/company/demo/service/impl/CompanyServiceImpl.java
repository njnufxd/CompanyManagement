package com.company.demo.service.impl;

import com.company.demo.dao.CompanyDAO;
import com.company.demo.dataobject.CompanyDO;
import com.company.demo.model.Company;
import com.company.demo.model.Result;
import com.company.demo.service.CompanyService;
import com.company.demo.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDAO companyDAO;
    @Override
    public Result<CompanyDO> add(CompanyDO companyDO) {
        Result<CompanyDO> result=new Result<>();
        if (StringUtils.isEmpty(companyDO.getCompanyName())){
            result.setCode("600");
            result.setMessage("公司名不能为空");
            return result;
        }
        if (companyDAO.findByCompanyName(companyDO.getCompanyName())!=null){
            result.setCode("602");
            result.setMessage("该公司名已存在");
            return result;
        }
        companyDO.setId(UUIDUtils.getUUID());
        companyDAO.add(companyDO);
        result.setData(companyDO);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result<CompanyDO> update(CompanyDO companyDO) {
        Result<CompanyDO> result=new Result<>();
        if (StringUtils.isEmpty(companyDO.getId())){
            result.setCode("600");
            result.setMessage("id不能为空");
            return result;
        }
        if (StringUtils.isEmpty(companyDO.getCompanyName())){
            result.setCode("601");
            result.setMessage("公司名不能为空");
            return result;
        }
        CompanyDO companyDO1=companyDAO.findByCompanyName(companyDO.getCompanyName());
        if (companyDO1!=null&&!companyDO1.getId().equals(companyDO.getId())){
            result.setCode("602");
            result.setMessage("该公司名已被其他占用");
            return result;
        }
        companyDAO.update(companyDO);
        result.setSuccess(true);
        result.setData(companyDO);
        return result;
    }

}
