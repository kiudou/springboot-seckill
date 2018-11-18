package com.imooc.miaosha.controller;
import com.imooc.miaosha.domain.Result;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.UserKey;
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
        User user = userService.get(UserKey.getById,""+1,User.class);
        Result<User> result = new Result<>(user);
        return result.toString();
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public boolean redisSet(){
        User user = new User(1,"1111");
        userService.set(UserKey.getById,""+1,user); //UserKey:id1
        return true;
    }

}
