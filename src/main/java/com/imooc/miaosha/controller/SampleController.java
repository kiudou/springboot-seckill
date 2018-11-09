package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Resource
    UserService userService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","kiudou");
        return "hello";
    }

    @RequestMapping("/db")
    @ResponseBody
    public String dbGet(){
        User user = userService.getById(1);
        return user.getName();
    }


    @RequestMapping("/redis/get")
    @ResponseBody
    public String redisGet(){
        Integer l = userService.get("key1",Integer.class);
        return l.toString();
    }

}
