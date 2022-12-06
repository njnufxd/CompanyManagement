package com.company.demo.control;

import com.company.demo.dao.ContactDAO;
import com.company.demo.dataobject.ContactDO;
import com.company.demo.model.User;
import com.company.demo.service.ContactService;
import com.company.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactDAO contactDAO;
    @GetMapping("/contacts")
    public String contacts(Model model, HttpServletRequest request){
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
        return "contacts";
    }
    @GetMapping("/updateContact")
    public String updateContact(Model model, HttpServletRequest request, @RequestParam(value = "id")String contactId){
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
        List<String> ids=new ArrayList<>();
        ids.add(contactId);
        ContactDO contactDO=contactDAO.findByIds(ids).get(0);
        model.addAttribute("contactId",contactDO.getId());
        return "updateContact";
    }
}
