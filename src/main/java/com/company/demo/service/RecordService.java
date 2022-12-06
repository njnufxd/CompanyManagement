package com.company.demo.service;

import com.company.demo.model.Record;
import com.company.demo.param.RecordParam;

import java.util.List;

public interface RecordService {
    List<Record> query(RecordParam param);
    Record get(String id);
}
