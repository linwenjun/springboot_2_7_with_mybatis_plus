package com.example.demo.repository.mapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.repository.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends ServiceImpl<RoleMapper, Role> implements RoleMapperService{}
