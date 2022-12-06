package com.company.demo.service.impl;

import com.company.demo.dao.CompanyDAO;
import com.company.demo.dao.ContactDAO;
import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.ContactDO;
import com.company.demo.model.Company;
import com.company.demo.model.Contact;
import com.company.demo.param.NormalContactParam;
import com.company.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Override
    public List<Contact> advanceQuery(ContactDO contactDO) {
        List<Contact> contacts=new ArrayList<>();
        List<ContactDO> contactDOs=contactDAO.normalQuery(contactDO);
        List<String> ids=new ArrayList<>();
        for (ContactDO contactDO1 : contactDOs) {
            ids.add(contactDO1.getCompanyId());
        }
        List<CompanyDO> companyDOList=companyDAO.findByIds(ids);
        for (ContactDO contactDO1:contactDOs){
            Contact contact=contactDO1.toModel();
            for (CompanyDO companyDO1 : companyDOList) {
                if (companyDO1.getId().equals(contact.getCompanyId())){
                    Company company=companyDO1.toModel();
                    contact.setCompany(company);
                    break;
                }
            }
            contacts.add(contact);
        }
        return contacts;
    }
    @Override
    public List<Contact> normalQuery(NormalContactParam param) {
        List<Contact> contacts=new ArrayList<>();
        CompanyDO companyDO=new CompanyDO();
        ContactDO contactDO=new ContactDO();
        companyDO.setCompanyName(param.getCompanyName());
        companyDO.setProvince(param.getProvince());
        companyDO.setCity(param.getCity());
        companyDO.setArea(param.getArea());
        companyDO.setCategory(param.getCategory());
        companyDO.setSize(param.getSize());
        contactDO.setPosition(param.getPosition());
        contactDO.setGender(param.getGender());
        contactDO.setName(param.getName());
        List<CompanyDO> companyDOs=companyDAO.query(companyDO);
        List<String> companyIds=new ArrayList<>();
        for (CompanyDO companyDO1 : companyDOs) {
            companyIds.add(companyDO1.getId());
        }
        System.out.println(companyIds);
        List<ContactDO> contactDOs=contactDAO.advanceQuery(contactDO,companyIds);
        List<String> ids=new ArrayList<>();
        for (ContactDO contactDO1 : contactDOs) {
            ids.add(contactDO1.getCompanyId());
        }
        List<CompanyDO> companyDOList=companyDAO.findByIds(ids);
        for (ContactDO contactDO1:contactDOs){
            Contact contact=contactDO1.toModel();
            for (CompanyDO companyDO1 : companyDOList) {
                if (companyDO1.getId().equals(contact.getCompanyId())){
                    Company company=companyDO1.toModel();
                    contact.setCompany(company);
                    break;
                }
            }
            contacts.add(contact);
        }
        return contacts;
    }
}
