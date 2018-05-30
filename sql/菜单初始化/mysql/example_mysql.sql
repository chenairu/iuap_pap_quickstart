

-- 清除历史遗留数据
delete from WB_AREAS where id='AREAdemo0001';
delete from wb_app_groups where id='groupdemo0001';
delete from wb_app_groups where id='groupdemo0002';

delete from wb_app_menu where id ='21fad9f7c7284e13839e2ebf20f3917a';
delete from wb_app_menu where id ='6577091c7cbc43dfb646545405e8fe70';
delete from wb_app_menu where id ='84e28bbfacef46e39cf8ab0a15b791cf';
delete from wb_app_menu where id ='0311f243d0a840f6a89c1fb6aaa79286';

delete from WB_APP_APPS where id='6ffad138bde848a2881fc0e599017662';
delete from WB_LABEL_RELATION where id='6d6d269e24344a87a39eb68ee13be925';
delete from IEOP_ROLE_PERMISSION where id='25ef2da0836246f9ae7c9871faaee4b4';
delete from wb_app_menu where id='5221a69c3971496b9706ca2a195372d6';

delete from WB_APP_APPS where id='0435a5190324442ab0c850b92766521c';
delete from WB_LABEL_RELATION where id='673af20cc95b4029a26e558eac009303';
delete from IEOP_ROLE_PERMISSION where id='ff62df759d6d4ff3ac2242c3c7d8e868';
delete from wb_app_menu where id='e83878639b94405c844eebc0cfcca3d7';

delete from WB_APP_APPS where id='2b62827789144e89924f6986266be6b3';
delete from WB_LABEL_RELATION where id='2be0710c27c94887b12f70b37faa00e5';
delete from IEOP_ROLE_PERMISSION where id='c8f220686c864ae780fee790bdb8548e';
delete from wb_app_menu where id='65b43f2f77904e64ae52444b282155a0';


-- 首页
INSERT INTO WB_AREAS VALUES ('AREAdemo0001', '010099', '示例模块', 'Y', '9', 'tenant');

insert into wb_app_groups values ('groupdemo0001', '经典UI模式', '1', 'areademo0001', 'tenant');
insert into wb_app_groups values ('groupdemo0002', '示例节点', '1', 'areademo0001', 'tenant');

INSERT INTO wb_app_menu (ID, FUNC_ID, ICON, ISENABLE, ISVISIBLE, IS_VIRTUAL_NODE, PARENT_ID, CLASSIFY, LAYOUT_ID, SORT, CREATE_TIME, NAME, TENANT_ID, VERSION, LABEL, TS, DR, OPENVIEW) VALUES ('21fad9f7c7284e13839e2ebf20f3917a', 'uitest', 'iconfont icon-component red    ', 'Y', 'Y', 'Y', 'M0000000000001', NULL, 'null', '1', DATE_FORMAT('2018-04-13 09:54:43', '%Y-%m-%d %H:%i:%S'), '经典UI模式', 'tenant', '9', 'common', NULL, NULL, 'curnpage');
INSERT INTO wb_app_menu (ID, FUNC_ID, ICON, ISENABLE, ISVISIBLE, IS_VIRTUAL_NODE, PARENT_ID, CLASSIFY, LAYOUT_ID, SORT, CREATE_TIME, NAME, TENANT_ID, VERSION, LABEL, TS, DR, OPENVIEW) VALUES ('6577091c7cbc43dfb646545405e8fe70', 'bd', 'iconfont icon-bookmarks blue       ', 'Y', 'Y', 'Y', '21fad9f7c7284e13839e2ebf20f3917a', NULL, 'null', '1', DATE_FORMAT('2018-04-16 13:05:10', '%Y-%m-%d %H:%i:%S'), '基本档案', 'tenant', '1', 'common', NULL, NULL, 'curnpage');
INSERT INTO wb_app_menu (ID, FUNC_ID, ICON, ISENABLE, ISVISIBLE, IS_VIRTUAL_NODE, PARENT_ID, CLASSIFY, LAYOUT_ID, SORT, CREATE_TIME, NAME, TENANT_ID, VERSION, LABEL, TS, DR, OPENVIEW) VALUES ('84e28bbfacef46e39cf8ab0a15b791cf', 'bill', ' iconfont icon-appicon ', 'Y', 'Y', 'Y', '21fad9f7c7284e13839e2ebf20f3917a', NULL, 'null', '2', DATE_FORMAT('2018-04-16 13:08:52', '%Y-%m-%d %H:%i:%S'), '业务单据', 'tenant', '2', NULL, NULL, NULL, 'curnpage');
INSERT INTO wb_app_menu (ID, FUNC_ID, ICON, ISENABLE, ISVISIBLE, IS_VIRTUAL_NODE, PARENT_ID, CLASSIFY, LAYOUT_ID, SORT, CREATE_TIME, NAME, TENANT_ID, VERSION, LABEL, TS, DR, OPENVIEW) VALUES ('0311f243d0a840f6a89c1fb6aaa79286', 'demo', 'iconfont icon-appicon deep-orange', 'Y', 'Y', 'Y', 'M0000000000001', NULL, 'null', '2', DATE_FORMAT('2018-04-13 09:54:43', '%Y-%m-%d %H:%i:%S'), '示例节点', 'tenant', '1', 'common', NULL, NULL, 'curnpage');


