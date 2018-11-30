package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.CodeMsg;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalExceptionHandler;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Resource
    MiaoshaUserDao miaoshaUserDao;

    @Resource
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        //取缓存
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
        if (miaoshaUser != null) {
            return miaoshaUser;
        }

        miaoshaUser = miaoshaUserDao.getById(id);
        if (miaoshaUser != null) {
            redisService.set(MiaoshaUserKey.getById,""+id, miaoshaUser);
        }
        return miaoshaUser;
    }

    //对象缓存
    public boolean updatePassword(String token, long id, String formpass) {
        //取user
        MiaoshaUser miaoshaUser = getById(id);
        if(miaoshaUser == null) {
//            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
            return false;
        }

        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formpass,miaoshaUser.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        miaoshaUser.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token,token,miaoshaUser);
        return true;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token,token, MiaoshaUser.class);
        if(user != null) {
            addCookie(response, token,user);  //延长cookie有效期
        }
        return user;
    }


    public CodeMsg login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        String dbpass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password, saltDB);
        if (!dbpass.equals(calcPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }

        //登陆成功
        String token = UUIDUtil.uuid();
        addCookie(response, token,user);
        return CodeMsg.SUCCESS;
    }

    //生成cookie,并返回给客户端
    private void addCookie(HttpServletResponse response, String token,MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user); //把token放入redis缓存中
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
