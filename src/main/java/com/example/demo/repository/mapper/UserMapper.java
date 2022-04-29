package com.example.demo.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.repository.Role;
import com.example.demo.repository.User;
import com.example.demo.repository.typeHandler.DetailListTypeHandler;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM `user` WHERE id = #{id}")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="details", column="details", typeHandler = DetailListTypeHandler.class),
            @Result(property="roles", javaType= List.class, column="id",
                    many=@Many(select="findRolesByUserId"))})
    User findById(String id);

    @Select("SELECT * FROM `role` WHERE userId = #{userId}")
    List<Role> findRolesByUserId(String userId);
}
