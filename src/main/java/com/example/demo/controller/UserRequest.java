package com.example.demo.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.repository.User;
import com.example.demo.repository.typeHandler.DetailListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value="user", autoResultMap = true)
public class UserRequest {


    @Id
    private String id;

    private String name;

    @TableField(typeHandler = DetailListTypeHandler.class)
    private List<User.Detail> details;
}
