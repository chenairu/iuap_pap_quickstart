package com.yonyou.iuap.example.template.service.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;

public abstract class ParentService<TEntity extends BaseEntity> {
	
	private Logger logger = LoggerFactory.getLogger(ParentService.class);
	
	public Page<TEntity> selectAllByPage(PageRequest pageRequest, SearchParams searchParams, Class clazz) {
		return selectAllByPage(pageRequest, searchParams, clazz, getFieldDataPermResTypeMap());
	}
	
	public Page<TEntity> selectAllByPage(PageRequest pageRequest, SearchParams searchParams, Class clazz, Map<String, String> fieldDataPermResTypeMap) {
		Page<TEntity> pageResult = selectDataByPage(pageRequest, searchParams, clazz, fieldDataPermResTypeMap);
		setRefName(pageResult);
		return pageResult;
	}
	
	/**
	 * 字段名称与数据权限资源类型名称的对应关系
	 * @return
	 */
	protected Map<String, String> getFieldDataPermResTypeMap(){
		Map<String, String> map = new HashMap<String, String>();
		processFieldDataPermResTypeMap(map);
		return map;
	}
	
	public Page<TEntity> selectDataByPage(PageRequest pageRequest, SearchParams searchParams, Class clazz, Map<String, String> fieldDataPermResTypeMap) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT * FROM " + FastBeanHelper.getTableName(clazz) + " ");
        
		sqlBuilder.append(" where 1=1 ");
		
		String TENANT_ID = FastBeanHelper.getColumn(clazz, CommonConstants.TENANT_FiELEDNAME);
		if(TENANT_ID != null){			
			sqlBuilder.append(String.format(" and %s='%s'", CommonConstants.TENANT_FiELEDNAME, InvocationInfoProxy.getTenantid()));
		}
        
    	if(fieldDataPermResTypeMap != null && !fieldDataPermResTypeMap.isEmpty()){
    		Set<String> keySet = fieldDataPermResTypeMap.keySet();
    		Map<String, Set<String>> resTypeDataPermMap = new HashMap<String, Set<String>>();
    		
    		for (String columnname : keySet) {
				String resourceTypeCode = fieldDataPermResTypeMap.get(columnname);
				Set<String> set = resTypeDataPermMap.get(resourceTypeCode);
				
				if(set == null){
					set = getAuthData(resourceTypeCode);
					resTypeDataPermMap.put(resourceTypeCode, set);
				}
				
				if(!set.isEmpty()){					
					sqlBuilder.append(" and ").append(columnname).append(" in (");
					
					for (String param : set) {						
						sqlBuilder.append(String.format("'%s',", param));
					}
					
					sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
					sqlBuilder.append(")");
				}
			}
    	}
    	
    	String querySql = sqlBuilder.toString();
    	
    	return selectAllByPage(pageRequest, searchParams, querySql);
	}
	
	private Set<String> getAuthData(String resourceTypeCode) {
		String tenantId = InvocationInfoProxy.getTenantid();
		String sysId = InvocationInfoProxy.getSysid();
		String userId = InvocationInfoProxy.getUserid();
		
		List<DataPermission> dataPerms = AuthRbacClient.getInstance().queryDataPerms(tenantId, sysId, userId, resourceTypeCode);
		
		Set<String> set = new HashSet<String>();
		for (Iterator<DataPermission> iterator = dataPerms.iterator(); iterator.hasNext();) {
			DataPermission dataPermission = (DataPermission) iterator.next();
			set.add(dataPermission.getResourceId());
		}
		
		return set;
	}
	
	protected Page<TEntity> selectAllByPage(PageRequest pageRequest, SearchParams searchParams, String sql){
		return null;
	}
	
	/**
	 * 设置字段名称与数据权限资源类型名称的对应关系
	 * @param fieldDataPermResTypeMapPageRequest pageRequest
	 */
	protected void processFieldDataPermResTypeMap(Map<String, String> fieldDataPermResTypeMap){
		
	}
	
	protected Page<TEntity> setRefName(Page<TEntity> pageResult){
		return pageResult;
	}

}
