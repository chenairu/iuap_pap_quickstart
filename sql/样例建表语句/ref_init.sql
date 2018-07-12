-- 初始化应用平台参照配置,如果未进行下面的配置可能导致参照加载不到数据
insert into REF_REFINFO (PK_REF, REFNAME, REFCODE, REFCLASS, REFURL, MD_ENTITYPK, PRODUCTTYPE, TENANTID)
values ('common_ref', '通用树表参照', 'common_ref', null, '/iuap_pap_quickstart/common/ref/', null, null, null);
 