--  档案
INSERT INTO WB_APP_APPS (ID, APP_NAME, APP_INDEX, GROUP_ID, DOMAIN_ID, URL, APP_CHINESE, APP_DESC, APP_ICON, APP_GROUPCODE, APP_CODE, DYNA_URL, TENANT_ID, URLTYPE, VERSION, SYSTEM, LABEL, SHOWWAY, CREATOR, REVISER, CREATE_DATE, MODIFY_DATE) VALUES ('6ffad138bde848a2881fc0e599017662', '档案', NULL, 'groupdemo0001', 'AREAdemo0001', '/iuap-example/pages/record/exampleEquip.js', NULL, NULL, 'iconfont icon-formthree blue       ', NULL, '01dict', NULL, 'tenant', 'plugin', '1', 'wbalone', NULL, 'apparea', 'U001', NULL, DATE_FORMAT('2018-03-31 14:02:53', '%Y-%m-%d %H:%i:%S'), NULL);
INSERT INTO WB_LABEL_RELATION (ID, LABELCODE, SOURCECODE, CREATE_DATE, TENANT_ID, SYSTEM, LABLEID, SOURCEID, TYPE) VALUES ('6d6d269e24344a87a39eb68ee13be925', 'buisource', '01dict', NULL, 'tenant', NULL, NULL, '6ffad138bde848a2881fc0e599017662', 'app');
INSERT INTO IEOP_ROLE_PERMISSION (ID, ROLE_ID, ROLE_CODE, PERMISSION_ID, PERMISSION_CODE, PERMISSION_TYPE, TENANT_ID, SYS_ID) VALUES ('25ef2da0836246f9ae7c9871faaee4b4', 'R001', 'admin', '6ffad138bde848a2881fc0e599017662', '01dict', '1', 'tenant', NULL);

INSERT INTO wb_app_menu (ID, FUNC_ID, ICON, ISENABLE, ISVISIBLE, IS_VIRTUAL_NODE, PARENT_ID, CLASSIFY, LAYOUT_ID, SORT, CREATE_TIME, NAME, TENANT_ID, VERSION, LABEL, TS, DR, OPENVIEW) VALUES ('5221a69c3971496b9706ca2a195372d6', 'bd01', 'iconfont icon-formthree blue       ', 'Y', 'Y', 'N', '6577091c7cbc43dfb646545405e8fe70', NULL, '01dict', '1', DATE_FORMAT('2018-04-13 09:55:14', '%Y-%m-%d %H:%i:%S'), '档案', 'tenant', '2', NULL, NULL, NULL, 'curnpage');

