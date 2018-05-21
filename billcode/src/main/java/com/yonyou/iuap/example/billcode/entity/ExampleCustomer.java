package com.yonyou.iuap.example.billcode.entity;

public class ExampleCustomer {

    private String id;
    private String code;
    private String name;
    private String province;
    private String city;
    private int corpSize;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCorpSize() {
        return corpSize;
    }

    public void setCorpSize(int corpSize) {
        this.corpSize = corpSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
