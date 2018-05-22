package com.yonyou.iuap.example.asval.entity;

import java.io.Serializable;
import java.util.Date;

public class ExampleAsVal implements Serializable {

    private String valsetId;

    private String valId;

    private String val;

    private Integer ordIndex;

    private Date lstdate;

    private String isSystem;

    private static final long serialVersionUID = 1L;

    public String getValsetId() {
        return valsetId;
    }

    public void setValsetId(String valsetId) {
        this.valsetId = valsetId == null ? null : valsetId.trim();
    }

    public String getValId() {
        return valId;
    }

    public void setValId(String valId) {
        this.valId = valId == null ? null : valId.trim();
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val == null ? null : val.trim();
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
        this.isSystem = isSystem == null ? null : isSystem.trim();
    }
}