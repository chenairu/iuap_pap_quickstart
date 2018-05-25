package com.yonyou.iuap.example.reference.goods.service;

import java.util.*;

import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.reference.goods.dao.GoodsMapper;
import com.yonyou.iuap.example.reference.goods.entity.Goods;

@Service
public class GoodsService extends GenericService<Goods>{


    public void delete(List<String> ids) {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("ids", ids);
    	goodsMapper.delete(params);
    }
    public Page<Goods> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return goodsMapper.selectAllByPage(pageRequest, searchParams,buildPermSql()).getPage();
    }


	/**
	 * 构造数据权限SQL
	 * @return
	 */
	public String buildPermSql() {
		StringBuilder sqlBuilder = new StringBuilder();

		sqlBuilder.append(String.format(" and %s='%s'", CommonConstants.TENANT_FiELEDNAME, InvocationInfoProxy.getTenantid()));

		HashMap<String, String> fieldDataPermResTypeMap = processFieldDataPermResTypeMap();

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

		String querySql = sqlBuilder.toString();

		return querySql;
	}
	/**
	 * 需要支持数据权限的资源
	 * @return
	 */
	private HashMap<String, String> processFieldDataPermResTypeMap(){
		HashMap<String, String> fieldDataPermResTypeMap = new HashMap<String, String>();
		fieldDataPermResTypeMap.put("pk_currtype", "currtype"); //字段与权限资源名称的对应关系
		return fieldDataPermResTypeMap;
	}
	/**
	 * 调用API获取有权限的数据
	 * @param resourceTypeCode
	 * @return
	 */
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
    /***********************************************************/
    private GoodsMapper goodsMapper;

    @Autowired
	public void setGoodsMapper(GoodsMapper goodsMapper) {
    	super.ibatisMapper = goodsMapper;
		this.goodsMapper = goodsMapper;
	}
    
    

}