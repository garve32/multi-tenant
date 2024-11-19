package com.example.multitenant.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TenantMapper {

    @Select("SELECT COUNT(*) FROM EMP")
    int countUser();

    List getUsers();
}
