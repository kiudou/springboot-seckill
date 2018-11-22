package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper //mybatis的注解
public interface UserDao {

    @Select("select * from miaosha_user where id = #{id}") //通过Param,{}里就可以引用
    public User getById(@Param("id")int id);
}
