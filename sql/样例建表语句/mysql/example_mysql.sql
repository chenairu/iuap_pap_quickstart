
/*************示例应用：档案-字典（单表）***************/
CREATE TABLE example_dictionary (
  id VARCHAR(36) NOT NULL,
  code VARCHAR(50) DEFAULT NULL,
  name VARCHAR(50) DEFAULT NULL,
  sys VARCHAR(50) DEFAULT NULL,
  creator VARCHAR(50) DEFAULT NULL,
  create_time VARCHAR(50) DEFAULT NULL,
  remark VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ;



/*************示例应用：联系人（树表）***************/
CREATE TABLE example_contacts (
  id CHAR(36) NOT NULL,
  peocode VARCHAR(100) NOT NULL,
  peoname VARCHAR(50) NOT NULL,
  sex VARCHAR(50) DEFAULT NULL,
  worktel VARCHAR(100) DEFAULT NULL,
  office VARCHAR(100) DEFAULT NULL,
  tel VARCHAR(100) DEFAULT NULL,
  email VARCHAR(100) DEFAULT NULL,
  countryzone VARCHAR(36) DEFAULT NULL,
  institid CHAR(36) DEFAULT NULL,
  institname CHAR(50) DEFAULT NULL,
  ts DATE DEFAULT NULL,
  dr INT(11) DEFAULT '0',
  PRIMARY KEY (id)
);



/*************示例应用：组织机构（树表---树）***************/
CREATE TABLE example_organization (
  institid CHAR(36) NOT NULL,
  instit_code VARCHAR(200) NOT NULL,
  instit_name VARCHAR(200) NOT NULL,
  short_name VARCHAR(50) DEFAULT NULL,
  email VARCHAR(50) DEFAULT NULL,
  instit_type VARCHAR(10) DEFAULT NULL,
  parent_id VARCHAR(36) DEFAULT NULL,
  creator VARCHAR(20) DEFAULT NULL,
  creationtime DATE DEFAULT NULL,
  ts DATE DEFAULT NULL,
  dr INT(11) DEFAULT '0',
  PRIMARY KEY (institid)
);



/*************示例应用：商品管理（单表+参照使用）***************/
CREATE TABLE example_goods (
  id VARCHAR(64) NOT NULL COMMENT '主键ID',
  goodsCode VARCHAR(20) NOT NULL COMMENT '商品编码',
  goodsName VARCHAR(64) NOT NULL COMMENT '商品名称',
  model VARCHAR(64) DEFAULT NULL COMMENT '型号',
  price DECIMAL(10,0) DEFAULT NULL COMMENT '单价',
  currency VARCHAR(64) DEFAULT NULL COMMENT '币种',
  manufacturer VARCHAR(64) DEFAULT NULL COMMENT '制造商',
  linkman VARCHAR(64) DEFAULT NULL COMMENT '联系人',
  remark VARCHAR(1024) DEFAULT NULL COMMENT '备注',
  version INT(11) DEFAULT '0' COMMENT '版本',
  createTime TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  createUser VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  lastModified TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间',
  lastModifyUser VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  ts TIMESTAMP NULL DEFAULT NULL COMMENT '数据时间',
  dr INT(11) DEFAULT NULL COMMENT '删除标志：0-可用；1-已删除',
  PRIMARY KEY (id)
);


/*************示例应用：订单管理（主子表+航编辑+行编辑参照使用）***************/
CREATE TABLE example_order_bill (
  id CHAR(36) NOT NULL COMMENT '主键ID',
  orderCode VARCHAR(50) DEFAULT NULL COMMENT '订单编号',
  orderName VARCHAR(50) DEFAULT NULL COMMENT '订单名称',
  orderDate DATETIME DEFAULT NULL COMMENT '订单日期',
  beginDate DATE DEFAULT NULL COMMENT '开始时间',
  endDate DATE DEFAULT NULL COMMENT '结束时间',
  customer VARCHAR(255) DEFAULT NULL COMMENT '订单客户',
  dept VARCHAR(36) DEFAULT NULL COMMENT '组织机构',
  busiman VARCHAR(50) DEFAULT NULL COMMENT '业务员',
  amount DECIMAL(10,0) DEFAULT NULL COMMENT '订单金额',
  currency VARCHAR(36) DEFAULT NULL COMMENT '币种',
  orderState VARCHAR(10) DEFAULT NULL COMMENT '订单状态',
  version INT(11) DEFAULT '0' COMMENT '版本号',
  createTime TIMESTAMP(6) NULL DEFAULT NULL COMMENT '创建时间',
  createUser VARCHAR(36) DEFAULT NULL COMMENT '创建人',
  lastModified TIMESTAMP(6) NULL DEFAULT NULL COMMENT '最后修改时间',
  lastModifyUser VARCHAR(36) DEFAULT NULL COMMENT '最后修改人',
  ts TIMESTAMP(6) NULL DEFAULT NULL COMMENT '数据时间',
  dr INT(11) DEFAULT '0' COMMENT '删除标志：0-未删除；1-已删除',
  PRIMARY KEY (id)
);


/*************示例应用：订单管理（子表）***************/
CREATE TABLE example_order_detail (
  id CHAR(36) NOT NULL COMMENT '订单明细ID',
  orderId CHAR(36) DEFAULT NULL COMMENT '订单ID',
  detailCode VARCHAR(50) DEFAULT NULL COMMENT '订单明细编号',
  goodsCode VARCHAR(50) DEFAULT NULL COMMENT '商品编号',
  goodsName VARCHAR(50) DEFAULT NULL COMMENT '商品名称',
  manufacturer VARCHAR(64) DEFAULT NULL COMMENT '制造商',
  price DECIMAL(10,0) DEFAULT NULL COMMENT '单价',
  total INT(4) DEFAULT NULL COMMENT '数量',
  amount DECIMAL(10,2) DEFAULT NULL COMMENT '总价',
  remark VARCHAR(1024) DEFAULT NULL,
  VERSION INT(11) DEFAULT '0' COMMENT '版本号',
  ts TIMESTAMP NULL DEFAULT NULL COMMENT '数据时间',
  dr INT(11) DEFAULT '0' COMMENT '删除标志：0-未删除；1-已删除',
  PRIMARY KEY (id)
);