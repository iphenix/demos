package com.huawei.pqm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("call listAllBoard method.");
        UsernamePasswordToken token = new UsernamePasswordToken("aaa", "aaa");
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            //e.printStackTrace();
        }

        return "demo";
    }
}
