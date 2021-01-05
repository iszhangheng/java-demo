package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FindTableDetailMapper {
    List<Map<String, Object>> selectAll(@Param("dbName") String dbName);

    List<Map<String, Object>> selectByDbNameAndTableName(@Param("dbName") String dbName, @Param("tableName") String tableName);

    List<Map<String, Object>> selectAllTableByDbName(@Param("dbName") String dbName);
}
