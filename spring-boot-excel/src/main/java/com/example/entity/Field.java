package com.example.entity;

/**
 * 数据库字段类
 */
public class Field {
    /**
     * 字段英文名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 长度
     */
    private Integer fieldLength;
    /**
     * 是否是主键
     */
    private Boolean isKey;
    /**
     * 是否可为空
     */
    private Boolean isNull;
    /**
     * 取值范围
     */
    private String comment;
}
