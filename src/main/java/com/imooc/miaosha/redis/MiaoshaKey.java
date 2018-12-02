package com.imooc.miaosha.redis;

public class MiaoshaKey extends BasePrefix {

    public MiaoshaKey() {
    }

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
