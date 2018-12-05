# springboot-seckill
基于springboot高并发系统网站

### 项目环境

SpringBoot, Thymeleaf, Mybatis, Druid,Mysql, Redis, RabbitMQ

### Redis
	
[redis安装和操作](https://kiudou.github.io/2018/12/05/Redis%E5%AE%89%E8%A3%85%E5%92%8C%E6%93%8D%E4%BD%9C/)
	
	redis-server redis.conf //根据配置文件启动
	
	redis-cli		//进入redis
	>shutdown		//关闭redis
	
### RabbitMQ

	在sbin下执行文件
	./rabbitmq-server	//开启rabbitmq
	
	./rabbitmqctl stop  //关闭rabbitmq


### [项目中遇到的坑](https://kiudou.github.io/2018/12/05/%E5%81%9A%E7%A7%92%E6%9D%80%E9%A1%B9%E7%9B%AE%E9%81%87%E5%88%B0%E7%9A%84%E5%9D%91/)

### 项目展示

![](http://m.qpic.cn/psb?/V13zmFZT2vlTI6/GduKucxBU5T0V.plqTHytHLtoA5OCkxcWO8eVU9psxg!/b/dLYAAAAAAAAA&bo=CAWAAtgJ5AQDCZI!&rf=viewer_4)

![](http://m.qpic.cn/psb?/V13zmFZT2vlTI6/VYjB5ECdwl2nq.aLF85c3xmfbSw20uFfRHgVKVuchZg!/b/dFQBAAAAAAAA&bo=lgaAAqQKCgQDGY4!&rf=viewer_4)

![](http://m.qpic.cn/psb?/V13zmFZT2vlTI6/hUfAl9aazlKpCOFdLuWCGRTvim9sBmeiJUTZuFm4me4!/b/dDIBAAAAAAAA&bo=6gWAArQJGgQDGfI!&rf=viewer_4)


### 数据库表的定义

	CREATE TABLE `goods` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
	  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
	  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
	  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
	  `goods_detail` longtext COMMENT '商品的详情介绍',
	  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
	  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='商品';


	CREATE TABLE `miaosha_goods` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品表',
	  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
	  `miaosha_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',
	  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
	  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
	  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='秒杀详情表';


	CREATE TABLE `miaosha_order` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	  `user_id` bigint(20) DEFAULT NULL COMMENT '用户订单',
	  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
	  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
	  PRIMARY KEY (`id`),
	  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`)
	) ENGINE=InnoDB AUTO_INCREMENT=3580 DEFAULT CHARSET=utf8mb4 COMMENT='秒杀的订单';


	CREATE TABLE `miaosha_user` (
	  `id` bigint(20) NOT NULL COMMENT '用户ID，手机号',
	  `nickname` varchar(255) NOT NULL COMMENT '姓名',
	  `password` varchar(66) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
	  `salt` varchar(20) DEFAULT NULL,
	  `head` varchar(128) DEFAULT NULL COMMENT '头像，云储存的ID\n',
	  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
	  `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
	  `login_count` int(11) DEFAULT NULL COMMENT '登陆次数',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户';
	
	CREATE TABLE `order_info` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
	  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
	  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址id',
	  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
	  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
	  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
	  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1.pc,2.android,3.ios',
	  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态，0.新建未支付,1.已支付,2.已发货,3.已收货,4.已退款,5.已完成',
	  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
	  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=3672 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
	



