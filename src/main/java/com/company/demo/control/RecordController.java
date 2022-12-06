package com.company.demo.control;

import com.company.demo.dao.RecordDAO;
import com.company.demo.dataobject.RecordDO;
import com.company.demo.model.User;
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
public class RecordController {
    @Autowired
    private UserService userService;
    @Autowired
    private RecordDAO recordDAO;
    @GetMapping("/records")
    public String records(Model model, HttpServletRequest request){
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
        return "records";
    }
    @GetMapping("/updateRecord")
    public String update(Model model, HttpServletRequest request, @RequestParam(value = "id") String id){
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
        List<String> ids=new ArrayList<>();
        ids.add(id);
        RecordDO recordDO=recordDAO.findByIds(ids).get(0);
        model.addAttribute("userName",user.getUserName() );
        model.addAttribute("userId",user.getId());
        model.addAttribute("recordId",recordDO.getId());
        return "updateRecord";
    }
}
