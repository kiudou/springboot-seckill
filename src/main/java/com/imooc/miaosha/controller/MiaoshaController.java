package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.*;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    private static Logger logger = LoggerFactory.getLogger(MiaoshaController.class);


    @Resource
    GoodsService goodsService;

    @Resource
    OrderService orderService;

    @Resource
    MiaoshaService miaoshaService;

    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result miaosha(MiaoshaUser user, @RequestParam("goodsId")long goodsId) {
        if(user == null) {
            return Result.buildError(CodeMsg.SESSION_ERROR);
        }

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.buildError(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null) {
            return Result.buildError(CodeMsg.REPEATE_MIAOSHA);
        }

        //减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        return Result.buildSuccess(orderInfo);
    }
}
