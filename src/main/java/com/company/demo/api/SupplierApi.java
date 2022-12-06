package com.company.demo.api;

import com.company.demo.dao.ContactDAO;
import com.company.demo.dao.RecordDAO;
import com.company.demo.dao.SupplierDAO;
import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.ContactDO;
import com.company.demo.dataobject.SupplierDO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Paging;
import com.company.demo.model.Result;
import com.company.demo.model.Supplier;
import com.company.demo.service.SupplierService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SupplierApi {


    @Autowired
    private SupplierDAO supplierDAO;
    @Autowired
    private RecordDAO recordDAO;
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private SupplierService supplierService;

    @PostMapping("/api/supplier/paging")
    @ResponseBody
    public Result<Paging<CompanyDO>> query(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                           @RequestBody SupplierDO supplierDO) {
        Result<Paging<CompanyDO>> result = new Result();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        Page<CompanyDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> supplierDAO.query(supplierDO));
        result.setSuccess(true);
        result.setData(
                new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult()));
        return result;
    }

    @GetMapping("/api/supplier/all")
    @ResponseBody
    public Result<List<SupplierDO>> getAll(){
        Result<List<SupplierDO>> result=new Result<>();
        result.setData(supplierDAO.findAll());
        result.setSuccess(true);
        return result;
    }
    @GetMapping("/api/supplier/del")
    @ResponseBody
    public Result del(@RequestParam(value = "id")Long id){
        Result result=new Result<>();
        recordDAO.delBySupplierId(id);
        List< ContactDO> contactDOs=contactDAO.findBySupplierId(id);
        List<String> ids=new ArrayList<>();
        for (ContactDO contactDO : contactDOs) {
            ids.add(contactDO.getId());
        }
        contactDAO.updateSupplierId(ids);
        if (supplierDAO.delete(id)>0){
            result.setSuccess(true);
        }else {
            result.setMessage("删除失败");
        }
        return result;
    }
    @GetMapping("/api/supplier/get")
    @ResponseBody
    public Result<Supplier> get(@RequestParam(value = "id")Long id){
        Result<Supplier> result=new Result<>();
        List<Long> ids=new ArrayList<>();
        ids.add(id);
        List<SupplierDO> supplierDOs=supplierDAO.findByIds(ids);
        if (supplierDOs.isEmpty()){
            result.setCode("600");
            result.setMessage("该供应商不存在");
            return result;
        }
        SupplierDO supplierDO=supplierDOs.get(0);
        result.setData(supplierDO.toModel());
        result.setSuccess(true);
        return result;
    }

    @PostMapping("api/supplier/update")
    @ResponseBody
    public Result<SupplierDO> update(@RequestBody SupplierDO supplierDO){
        Result<SupplierDO> result=supplierService.update(supplierDO);
        return result;
    }
}
