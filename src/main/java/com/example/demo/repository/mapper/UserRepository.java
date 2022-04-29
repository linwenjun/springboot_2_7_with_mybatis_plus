package com.example.demo.repository.mapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.repository.Role;
import com.example.demo.repository.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> implements UserMapperService{}
