package com.company.demo.service.impl;

import com.company.demo.dao.*;
import com.company.demo.dataobject.*;
import com.company.demo.model.Contact;
import com.company.demo.model.Record;
import com.company.demo.model.User;
import com.company.demo.param.NormalContactParam;
import com.company.demo.param.RecordParam;
import com.company.demo.service.ContactService;
import com.company.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class RecordServiceImpl implements RecordService {

    @Autowired
    private ContactService contactService;
    @Autowired
    private RecordDAO recordDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SupplierDAO supplierDAO;
    @Override
    public List<Record> query(RecordParam param) {
        List<Record> records=new ArrayList<>();
        NormalContactParam contactParam=new NormalContactParam();
        contactParam.setCompanyName(param.getCompanyName());
        contactParam.setPosition(param.getPosition());
        contactParam.setName(param.getContactName());
        contactParam.setProvince(param.getProvince());
        contactParam.setCity(param.getCity());
        contactParam.setArea(param.getArea());
        contactParam.setCategory(param.getCategory());
        contactParam.setSize(param.getSize());
        List<Contact> contacts=contactService.normalQuery(contactParam);
        System.out.println(contacts);
        List<String> ids=new ArrayList<>();
        for (Contact contact : contacts) {
            ids.add(contact.getId());
        }
        List<RecordDO> recordDOs=recordDAO.query(ids, param.getContent());
        System.out.println(recordDOs);
        for (RecordDO recordDO : recordDOs) {
            Record record=recordDO.toModel();
            for (Contact contact : contacts) {
                if (recordDO.getContactId().equals(contact.getId())){
                    record.setContact(contact);
                    break;
                }
            }
            records.add(record);
        }
        return records;
    }

    @Override
    public Record get(String id) {
        List<String> ids=new ArrayList<>();
        ids.add(id);
        RecordDO recordDO=recordDAO.findByIds(ids).get(0);
        Record record=recordDO.toModel();
        ids=new ArrayList<>();
        ids.add(recordDO.getContactId());
        ContactDO contactDO=contactDAO.findByIds(ids).get(0);
        List<Long> list=new ArrayList<>();
        list.add(recordDO.getModifiedId());
        list.add(recordDO.getCreatedId());
        List<UserDO> userDOs=userDAO.findByIds(list);
        for (UserDO userDO : userDOs) {
            if (userDO.getId().equals(recordDO.getCreatedId())){
                record.setCreatedUser(userDO.toModel());
            }
            if (userDO.getId().equals(recordDO.getModifiedId())){
                record.setModifiedUser(userDO.toModel());
            }
        }
        list=new ArrayList<>();
        list.add(recordDO.getSupplierId());
        SupplierDO supplierDO=supplierDAO.findByIds(list).get(0);
        record.setSupplier(supplierDO.toModel());
        ids=new ArrayList<>();
        ids.add(contactDO.getCompanyId());
        CompanyDO companyDO=companyDAO.findByIds(ids).get(0);
        Contact contact=contactDO.toModel();
        contact.setCompany(companyDO.toModel());
        record.setContact(contact);
        return record;
    }
}
