package com.baize.mybatis.reverse.bean;


public class Column {

    private String columnName;
    private String columnDataType;
    private String columnComment;
    private String propertyName;
    private String propertyDataType;
    private String typeHandler;

    public Column() {
        super();
    }

    public Column(String columnName, String columnDataType,
                  String columnComment, String propertyName, String propertyDataType, String typeHandler) {
        super();
        this.columnName = columnName;
        this.columnDataType = columnDataType;
        this.columnComment = columnComment;
        this.propertyName = propertyName;
        this.propertyDataType = propertyDataType;
        this.typeHandler = typeHandler;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyDataType() {
        return propertyDataType;
    }

    public void setPropertyDataType(String propertyDataType) {
        this.propertyDataType = propertyDataType;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }
}

