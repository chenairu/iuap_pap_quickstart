CREATE TABLE example_instit (
  institid varchar2(36) PRIMARY KEY,
  instit_code varchar2(200) NOT NULL,
  instit_name varchar2(200) NOT NULL,
  short_name varchar2(50) DEFAULT NULL,
  email varchar2(50) DEFAULT NULL,
  instit_type varchar2(10) DEFAULT NULL,
  parent_id varchar2(36) DEFAULT NULL,
  creator varchar2(20) DEFAULT NULL,
  creationtime DATE DEFAULT NULL,
  ts DATE DEFAULT NULL,
  dr INT DEFAULT '0'
);


CREATE TABLE example_record (
  id varchar2(36) PRIMARY KEY,
  CODE varchar2(50) DEFAULT NULL,
  NAME varchar2(50) DEFAULT NULL,
  sys varchar2(50) DEFAULT NULL,
  creator varchar2(50) DEFAULT NULL,
  create_time varchar2(50) DEFAULT NULL,
  remark varchar2(50) DEFAULT NULL
);



CREATE TABLE example_telbook (
  id varchar2(36) PRIMARY KEY,
  peocode varchar2(100) NOT NULL,
  peoname varchar2(50) NOT NULL,
  sex varchar2(50) DEFAULT NULL,
  worktel varchar2(100) DEFAULT NULL,
  office varchar2(100) DEFAULT NULL,
  tel varchar2(100) DEFAULT NULL,
  email varchar2(100) DEFAULT NULL,
  countryzone varchar2(36) DEFAULT NULL,
  institid varchar2(36) DEFAULT NULL,
  institname varchar2(50) DEFAULT NULL,
  ts DATE DEFAULT NULL,
  dr INT DEFAULT '0'
);