package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.*;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.service.UserService;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.OrderDetailvo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @Resource
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result detail(MiaoshaUser user, @RequestParam("orderId")long orderId){
        if (user == null) {
             return Result.buildError(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.buildError(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailvo orderDetailvo = new OrderDetailvo();
        orderDetailvo.setGoodsVo(goods);
        orderDetailvo.setOrder(order);
        return Result.buildSuccess(orderDetailvo);
    }




}
