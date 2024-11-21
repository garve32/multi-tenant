package com.example.multitenant.repository;

import com.example.multitenant.model.BspUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

    BspUser selectUserTenant(String userId);
}