--  树表
INSERT INTO WB_APP_APPS (ID, APP_NAME, APP_INDEX, GROUP_ID, DOMAIN_ID, URL, APP_CHINESE, APP_DESC, APP_ICON, APP_GROUPCODE, APP_CODE, DYNA_URL, TENANT_ID, URLTYPE, VERSION, SYSTEM, LABEL, SHOWWAY, CREATOR, REVISER, CREATE_DATE, MODIFY_DATE) VALUES ('0435a5190324442ab0c850b92766521c', '树表', NULL, 'groupdemo0001', 'AREAdemo0001', '/iuap-example/pages/contacts/contacts.js', NULL, NULL, 'iconfont icon-organizationicon deep-purple', NULL, '01address', NULL, 'tenant', 'plugin', '1', 'wbalone', NULL, 'apparea', 'U001', NULL, DATE_FORMAT('2018-03-31 13:48:58', '%Y-%m-%d %H:%i:%S'), NULL);
INSERT INTO WB_LABEL_RELATION (ID, LABELCODE, SOURCECODE, CREATE_DATE, TENANT_ID, SYSTEM, LABLEID, SOURCEID, TYPE) VALUES ('673af20cc95b4029a26e558eac009303', 'buisource', '01address', NULL, 'tenant', NULL, NULL, '0435a5190324442ab0c850b92766521c', 'app');
INSERT INTO IEOP_ROLE_PERMISSION (ID, ROLE_ID, ROLE_CODE, PERMISSION_ID, PERMISSION_CODE, PERMISSION_TYPE, TENANT_ID, SYS_ID) VALUES ('ff62df759d6d4ff3ac2242c3c7d8e868', 'R001', 'admin', '0435a5190324442ab0c850b92766521c', '01address', '1', 'tenant', NULL);
INSERT INTO wb_app_menu (ID, FUNC_ID, ICON, ISENABLE, ISVISIBLE, IS_VIRTUAL_NODE, PARENT_ID, CLASSIFY, LAYOUT_ID, SORT, CREATE_TIME, NAME, TENANT_ID, VERSION, LABEL, TS, DR, OPENVIEW) VALUES ('e83878639b94405c844eebc0cfcca3d7', 'bd02', 'iconfont icon-organizationicon deep-purple', 'Y', 'Y', 'N', '6577091c7cbc43dfb646545405e8fe70', NULL, '01address', '2', DATE_FORMAT('2018-04-16 13:00:17', '%Y-%m-%d %H:%i:%S'), '树表', 'tenant', '0', NULL, NULL, NULL, 'curnpage');

-- 主子表
INSERT INTO WB_APP_APPS (ID, APP_NAME, APP_INDEX, GROUP_ID, DOMAIN_ID, URL, APP_CHINESE, APP_DESC, APP_ICON, APP_GROUPCODE, APP_CODE, DYNA_URL, TENANT_ID, URLTYPE, VERSION, SYSTEM, LABEL, SHOWWAY, CREATOR, REVISER, CREATE_DATE, MODIFY_DATE) VALUES ('2b62827789144e89924f6986266be6b3', '主子表', NULL, 'groupdemo0001', 'AREAdemo0001', '/iuap-example/pages/ygdemo_yw_infonew/ygdemo_yw_info.js', NULL, NULL, 'iconfont icon-phone light-blue ', NULL, '01dubannew', NULL, 'tenant', 'plugin', '0', 'wbalone', NULL, 'apparea', 'U001', NULL, DATE_FORMAT('2018-04-03 15:10:41', '%Y-%m-%d %H:%i:%S'), NULL);
INSERT INTO WB_LABEL_RELATION (ID, LABELCODE, SOURCECODE, CREATE_DATE, TENANT_ID, SYSTEM, LABLEID, SOURCEID, TYPE) VALUES ('2be0710c27c94887b12f70b37faa00e5', 'buisource', '01dubannew', DATE_FORMAT('2018-04-03 15:10:41', '%Y-%m-%d %H:%i:%S'), 'tenant', NULL, NULL, '2b62827789144e89924f6986266be6b3', 'app');
INSERT INTO IEOP_ROLE_PERMISSION (ID, ROLE_ID, ROLE_CODE, PERMISSION_ID, PERMISSION_CODE, PERMISSION_TYPE, TENANT_ID, SYS_ID) VALUES ('c8f220686c864ae780fee790bdb8548e', 'R001', 'admin', '2b62827789144e89924f6986266be6b3', '01dubannew', '1', 'tenant', NULL);
INSERT INTO wb_app_menu (ID, FUNC_ID, ICON, ISENABLE, ISVISIBLE, IS_VIRTUAL_NODE, PARENT_ID, CLASSIFY, LAYOUT_ID, SORT, CREATE_TIME, NAME, TENANT_ID, VERSION, LABEL, TS, DR, OPENVIEW) VALUES ('65b43f2f77904e64ae52444b282155a0', 'bill01', 'iconfont icon-phone blue       ', 'Y', 'Y', 'N', '84e28bbfacef46e39cf8ab0a15b791cf', NULL, '01dubannew', '1', DATE_FORMAT('2018-04-13 09:55:38', '%Y-%m-%d %H:%i:%S'), '主子表', 'tenant', '5', NULL, NULL, NULL, 'curnpage');
