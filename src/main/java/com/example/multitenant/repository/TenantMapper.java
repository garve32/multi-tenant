package com.example.multitenant.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TenantMapper {

    @Select("SELECT COUNT(*) FROM EMP")
    int countUser();
}
