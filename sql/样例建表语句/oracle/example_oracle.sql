

-- ----------------------------
-- Table structure for example_contacts
-- ----------------------------
-- DROP TABLE example_contacts;
CREATE TABLE example_contacts (
id NCHAR(36) NOT NULL ,
peocode VARCHAR2(100) NOT NULL ,
peoname VARCHAR2(50) NOT NULL ,
sex VARCHAR2(50) NULL ,
worktel VARCHAR2(100) NULL ,
office VARCHAR2(100) NULL ,
tel VARCHAR2(100) NULL ,
email VARCHAR2(100) NULL ,
countryzone VARCHAR2(36) NULL ,
institid NCHAR(36) NULL ,
institname NCHAR(50) NULL ,
ts DATE NULL ,
dr NUMBER(11) NULL 
)

;

-- ----------------------------
-- Records of example_contacts
-- ----------------------------

-- ----------------------------
-- Table structure for example_dictionary
-- ----------------------------
-- DROP TABLE example_dictionary;
CREATE TABLE example_dictionary (
id VARCHAR2(36) NOT NULL ,
code VARCHAR2(50) NULL ,
name VARCHAR2(50) NULL ,
sys VARCHAR2(50) NULL ,
creator VARCHAR2(50) NULL ,
create_time VARCHAR2(50) NULL ,
remark VARCHAR2(50) NULL 
)

;

-- ----------------------------
-- Records of example_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for example_goods
-- ----------------------------
-- DROP TABLE example_goods;
CREATE TABLE example_goods (
id VARCHAR2(64) NOT NULL ,
goodsCode VARCHAR2(20) NOT NULL ,
goodsName VARCHAR2(64) NOT NULL ,
model VARCHAR2(64) NULL ,
price NUMBER NULL ,
currency VARCHAR2(64) NULL ,
manufacturer VARCHAR2(64) NULL ,
linkman VARCHAR2(64) NULL ,
remark NCLOB NULL ,
version NUMBER(11) NULL ,
createTime DATE NULL ,
createUser VARCHAR2(64) NULL ,
lastModified DATE NULL ,
lastModifyUser VARCHAR2(64) NULL ,
ts DATE NULL ,
dr NUMBER(11) NULL 
)

;
COMMENT ON COLUMN example_goods.id IS '主键ID';
COMMENT ON COLUMN example_goods.goodsCode IS '商品编码';
COMMENT ON COLUMN example_goods.goodsName IS '商品名称';
COMMENT ON COLUMN example_goods.model IS '型号';
COMMENT ON COLUMN example_goods.price IS '单价';
COMMENT ON COLUMN example_goods.currency IS '币种';
COMMENT ON COLUMN example_goods.manufacturer IS '制造商';
COMMENT ON COLUMN example_goods.linkman IS '联系人';
COMMENT ON COLUMN example_goods.remark IS '备注';
COMMENT ON COLUMN example_goods.version IS '版本';
COMMENT ON COLUMN example_goods.createTime IS '创建时间';
COMMENT ON COLUMN example_goods.createUser IS '创建人';
COMMENT ON COLUMN example_goods.lastModified IS '修改时间';
COMMENT ON COLUMN example_goods.lastModifyUser IS '修改人';
COMMENT ON COLUMN example_goods.ts IS '数据时间';
COMMENT ON COLUMN example_goods.dr IS '删除标志：0-可用；1-已删除';

-- ----------------------------
-- Records of example_goods
-- ----------------------------

-- ----------------------------
-- Table structure for example_order_bill
-- ----------------------------
-- DROP TABLE example_order_bill;
CREATE TABLE example_order_bill (
id NCHAR(36) NOT NULL ,
orderCode VARCHAR2(50) NULL ,
orderName VARCHAR2(50) NULL ,
orderDate DATE NULL ,
beginDate DATE NULL ,
endDate DATE NULL ,
customer VARCHAR2(255) NULL ,
dept VARCHAR2(36) NULL ,
busiman VARCHAR2(50) NULL ,
amount NUMBER NULL ,
currency VARCHAR2(36) NULL ,
orderState VARCHAR2(10) NULL ,
version NUMBER(11) NULL ,
createTime DATE NULL ,
createUser VARCHAR2(36) NULL ,
lastModified DATE NULL ,
lastModifyUser VARCHAR2(36) NULL ,
ts DATE NULL ,
dr NUMBER(11) NULL,
tenant_id varchar2(32)
)

