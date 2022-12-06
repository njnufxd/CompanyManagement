package com.company.demo.api;

import com.company.demo.dao.RecordDAO;
import com.company.demo.dataobject.RecordDO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Contact;
import com.company.demo.model.Paging;
import com.company.demo.model.Record;
import com.company.demo.model.Result;
import com.company.demo.param.RecordParam;
import com.company.demo.service.RecordService;
import com.company.demo.util.UUIDUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecordApi {

    @Autowired
    private RecordDAO recordDAO;

    @Autowired
    private RecordService recordService;

    @PostMapping("/api/record/add")
    @ResponseBody
    public Result<RecordDO> add(@RequestBody RecordDO recordDO, HttpServletRequest request){
        Result<RecordDO> result=new Result<>();
        HttpSession session=request.getSession();
        recordDO.setId(UUIDUtils.getUUID());
        Long userId= (Long) session.getAttribute("userId");
        System.out.println(userId);
        recordDO.setCreatedId(userId);
        recordDO.setModifiedId(userId);
        if (recordDO.getSupplierId()==null){
            result.setCode("600");
            result.setMessage("请选择供应商");
            return result;
        }
        if (StringUtils.isEmpty(recordDO.getContent())){
            result.setCode("601");
            result.setMessage("记录内容不能为空");
        }
        recordDAO.add(recordDO);
        result.setData(recordDO);
        result.setSuccess(true);
        return result;
    }
    @GetMapping("/api/record/paging")
    @ResponseBody
    public Result<Paging<UserDO>> paging(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                         @RequestParam(value = "contactId") String contactId){
        Result<Paging<UserDO>> result = new Result();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        // 设置当前页数为1，以及每页3条记录
        Page<UserDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> recordDAO.findByContactId(contactId));
        result.setSuccess(true);
        result.setData(
                new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult()));
        return result;
    }
    @PostMapping("/api/record/query")
    @ResponseBody
    public Result<Paging<Record>> query(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                        @RequestBody RecordParam param){
        Result<Paging<Record>> result = new Result();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        // 设置当前页数为1，以及每页3条记录
        List<Record> data=new ArrayList<>();
        List<Record> records=recordService.query(param);
        for (int i = pageNum*pageSize-pageSize; i < pageNum*pageSize&&i<records.size(); i++) {
            data.add(records.get(i));
        }
        result.setSuccess(true);
        result.setData(
                new Paging<>(pageNum,pageSize,records.size()/pageSize+1,records.size(),data));
        return result;
    }
    @GetMapping("/api/record/del")
    @ResponseBody
    public Result del(@RequestParam(value = "id") String id){
        Result result=new Result();
        if (StringUtils.isEmpty(id)){
            result.setMessage("id不能为空");
            result.setCode("600");
            return result;
        }
        System.out.println(id);
        if (recordDAO.delete(id)<=0){
            result.setCode("601");
            result.setMessage("删除失败");
            return result;
        }
        result.setSuccess(true);
        return result;
    }
    @GetMapping("/api/record/get")
    @ResponseBody
    public Result<Record> get(@RequestParam(value = "id") String id){
        Result<Record> result=new Result<>();
        if (StringUtils.isEmpty(id)){
            result.setMessage("id不能为空");
            result.setCode("600");
            return result;
        }
        Record record=recordService.get(id);
        result.setData(record);
        result.setSuccess(true);
        return result;
    }
    @PostMapping("/api/record/update")
    @ResponseBody
    public Result<RecordDO> update(@RequestBody RecordDO recordDO){
        Result<RecordDO> result=new Result<>();
        if (recordDAO.update(recordDO)<=0) {
            result.setCode("600");
            result.setMessage("修改失败");
            return result;
        }
        result.setSuccess(true);
        result.setData(recordDO);
        return result;
    }


}
