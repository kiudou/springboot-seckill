package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.CodeMsg;
import com.imooc.miaosha.domain.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.util.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }


    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(LoginVo loginVo) {
        logger.info(loginVo.toString());
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile)) {
            return Result.buildError(CodeMsg.MOBILE_EMPTY);
        }
        if (StringUtils.isEmpty(password)) {
            return Result.buildError(CodeMsg.PASSWORD_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            return Result.buildError(CodeMsg.MOBILE_ERROR);
        }
        CodeMsg cm = miaoshaUserService.login(loginVo);
        if (cm.getCode() == 0) {
            return Result.buildSuccess(CodeMsg.SUCCESS);
        } else {
            return Result.buildError(cm);
        }
    }
}
