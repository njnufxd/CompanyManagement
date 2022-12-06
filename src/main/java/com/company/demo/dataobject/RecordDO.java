package com.company.demo.dataobject;

import com.company.demo.model.Contact;
import com.company.demo.model.Record;
import com.company.demo.model.Supplier;
import com.company.demo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class RecordDO {
    private String id;
    private String contactId;
    private String content;
    private Long createdId;
    private Long modifiedId;
    private Long supplierId;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    public Long getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(Long modifiedId) {
        this.modifiedId = modifiedId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
    public Record toModel(){
        Record record=new Record();
        BeanUtils.copyProperties(this, record);
        return record;
    }
}
