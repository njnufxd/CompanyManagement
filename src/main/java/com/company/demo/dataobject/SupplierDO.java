package com.company.demo.dataobject;

import com.company.demo.model.Record;
import com.company.demo.model.Supplier;
import com.company.demo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class SupplierDO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
    public Supplier toModel(){
        Supplier supplier=new Supplier();
        BeanUtils.copyProperties(this, supplier);
        return supplier;
    }

}
