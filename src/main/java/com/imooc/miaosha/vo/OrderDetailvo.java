package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.OrderInfo;

public class OrderDetailvo {
    private GoodsVo goodsVo;
    private OrderInfo order;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
