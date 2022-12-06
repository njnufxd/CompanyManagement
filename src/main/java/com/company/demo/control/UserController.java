package com.company.demo.control;

import com.company.demo.dao.UserDAO;
import com.company.demo.dataobject.UserDO;
import com.company.demo.model.Paging;
import com.company.demo.model.Result;
import com.company.demo.model.User;
import com.company.demo.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/users")
    public String users(Model model, HttpServletRequest request){
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
        return "users";
    }
    @GetMapping("/self")
    public String self(Model model, HttpServletRequest request){
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
        model.addAttribute("id",user.getId());
        return "self";
    }
    @GetMapping("/updateUser")
    public String update(Model model,HttpServletRequest request,@RequestParam(value = "id") Long id){
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
        return "updateUser";
    }
    @GetMapping("/addUser")
    public String add(Model model,HttpServletRequest request){
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
        return "addUser";
    }
}
