package com.company.demo.control;


import com.company.demo.dao.CompanyDAO;
import com.company.demo.dao.ContactDAO;
import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.ContactDO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.*;
import com.company.demo.service.CompanyService;
import com.company.demo.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CompanyController {
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactDAO contactDAO;
    @PostMapping("/company")
    @ResponseBody
    public CompanyDO save(@RequestBody CompanyDO companyDO) {
        companyDAO.add(companyDO);
        return companyDO;
    }
    @GetMapping("/companies")
    public String companies(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        Long userId= (Long) session.getAttribute("userId");
        if (userId==null){
            return "redirect:/login";
        }
        User user=userService.getById(userId);
        if (user==null){
            session.removeAttribute("userId");
            return "redirect:/login";
        }
        model.addAttribute("userName",user.getUserName() );
        return "companies";
    }
    @GetMapping("/addCompany")
    public String addCompany(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        Long userId= (Long) session.getAttribute("userId");
        if (userId==null){
            return "redirect:/login";
        }
        User user=userService.getById(userId);
        if (user==null){
            session.removeAttribute("userId");
            return "redirect:/login";
        }
        model.addAttribute("userName",user.getUserName() );
        return "addCompany";
    }
    @GetMapping("/updateCompany")
    public String updateCompany(Model model, @RequestParam(value = "id") String id, HttpServletRequest request){
        HttpSession session=request.getSession();
        Long userId= (Long) session.getAttribute("userId");
        if (userId==null){
            return "redirect:/login";
        }
        User user=userService.getById(userId);
        if (user==null){
            session.removeAttribute("userId");
            return "redirect:/login";
        }
        model.addAttribute("userName",user.getUserName() );
        model.addAttribute("id",id);
        return "updateCompany";
    }


}