;
COMMENT ON COLUMN example_order_bill.id IS '主键ID';
COMMENT ON COLUMN example_order_bill.orderCode IS '订单编号';
COMMENT ON COLUMN example_order_bill.orderName IS '订单名称';
COMMENT ON COLUMN example_order_bill.orderDate IS '订单日期';
COMMENT ON COLUMN example_order_bill.beginDate IS '开始时间';
COMMENT ON COLUMN example_order_bill.endDate IS '结束时间';
COMMENT ON COLUMN example_order_bill.customer IS '订单客户';
COMMENT ON COLUMN example_order_bill.dept IS '组织机构';
COMMENT ON COLUMN example_order_bill.busiman IS '业务员';
COMMENT ON COLUMN example_order_bill.amount IS '订单金额';
COMMENT ON COLUMN example_order_bill.currency IS '币种';
COMMENT ON COLUMN example_order_bill.orderState IS '订单状态';
COMMENT ON COLUMN example_order_bill.version IS '版本号';
COMMENT ON COLUMN example_order_bill.createTime IS '创建时间';
COMMENT ON COLUMN example_order_bill.createUser IS '创建人';
COMMENT ON COLUMN example_order_bill.lastModified IS '最后修改时间';
COMMENT ON COLUMN example_order_bill.lastModifyUser IS '最后修改人';
COMMENT ON COLUMN example_order_bill.ts IS '数据时间';
COMMENT ON COLUMN example_order_bill.dr IS '删除标志：0-未删除；1-已删除';

-- ----------------------------
-- Records of example_order_bill
-- ----------------------------

-- ----------------------------
-- Table structure for example_order_detail
-- ----------------------------
-- DROP TABLE example_order_detail;
CREATE TABLE example_order_detail (
id NCHAR(36) NOT NULL ,
orderId NCHAR(36) NULL ,
detailCode VARCHAR2(50) NULL ,
goodsCode VARCHAR2(50) NULL ,
goodsName VARCHAR2(50) NULL ,
manufacturer VARCHAR2(64) NULL ,
price NUMBER NULL ,
total NUMBER(11) NULL ,
amount NUMBER NULL ,
remark NCLOB NULL ,
VERSION NUMBER(11) NULL ,
ts DATE NULL ,
dr NUMBER(11) NULL 
)

;
COMMENT ON COLUMN example_order_detail.id IS '订单明细ID';
COMMENT ON COLUMN example_order_detail.orderId IS '订单ID';
COMMENT ON COLUMN example_order_detail.detailCode IS '订单明细编号';
COMMENT ON COLUMN example_order_detail.goodsCode IS '商品编号';
COMMENT ON COLUMN example_order_detail.goodsName IS '商品名称';
COMMENT ON COLUMN example_order_detail.manufacturer IS '制造商';
COMMENT ON COLUMN example_order_detail.price IS '单价';
COMMENT ON COLUMN example_order_detail.total IS '数量';
COMMENT ON COLUMN example_order_detail.amount IS '总价';
COMMENT ON COLUMN example_order_detail.VERSION IS '版本号';
COMMENT ON COLUMN example_order_detail.ts IS '数据时间';
COMMENT ON COLUMN example_order_detail.dr IS '删除标志：0-未删除；1-已删除';

-- ----------------------------
-- Records of example_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for example_organization
-- ----------------------------
-- DROP TABLE example_organization;
CREATE TABLE example_organization (
institid NCHAR(36) NOT NULL ,
instit_code VARCHAR2(200) NOT NULL ,
instit_name VARCHAR2(200) NOT NULL ,
short_name VARCHAR2(50) NULL ,
email VARCHAR2(50) NULL ,
instit_type VARCHAR2(10) NULL ,
parent_id VARCHAR2(36) NULL ,
creator VARCHAR2(20) NULL ,
creationtime DATE NULL ,
ts DATE NULL ,
dr NUMBER(11) NULL 
)

;

-- ----------------------------
-- Records of example_organization
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table example_contacts
-- ----------------------------
ALTER TABLE example_contacts ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table example_dictionary
-- ----------------------------
ALTER TABLE example_dictionary ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table example_goods
-- ----------------------------
ALTER TABLE example_goods ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table example_order_bill
-- ----------------------------
ALTER TABLE example_order_bill ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table example_order_detail
-- ----------------------------
ALTER TABLE example_order_detail ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table example_organization
-- ----------------------------
ALTER TABLE example_organization ADD PRIMARY KEY (institid);

