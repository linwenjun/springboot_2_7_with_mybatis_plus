package com.example.demo.repository;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.repository.typeHandler.DetailListTypeHandler;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        String type;
        String item;
    }

    @TableId(type = IdType.AUTO)
    private String id;

    private String name;

    @TableField(value = "details", typeHandler = DetailListTypeHandler.class)
    private List<Detail> details;

    @TableField(exist = false)
    private List<Role> roles;
}
