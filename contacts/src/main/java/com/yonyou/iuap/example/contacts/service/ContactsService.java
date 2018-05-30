package com.yonyou.iuap.example.contacts.service;

import com.yonyou.iuap.example.contacts.dao.OrganizationMapper;
import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.contacts.dao.ContactsMapper;
import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.example.contacts.entity.Contacts;
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
public class ContactsService extends GenericService<Contacts>{

    /**
     * 根据某一非主键字段查询实体
     */
    public List<Contacts> findByCode(String code) {
        return contactsMapper.findByCode(code);
    }

    /**
     * 分页相关
     */
    public Page<Contacts> selectAllByPage(PageRequest pageRequest, SearchParams searchParams ,String institid) {
        List<String> list = new ArrayList<String>();
        String[] ids = institid.split(",");
        for(String id : ids){list.add(id);}
        searchParams.getSearchMap().put("ids", list);
    	return this.selectAllByPage(pageRequest, searchParams);
    }
    
    /**
     * 分页相关
     */
    public Page<Contacts> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
    	return contactsMapper.selectAllByPage(pageRequest, searchParams).getPage();
    }
    
    public List<Contacts> query4Refer(String refParam){
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("refParam", refParam);
    	return contactsMapper.queryList(params);
    }

    /**
     * 保存（插入和更新）
     */
    @Transactional
    public void save(List<Contacts> recordList) {
        List<Contacts> addList = new ArrayList<>(recordList.size());
        List<Contacts> updateList = new ArrayList<>(recordList.size());
        for (Contacts TelBook : recordList) {

            if (TelBook.getId() == null) {
                TelBook.setId(UUID.randomUUID().toString());
                TelBook.setDr(0);
                addList.add(TelBook);
            } else {
                updateList.add(TelBook);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            contactsMapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            contactsMapper.batchUpdate(updateList);
        }
    }

    /**
     * 批量删除
     */
    public void batchDeleteByPrimaryKey(List<Contacts> list) {
        contactsMapper.batchDelete(list);
    }

    /**
     * 根据联系人信息查询所属机构
     *
     * @param userJobPage
     * @return
     */
    public Page<Contacts> selectInstit(Page<Contacts> data) {
        List<Contacts> list = data.getContent();
        Set<String> ids = new HashSet<String>();
        for (Contacts TelBook : list) {
            if (!StringUtil.isEmpty(TelBook.getInstitid())) {
                ids.add(TelBook.getInstitid());
            }
        }
        if (!ids.isEmpty()) {
            List<Organization> instits = this.selectInstitByIds(ids);
            for (Contacts TelBook : list) {
                for (Organization instit : instits) {
                    if (TelBook.getInstitid() != null && TelBook.getInstitid().equalsIgnoreCase(instit.getInstitid())) {
                        TelBook.setInstitname(instit.getInstit_name());
                    }
                }
            }
            return data;
        }
        return null;
    }

    public List<Organization> selectInstitByIds(Set<String> ids) {
        return oranizationMapper.selectInstitByIds(ids);
    }
    
    /***************************************************************/
    private ContactsMapper contactsMapper;
    private OrganizationMapper oranizationMapper;
    
    @Autowired
	public void setContactsMapper(ContactsMapper contactsMapper) {
		this.contactsMapper = contactsMapper;
		super.ibatisMapper = contactsMapper;
	}
    @Autowired
	public void setOranizationMapper(OrganizationMapper oranizationMapper) {
		this.oranizationMapper = oranizationMapper;
	}
    
    
}