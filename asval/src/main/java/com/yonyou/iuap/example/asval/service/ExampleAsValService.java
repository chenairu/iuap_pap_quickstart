package com.yonyou.iuap.example.asval.service;

import com.yonyou.iuap.example.asval.dao.ExampleAsValMapper;
import com.yonyou.iuap.example.asval.entity.ComboboxEntity;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleAsValService {

    @Autowired
    private ExampleAsValMapper exampleAsValMapper;

    public List<ExampleAsVal> findAll(){
        return exampleAsValMapper.findAll();
    }

    public List<ComboboxEntity> findProvince(){
        return exampleAsValMapper.findProvince();
    }
}