CREATE TABLE example_equip (
id VARCHAR2(36) NOT NULL ,
code VARCHAR2(255) NULL ,
name VARCHAR2(255) NULL ,
org_id VARCHAR2(255) NULL ,
org_name VARCHAR2(255) NULL ,
dept_id VARCHAR2(255) NULL ,
dept_name VARCHAR2(255) NULL ,
create_user_id VARCHAR2(36) NULL ,
create_user_name VARCHAR2(255) NULL ,
update_user_id VARCHAR2(255) NULL ,
update_user_name VARCHAR2(255) NULL ,
create_time DATE NULL ,
update_time DATE NULL ,
version NUMBER(11) NULL ,
tenant_id VARCHAR2(36) NULL
);
ALTER TABLE example_equip ADD PRIMARY KEY (id);

CREATE TABLE example_bill (
id VARCHAR2(36) NOT NULL ,
code VARCHAR2(255) NULL ,
bill_type VARCHAR2(255) NULL ,
is_valid VARCHAR2(10) NULL ,
fiscal VARCHAR2(10) NULL ,
busi_date DATE NULL ,
pay_code VARCHAR2(255) NULL ,
pay_name VARCHAR2(255) NULL ,
in_code VARCHAR2(255) NULL ,
in_name VARCHAR2(255) NULL ,
amount NUMBER NULL ,
bill_status VARCHAR2(10) NULL ,
create_user_id VARCHAR2(36) NULL ,
create_user_name VARCHAR2(36) NULL ,
create_time DATE NULL ,
update_user_id VARCHAR2(36) NULL ,
update_user_name VARCHAR2(255) NULL ,
update_time DATE NULL ,
version VARCHAR2(255) NULL
);
ALTER TABLE example_bill ADD PRIMARY KEY (id);
COMMENT ON COLUMN example_bill.code IS '编码';
COMMENT ON COLUMN example_bill.bill_type IS '单据类型';
COMMENT ON COLUMN example_bill.is_valid IS '是否有效';
COMMENT ON COLUMN example_bill.fiscal IS '年度';
COMMENT ON COLUMN example_bill.busi_date IS '业务时间';
COMMENT ON COLUMN example_bill.pay_code IS '付款单位code';
COMMENT ON COLUMN example_bill.pay_name IS '付款单位';
COMMENT ON COLUMN example_bill.in_code IS '收款单位code';
COMMENT ON COLUMN example_bill.in_name IS '收款单位';
COMMENT ON COLUMN example_bill.amount IS '金额';
COMMENT ON COLUMN example_bill.bill_status IS '单据状态';
COMMENT ON COLUMN example_bill.create_user_id IS '创建人ID';
COMMENT ON COLUMN example_bill.create_user_name IS '创建人';
COMMENT ON COLUMN example_bill.create_time IS '创建时间';
COMMENT ON COLUMN example_bill.update_user_id IS '更新人ID';
COMMENT ON COLUMN example_bill.update_user_name IS '更新人';
COMMENT ON COLUMN example_bill.update_time IS '更新时间';
COMMENT ON COLUMN example_bill.version IS '版本';

CREATE TABLE example_customer (
id VARCHAR2(36) NOT NULL ,
customer_code VARCHAR2(255) NULL ,
customer_name VARCHAR2(255) NULL ,
province VARCHAR2(255) NULL ,
city VARCHAR2(255) NULL ,
corpSize NUMBER(4) NULL ,
status NUMBER(4) NULL
);
ALTER TABLE example_customer ADD PRIMARY KEY (id);
COMMENT ON COLUMN example_customer.customer_code IS '客户编码';
COMMENT ON COLUMN example_customer.customer_name IS '客户名称';
COMMENT ON COLUMN example_customer.province IS '省份';
COMMENT ON COLUMN example_customer.city IS '城市';
COMMENT ON COLUMN example_customer.corpSize IS '企业规模';
COMMENT ON COLUMN example_customer.status IS '状态';

CREATE TABLE example_as_val (
id VARCHAR2(36) NOT NULL ,
pid VARCHAR2(36) NOT NULL ,
code VARCHAR2(255) NULL ,
name VARCHAR2(255) NOT NULL ,
value VARCHAR2(255) NOT NULL ,
ord_index NUMBER(4) NULL ,
lstdate DATE NULL ,
is_system NUMBER(4) NULL
);
ALTER TABLE example_as_val ADD PRIMARY KEY (id);
COMMENT ON COLUMN example_as_val.pid IS '编码';
COMMENT ON COLUMN example_as_val.name IS '下拉框NAME';
COMMENT ON COLUMN example_as_val.value IS '下拉框VALUE';
COMMENT ON COLUMN example_as_val.ord_index IS '顺序';
COMMENT ON COLUMN example_as_val.lstdate IS '最后更新时间';
COMMENT ON COLUMN example_as_val.is_system IS '是否系统预置';
