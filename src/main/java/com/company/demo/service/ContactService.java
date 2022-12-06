package com.company.demo.service;

import com.company.demo.dataobject.ContactDO;
import com.company.demo.model.Contact;
import com.company.demo.model.Result;
import com.company.demo.param.NormalContactParam;

import java.util.List;

public interface ContactService {

    public List<Contact> advanceQuery(ContactDO contactDO);
    public List<Contact> normalQuery(NormalContactParam param);
}
