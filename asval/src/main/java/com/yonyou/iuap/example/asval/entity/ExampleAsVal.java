package com.yonyou.iuap.example.asval.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ExampleAsVal implements Serializable {

    private String id;

    private String pid;

    private String code;

    private String name;

    private String value;

    private Integer ordIndex;

    private Date lstdate;

    private String isSystem;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrdIndex() {
        return ordIndex;
    }

    public void setOrdIndex(Integer ordIndex) {
        this.ordIndex = ordIndex;
    }

    public Date getLstdate() {
        return lstdate;
    }

    public void setLstdate(Date lstdate) {
        this.lstdate = lstdate;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}