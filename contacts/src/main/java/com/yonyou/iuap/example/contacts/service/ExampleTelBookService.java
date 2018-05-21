package com.yonyou.iuap.example.contacts.service;

import com.yonyou.iuap.example.contacts.dao.ExampleInstitMapper;
import com.yonyou.iuap.example.contacts.dao.ExampleTelBookMapper;
import com.yonyou.iuap.example.contacts.entity.ExampleInstit;
import com.yonyou.iuap.example.contacts.entity.ExampleTelBook;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExampleTelBookService {

    @Autowired
    private ExampleTelBookMapper telBookmapper;
    @Autowired
    private ExampleInstitMapper institMapper;

    /**
     * 根据某一非主键字段查询实体
     */
    public List<ExampleTelBook> findByCode(String code) {
        return telBookmapper.findByCode(code);
    }

    /**
     * 分页相关
     */
    public Page<ExampleTelBook> selectAllByPage(PageRequest pageRequest, SearchParams searchParams ,String institid) {
        List<String> list = new ArrayList<String>();
        String[] ids = institid.split(",");
        for(String id : ids){list.add(id);}
        searchParams.getSearchMap().put("ids", list);
    	return telBookmapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }

    /**
     * 保存（插入和更新）
     */
    @Transactional
    public void save(List<ExampleTelBook> recordList) {
        List<ExampleTelBook> addList = new ArrayList<>(recordList.size());
        List<ExampleTelBook> updateList = new ArrayList<>(recordList.size());
        for (ExampleTelBook TelBook : recordList) {

            if (TelBook.getId() == null) {
                TelBook.setId(UUID.randomUUID().toString());
                TelBook.setDr(0);
                addList.add(TelBook);
            } else {
                updateList.add(TelBook);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            telBookmapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            telBookmapper.batchUpdate(updateList);
        }
    }

    /**
     * 批量删除
     */
    public void batchDeleteByPrimaryKey(List<ExampleTelBook> list) {
        telBookmapper.batchDelete(list);
    }

    /**
     * 根据联系人信息查询所属机构
     *
     * @param userJobPage
     * @return
     */
    public Page<ExampleTelBook> selectInstit(Page<ExampleTelBook> data) {
        List<ExampleTelBook> list = data.getContent();
        Set<String> ids = new HashSet<String>();
        for (ExampleTelBook TelBook : list) {
            if (!StringUtil.isEmpty(TelBook.getInstitid())) {
                ids.add(TelBook.getInstitid());
            }
        }
        if (!ids.isEmpty()) {
            List<ExampleInstit> instits = this.selectInstitByIds(ids);
            for (ExampleTelBook TelBook : list) {
                for (ExampleInstit instit : instits) {
                    if (TelBook.getInstitid() != null && TelBook.getInstitid().equalsIgnoreCase(instit.getInstitid())) {
                        TelBook.setInstitname(instit.getInstit_name());
                    }
                }
            }
            return data;
        }
        return null;
    }

    public List<ExampleInstit> selectInstitByIds(Set<String> ids) {
        return institMapper.selectInstitByIds(ids);
    }

}