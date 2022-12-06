package com.company.demo.service;

import com.company.demo.dataobject.CompanyDO;
import com.company.demo.model.Company;
import com.company.demo.model.Result;

public interface CompanyService {
    public Result<CompanyDO> add(CompanyDO companyDO);
    public Result<CompanyDO> update(CompanyDO companyDO);
}
