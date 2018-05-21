-- ----------------------------
-- Table structure for pt_layout
-- ----------------------------
DROP TABLE IF EXISTS `pt_layout`;
CREATE TABLE `pt_layout` (
  `templateid` varchar(150) default NULL COMMENT '布局模板编码',
  `setting` text COMMENT '内容',
  `pk_layout` varchar(22) NOT NULL COMMENT '主键',
  `id` varchar(100) NOT NULL COMMENT '编码',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `ctime` datetime default NULL,
  `parentid` varchar(100) default NULL,
  `userid` varchar(50) default NULL,
  `system` varchar(50) default NULL COMMENT '系统',
  `tenant` varchar(50) default NULL COMMENT '租户',
  `isdefault` char(255) default NULL,
  `modifytime` datetime default NULL,
  `wlevel` varchar(30) default NULL,
  `isenable` char(1) default NULL,
  `rnd` varchar(50) default NULL,
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  PRIMARY KEY  (`pk_layout`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_layout_template
-- ----------------------------
DROP TABLE IF EXISTS `pt_layout_template`;
CREATE TABLE `pt_layout_template` (
  `id` varchar(50) NOT NULL COMMENT '布局模板编码',
  `name` varchar(150) NOT NULL COMMENT '名称',
  `tpl` text NOT NULL COMMENT '模板内容',
  `pk_layout_template` varchar(22) NOT NULL,
  `i18n` varchar(100) default NULL COMMENT '多语',
  `system` varchar(50) default NULL COMMENT '系统',
  `tenant` varchar(50) default NULL COMMENT '租户',
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  PRIMARY KEY  (`pk_layout_template`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_widget
-- ----------------------------
DROP TABLE IF EXISTS `pt_widget`;
CREATE TABLE `pt_widget` (
  `id` varchar(200) default NULL COMMENT '编码',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `descr` varchar(255) default NULL COMMENT '描述',
  `category` varchar(150) NOT NULL COMMENT '类别',
  `wtype` varchar(50) NOT NULL COMMENT '类型',
  `url` varchar(300) NOT NULL,
  `pk_widget` varchar(22) NOT NULL,
  `isenable` char(1) default NULL COMMENT '是否启用',
  `system` varchar(50) default NULL,
  `tenant` varchar(50) default NULL,
  `ctime` datetime default NULL,
  `cnf` text,
  `modifytime` datetime default NULL,
  `setting` text COMMENT '内容',
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  `wlevel` varchar(30) default NULL,
  `tag` varchar(50) default NULL,
  PRIMARY KEY  (`pk_widget`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_widget_category
-- ----------------------------
DROP TABLE IF EXISTS `pt_widget_category`;
CREATE TABLE `pt_widget_category` (
  `id` varchar(100) NOT NULL COMMENT '编码',
  `pk_category` varchar(22) NOT NULL COMMENT '主键',
  `name` varchar(200) NOT NULL,
  `i18n` varchar(100) default NULL,
  `isenable` char(1) NOT NULL default '1' COMMENT '是否启用',
  `sort` int(10) default NULL COMMENT '序号',
  `system` varchar(50) default NULL COMMENT '系统',
  `tenant` varchar(50) default NULL COMMENT '租户',
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  PRIMARY KEY  (`pk_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for pt_widget_preference
-- ----------------------------
DROP TABLE IF EXISTS `pt_widget_preference`;
CREATE TABLE `pt_widget_preference` (
  `widgetid` varchar(150) NOT NULL,
  `viewid` varchar(150) NOT NULL COMMENT 'perference的编码',
  `id` varchar(100) NOT NULL,
  `name` varchar(150) default NULL,
  `value` varchar(150) default NULL,
  `ctime` datetime default NULL,
  `pk_widget_preference` varchar(22) NOT NULL,
  `userid` varchar(22) default NULL,
  `system` varchar(50) default NULL COMMENT '系统',
  `tenant` varchar(50) default NULL COMMENT '租户',
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  `wstatus` int(255) default NULL,
  PRIMARY KEY  (`pk_widget_preference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_theme
-- ----------------------------
DROP TABLE IF EXISTS `pt_theme`;
CREATE TABLE `pt_theme` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) default NULL,
  `isdefault` char(255) default NULL,
  `ctime` datetime default NULL,
  `pk_theme` varchar(22) NOT NULL,
  `system` varchar(50) default NULL COMMENT '系统',
  `tenant` varchar(50) default NULL COMMENT '租户',
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  PRIMARY KEY  (`pk_theme`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_theme_preference
-- ----------------------------
DROP TABLE IF EXISTS `pt_theme_preference`;
CREATE TABLE `pt_theme_preference` (
  `userid` varchar(50) NOT NULL,
  `themeid` varchar(100) NOT NULL,
  `ctime` datetime default NULL,
  `pk_theme_preference` varchar(22) NOT NULL,
  `system` varchar(50) default NULL COMMENT '系统',
  `tenant` varchar(50) default NULL COMMENT '租户',
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  PRIMARY KEY  (`pk_theme_preference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_more_menu
-- ----------------------------
DROP TABLE IF EXISTS `pt_more_menu`;
CREATE TABLE `pt_more_menu` (
  `code` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `name2` varchar(100) NULL,
  `name3` varchar(100) NULL,
  `name4` varchar(100) NULL,
  `name5` varchar(100) NULL,
  `name6` varchar(100) NULL,
  `url` varchar(300) NOT NULL,
  `url_type` varchar(10) NOT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `sort` int(255) DEFAULT NULL,
  `tag` varchar(50) DEFAULT NULL,
  `pk_more_menu` varchar(50) NOT NULL,
  `isenable` char(1) DEFAULT NULL,
  `system` varchar(50) DEFAULT NULL,
  `tenant` varchar(50) DEFAULT NULL,
  `ts` datetime default NULL,
  `dr` int(255) default NULL,
  PRIMARY KEY (`pk_more_menu`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;