package com.company.demo.api;

import com.company.demo.dao.*;
import com.company.demo.dataobject.*;
import com.company.demo.model.Contact;
import com.company.demo.model.Paging;
import com.company.demo.model.Record;
import com.company.demo.model.Result;
import com.company.demo.param.NormalContactParam;
import com.company.demo.service.ContactService;
import com.company.demo.util.UUIDUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactApi {

    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private RecordDAO recordDAO;

    @Autowired
    private SupplierDAO supplierDAO;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/api/contact/add")
    @ResponseBody
    public Result<ContactDO> add(@RequestBody ContactDO contactDO){
        contactDO.setId(UUIDUtils.getUUID());
        Result<ContactDO> result=new Result<>();
        if (contactDO.getName()==null|| StringUtils.isEmpty(contactDO.getName())){
            result.setCode("600");
            result.setMessage("姓名不能为空");
            return result;
        }
        contactDAO.add(contactDO);
        result.setSuccess(true);
        result.setData(contactDO);
        return result;
    }

    @PostMapping("/api/contact/advancePaging")
    @ResponseBody
    public Result<Paging<Contact>> advanceQuery(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                @RequestBody ContactDO contactDO) {
        Result<Paging<Contact>> result =new Result<>();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        List<Contact> data=new ArrayList<>();
        List<Contact> contacts=contactService.advanceQuery(contactDO);
        for (int i = pageNum*pageSize-pageSize; i < pageNum*pageSize&&i<contacts.size(); i++) {
            data.add(contacts.get(i));
        }
        result.setSuccess(true);
        result.setData(new Paging<>(pageNum,pageSize,contacts.size()/pageSize+1,contacts.size(),data));
        return result;
    }
    @PostMapping("/api/contact/normalPaging")
    @ResponseBody
    public Result<Paging<Contact>> normalQuery(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                               @RequestBody NormalContactParam param) {
        Result<Paging<Contact>> result = new Result<>();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        List<Contact> data=new ArrayList<>();
        List<Contact> contacts=contactService.normalQuery(param);
        for (int i = pageNum*pageSize-pageSize; i < pageNum*pageSize&&i<contacts.size(); i++) {
            data.add(contacts.get(i));
        }
        result.setSuccess(true);
        result.setData(new Paging<>(pageNum,pageSize,contacts.size()/pageSize+1,contacts.size(),data));
        return result;
    }
    @GetMapping("/api/contact/get")
    @ResponseBody
    public Result<Contact> getById(@RequestParam(value = "id") String id){
        Result<Contact> result=new Result<>();
        List<String> ids=new ArrayList<>();
        ids.add(id);
        ContactDO contactDO=contactDAO.findByIds(ids).get(0);
        if (contactDO==null){
            result.setCode("600");
            result.setMessage("联系人不存在");
            return result;
        }
        Contact contact=contactDO.toModel();
        List<RecordDO> recordDOs=recordDAO.findByContactId(contact.getId());
        List<Record> records = new ArrayList<>();
        if (!ListUtils.isEmpty(recordDOs)) {
            List<Long> userIds = new ArrayList<>();
            List<Long> supplierIds = new ArrayList<>();
            for (RecordDO recordDO : recordDOs) {
                userIds.add(recordDO.getCreatedId());
                userIds.add(recordDO.getModifiedId());
                supplierIds.add(recordDO.getSupplierId());
            }
            List<SupplierDO> supplierDOs = supplierDAO.findByIds(supplierIds);
            List<UserDO> userDOs = userDAO.findByIds(userIds);
            for (RecordDO recordDO : recordDOs) {
                Record record = recordDO.toModel();
                for (SupplierDO supplierDO : supplierDOs) {
                    if (supplierDO.getId() == recordDO.getSupplierId()) {
                        record.setSupplier(supplierDO.toModel());
                    }
                }
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == recordDO.getCreatedId()) {
                        record.setCreatedUser(userDO.toModel());
                    }
                    if (userDO.getId() == recordDO.getModifiedId()) {
                        record.setModifiedUser(userDO.toModel());
                    }
                }
                records.add(record);
            }
        }
        contact.setRecords(records);
        ids=new ArrayList<>();
        ids.add(contact.getCompanyId());
        CompanyDO companyDO=companyDAO.findByIds(ids).get(0);
        contact.setCompany(companyDO.toModel());
        result.setData(contact);
        result.setSuccess(true);
        return result;
    }
    @PostMapping("/api/contact/update")
    @ResponseBody
    public Result<ContactDO> update(@RequestParam(value = "companyName",required = false)String companyName,@RequestBody ContactDO contactDO){
        System.out.println(companyName);
        Result<ContactDO> result=new Result<>();
        if (contactDO.getId()==null){
            result.setCode("600");
            result.setMessage("联系人ID为空");
            return result;
        }
        List<String> ids=new ArrayList<>();
        ids.add(contactDO.getId());
        ContactDO contactDO1=contactDAO.findByIds(ids).get(0);
        if (contactDO1==null){
            result.setCode("601");
            result.setMessage("当前联系人不存在");
            return result;
        }
        if (StringUtils.isEmpty(companyName)){
            contactDO.setCompanyId(contactDO1.getCompanyId());
        }else {
            contactDO.setCompanyId(companyDAO.findByCompanyName(companyName).getId());
        }
        if(contactDAO.update(contactDO)==0) {
            result.setCode("602");
            result.setMessage("修改失败");
            return result;
        }
        result.setSuccess(true);
        result.setData(contactDO);
        return result;
    }
    @GetMapping("/api/contact/updateSupplier")
    @ResponseBody
    public Result<ContactDO> updateSupplier(@RequestParam(value = "id")String id,@RequestParam(value = "supplierId") String supplierId){
        ContactDO contactDO=new ContactDO();
        contactDO.setId(id);
        contactDO.setSupplierId(supplierId);
        contactDAO.update(contactDO);
        Result<ContactDO> result=new Result<>();
        result.setSuccess(true);
        result.setData(contactDO);
        return result;
    }
    @GetMapping("/api/contact/del")
    @ResponseBody
    public Result del(@RequestParam("id") String id) {
        Result result=new Result();
        List<String> ids=new ArrayList<>();
        ids.add(id);
        if (contactDAO.delete(id)>0){
            result.setSuccess(true);
            recordDAO.delByContactIds(ids);
        }else {
            result.setMessage("删除失败");
        }
        return result;
    }

    @GetMapping("/api/contact/getBySupplierId")
    @ResponseBody
    public Result<List<Contact>> getBySupplierId(@RequestParam("id") Long id){
        Result<List<Contact>> result=new Result<>();
        if (id==null){
            result.setMessage("id不能为空");
            result.setCode("600");
            return result;
        }
        List<Long> ids=new ArrayList<>();
        ids.add(id);
        if (supplierDAO.findByIds(ids).isEmpty()){
            result.setCode("601");
            result.setMessage("该供应商不存在");
            return result;
        }
        List<ContactDO> contactDOs=contactDAO.findBySupplierId(id);
        List<Contact> contacts=new ArrayList<>();
        for (ContactDO contactDO : contactDOs) {
            contacts.add(contactDO.toModel());
        }
        result.setSuccess(true);
        result.setData(contacts);
        return result;
    }
}
