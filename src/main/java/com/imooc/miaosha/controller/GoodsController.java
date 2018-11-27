package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.CodeMsg;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.Result;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource
    MiaoshaUserService miaoshaUserService;

    @Resource
    GoodsService goodsService;


    @RequestMapping("/to_list")
    public String toList(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);

        //查询秒杀列表
        List<GoodsVo> goodsList =  goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser user, @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        GoodsVo goods =  goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //判断秒杀时间
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0; // 活动状态
        int remainSeconds = 0; //剩余时间
        if(now < startAt) { //秒杀未开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now)/1000);
        }else if (now > endAt) { //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else { //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }

}
