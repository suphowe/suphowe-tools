package com.soft.method.template.thymeleaf;

import com.soft.method.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 主页
 * @author suphowe
 */
@Controller
@Slf4j
public class ThymeleafIndexController {

    @GetMapping("/thymeleaf_index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            mv.setViewName("redirect:/thymeleaf_user/thymeleaf_login");
        } else {
            mv.setViewName("thymeleaf/page/index.html");
            mv.addObject(user);
        }

        return mv;
    }
}
