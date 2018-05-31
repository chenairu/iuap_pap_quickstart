package com.yonyou.iuap.example.commons;

import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;

import java.util.*;

/**
 * com.yonyou.iuap.example.commons
 *
 * @author guoxh
 * @date 2018/5/29 17:21
 * @description
 */
public class CommonUtils {

    public static String buildPermSql(Map<String,String> fieldDataPermResTypeMap) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(String.format(" and %s='%s'", CommonConstants.TENANT_FiELEDNAME, InvocationInfoProxy.getTenantid()));

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

    private static Set<String> getAuthData(String resourceTypeCode) {
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
}
