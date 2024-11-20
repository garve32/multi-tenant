package com.example.multitenant.repository;

import com.example.multitenant.model.BspUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {

    BspUser selectUser(String userId);
}
