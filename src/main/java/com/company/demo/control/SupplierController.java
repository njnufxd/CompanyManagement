package com.company.demo.control;

import com.company.demo.model.User;
import com.company.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SupplierController {
    @Autowired
    private UserService userService;
    @GetMapping("/suppliers")
    public String suppliers(Model model, HttpServletRequest request){
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
        return "suppliers";
    }
    @GetMapping("/updateSupplier")
    public String updateSupplier(@RequestParam("id") Long id ,Model model, HttpServletRequest request){
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
        model.addAttribute("supplierId",id);
        return "updateSupplier";
    }
}
