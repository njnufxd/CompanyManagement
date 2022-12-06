package com.company.demo.api;

import com.company.demo.dao.CompanyDAO;
import com.company.demo.dao.ContactDAO;
import com.company.demo.dao.RecordDAO;
import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.ContactDO;
import com.company.demo.model.Company;
import com.company.demo.model.Contact;
import com.company.demo.model.Paging;
import com.company.demo.model.Result;
import com.company.demo.service.CompanyService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CompanyApi {
    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private RecordDAO recordDAO;
    @PostMapping("/api/company/paging")
    @ResponseBody
    public Result<Paging<CompanyDO>> query(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                            @RequestBody CompanyDO companyDO) {
        Result<Paging<CompanyDO>> result = new Result();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        Page<CompanyDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> companyDAO.query(companyDO));
        result.setSuccess(true);
        result.setData(
                new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult()));
        return result;
    }
    @PostMapping("/api/company/add")
    @ResponseBody
    public Result<CompanyDO> add(@RequestBody CompanyDO companyDO){
        return companyService.add(companyDO);
    }

    @PostMapping("/api/company/update")
    @ResponseBody
    public Result<CompanyDO> update(@RequestBody CompanyDO companyDO){
        return companyService.update(companyDO);
    }

    @GetMapping("/api/company/get")
    @ResponseBody
    public Result<Company> get(@RequestParam(value = "id") String id){
        Result<Company> result=new Result<>();
        List<String > ids=new ArrayList<>();
        ids.add(id);
        CompanyDO companyDO=companyDAO.findByIds(ids).get(0);
        if (companyDO==null ){
            result.setCode("600");
            result.setMessage("id不存在");
            return  result;
        }
        Company company=companyDO.toModel();
        List<ContactDO> contactDOs=contactDAO.findByCompanyId(id);
        List<Contact> contacts=new ArrayList<>();
        for (ContactDO contactDO : contactDOs) {
            contacts.add(contactDO.toModel());
        }
        company.setContacts(contacts);
        result.setData(company);
        result.setSuccess(true);
        return result;

    }
    @GetMapping("/api/company/del")
    @ResponseBody
    public Result del(@RequestParam("id") String id) {
        Result result=new Result();
        List<String> ids=new ArrayList<>();
        ids.add(id);
        CompanyDO companyDO=companyDAO.findByIds(ids).get(0);
        List<ContactDO> contactDOs=contactDAO.findByCompanyId(companyDO.getId());
        ids=new ArrayList<>();
        if (!ListUtils.isEmpty(contactDOs)) {
            for (ContactDO contactDO : contactDOs) {
                ids.add(contactDO.getId());
            }
        }
        if (companyDAO.delete(id)>0){
            result.setSuccess(true);
            contactDAO.delByCompanyId(id);
            if (!ListUtils.isEmpty(ids)) {
                recordDAO.delByContactIds(ids);
            }
        }else {
            result.setMessage("删除失败");
        }
        return result;
    }
    @GetMapping("/api/company/search")
    @ResponseBody
    public Result<List<CompanyDO>> search(@RequestParam(value = "name") String companyName){
        List<CompanyDO> companyDOs=companyDAO.search(companyName);
        Result<List<CompanyDO>> result=new Result<>();
        result.setData(companyDOs);
        result.setSuccess(true);
        return result;
    }
    @GetMapping("/api/company/all")
    @ResponseBody
    public Result<List<CompanyDO>> all(){
        Result<List<CompanyDO>> result=new Result<>();
        result.setData(companyDAO.findAll());
        result.setSuccess(true);
        return result;
    }
}
