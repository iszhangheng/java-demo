package com.example.entity;

/**
 * Author: Dreamer-1
 * Date: 2019-03-01
 * Time: 11:33
 * Description: 读取Excel时，封装读取的每一行的数据
 */
public class ExcelDataVO {

    /**
     * 序号
     */
    private String serialNumber;

    /**
     * 系统名称及代码
     */
    private String systemNameAndCode;

    /**
     * 数据库用户名称
     */
    private String databaseUserName;

    /**
     * 表大类
     */
    private String tableBigType;
    /**
     * 表类型
     */
    private String tableType;

    /**
     * 表英文名
     */
    private String tableEnName;

    /**
     * 表中文名
     */
    private String tableCnName;

    /**
     * 数据表说明
     */
    private String tableDesc;

    /**
     * 主键
     */
    private String tableKey;

    /**
     * 所在表空间
     */
    private String databaseName;

    /**
     * 表记录长度
     */
    private String tableLogLength;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSystemNameAndCode() {
        return systemNameAndCode;
    }

    public void setSystemNameAndCode(String systemNameAndCode) {
        this.systemNameAndCode = systemNameAndCode;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }

    public String getTableBigType() {
        return tableBigType;
    }

    public void setTableBigType(String tableBigType) {
        this.tableBigType = tableBigType;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableEnName() {
        return tableEnName;
    }

    public void setTableEnName(String tableEnName) {
        this.tableEnName = tableEnName;
    }

    public String getTableCnName() {
        return tableCnName;
    }

    public void setTableCnName(String tableCnName) {
        this.tableCnName = tableCnName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableLogLength() {
        return tableLogLength;
    }

    public void setTableLogLength(String tableLogLength) {
        this.tableLogLength = tableLogLength;
    }
}
