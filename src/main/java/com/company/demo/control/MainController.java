package com.company.demo.control;

import com.company.demo.api.UserAPI;
import com.company.demo.dao.CompanyDAO;
import com.company.demo.dao.ContactDAO;
import com.company.demo.dataobject.CompanyDO;
import com.company.demo.dataobject.ContactDO;
import com.company.demo.model.Company;
import com.company.demo.model.Contact;
import com.company.demo.model.User;
import com.company.demo.service.CompanyService;
import com.company.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @GetMapping("/index")
    public String index(Model model,HttpServletResponse response, HttpServletRequest request){
        HttpSession session=request.getSession();
        Long userId= (Long) session.getAttribute("userId");
        LocalDateTime loginTime= (LocalDateTime) session.getAttribute("loginTime");
        if (userId==null){
            model.addAttribute("user",null);
        }else {
            User user=userService.getById(userId);
            model.addAttribute("user",user);
            if (user!=null) {
                model.addAttribute("userName",user.getUserName() );
            }
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String loginTimeStr=dtf.format(loginTime);
            model.addAttribute("loginTime",loginTimeStr);
        }
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
