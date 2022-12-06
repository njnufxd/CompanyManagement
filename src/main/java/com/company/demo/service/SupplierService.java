package com.company.demo.service;

import com.company.demo.dataobject.SupplierDO;
import com.company.demo.model.Result;

public interface SupplierService {
    Result<SupplierDO> update(SupplierDO supplierDO);

}
