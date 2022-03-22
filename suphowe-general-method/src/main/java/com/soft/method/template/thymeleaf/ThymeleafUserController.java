package com.soft.method.template.thymeleaf;

import com.soft.method.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户页面
 * @author suphowe
 */
@Controller
@RequestMapping("/thymeleaf_user")
@Slf4j
public class ThymeleafUserController {
    @PostMapping("/thymeleaf_login")
    public ModelAndView login(User user, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        mv.addObject(user);
        mv.setViewName("redirect:/");

        request.getSession().setAttribute("user", user);
        return mv;
    }

    @GetMapping("/thymeleaf_login")
    public ModelAndView login() {
        return new ModelAndView("thymeleaf/page/login.html");
    }
}
