/*
Navicat MySQL Data Transfer

Source Server         : local_centos
Source Server Version : 50722
Source Host           : 10.6.226.81:3306
Source Database       : iuap

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-05-21 21:54:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for example_customer
-- ----------------------------
DROP TABLE IF EXISTS `example_customer`;
CREATE TABLE `example_customer` (
  `id` varchar(36) NOT NULL,
  `customer_code` varchar(255) DEFAULT NULL COMMENT '客户编码',
  `customer_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `corpSize` tinyint(1) DEFAULT NULL COMMENT '企业规模',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
