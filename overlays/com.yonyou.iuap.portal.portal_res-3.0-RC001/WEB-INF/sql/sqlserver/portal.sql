-- ----------------------------
-- Table structure for [pt_layout]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_layout')
DROP TABLE [pt_layout]
GO
CREATE TABLE [pt_layout] (
[templateid] varchar(50) NULL ,
[setting] text NULL ,
[pk_layout] varchar(22) NOT NULL ,
[id] varchar(50) NOT NULL ,
[name] varchar(100) NOT NULL ,
[ctime] datetime NULL ,
[parentid] varchar(50) NULL ,
[userid] varchar(50) NULL ,
[system] varchar(50) NULL ,
[tenant] varchar(50) NULL ,
[isdefault] char(1) NULL ,
[modifytime] datetime NULL ,
[wlevel] char(1) NULL ,
[isenable] char(1) NULL ,
[rnd] varchar(30) NULL ,
[ts] datetime NULL ,
[dr] int NULL ,
)
ALTER TABLE [pt_layout] ADD PRIMARY KEY ([pk_layout])
GO

-- ----------------------------
-- Table structure for [pt_layout_template]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_layout_template')
DROP TABLE [pt_layout_template]
CREATE TABLE [pt_layout_template] (
[id] varchar(50) NOT NULL ,
[name] varchar(100) NOT NULL ,
[tpl] text NOT NULL ,
[pk_layout_template] varchar(22) NOT NULL ,
[i18n] varchar(100) NULL ,
[system] varchar(50) NULL ,
[tenant] varchar(50) NULL ,
[ts] datetime NULL ,
[dr] int NULL ,
)
ALTER TABLE [pt_layout_template] ADD PRIMARY KEY ([pk_layout_template])
GO
-- ----------------------------
-- Table structure for [pt_widget]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_widget')
DROP TABLE [pt_widget]
CREATE TABLE [pt_widget] (
[id] varchar(50) NOT NULL ,
[name] varchar(100) NOT NULL ,
[descr] varchar(300) NULL ,
[category] varchar(50) NOT NULL ,
[wtype] varchar(50) NOT NULL ,
[url] varchar(300) NOT NULL ,
[pk_widget] varchar(22) NOT NULL ,
[isenable] char(1) NULL ,
[system] varchar(50) NULL ,
[tenant] varchar(50) NULL ,
[ctime] datetime NULL ,
[cnf] text NULL ,
[modifytime] datetime NULL,
[setting] text NULL ,
[ts] datetime NULL ,
[dr] int NULL ,
[wlevel] char(1) NULL ,
[tag] varchar(30) NULL ,
)
ALTER TABLE [pt_widget] ADD PRIMARY KEY ([pk_widget])
GO
-- ----------------------------
-- Table structure for [pt_widget_category]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_widget_category')
DROP TABLE [pt_widget_category]
CREATE TABLE [pt_widget_category] (
[id] varchar(50) NOT NULL ,
[pk_category] varchar(22) NOT NULL ,
[name] varchar(100) NOT NULL ,
[i18n] varchar(100) NULL ,
[isenable] char(1) NULL ,
[sort] int NULL ,
[system] varchar(50) NULL ,
[tenant] varchar(50) NULL ,
[ts] datetime NULL ,
[dr] int NULL ,
)
ALTER TABLE [pt_widget_category] ADD PRIMARY KEY ([pk_category])
GO
-- ----------------------------
-- Table structure for [pt_widget_preference]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_widget_preference')
DROP TABLE [pt_widget_preference]
GO
CREATE TABLE [pt_widget_preference] (
[widgetid] varchar(50) NOT NULL ,
[viewid] varchar(50) NOT NULL ,
[id] varchar(50) NOT NULL ,
[name] varchar(100) NULL ,
[value] varchar(100) NULL ,
[ctime] datetime NULL ,
[pk_widget_preference] varchar(22) NOT NULL ,
[userid] varchar(50) NULL ,
[system] varchar(50) NULL ,
[tenant] varchar(50) NULL ,
[ts] datetime NULL ,
[dr] int NULL ,
[wstatus] int NULL ,
)
ALTER TABLE [pt_widget_preference] ADD PRIMARY KEY ([pk_widget_preference])
GO
-- ----------------------------
-- Table structure for [pt_layout]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_theme')
DROP TABLE [pt_theme]
GO
CREATE TABLE [pt_theme] (
[id] varchar(50) NULL ,
[name] varchar(100) NOT NULL ,
[pk_theme] varchar(22) NOT NULL ,
[isdefault] char(1) NULL ,
[ctime] datetime NULL ,
[system] varchar(50) NULL ,
[tenant] varchar(50) NULL ,
[ts] datetime NULL ,
[dr] int NULL ,
)
ALTER TABLE [pt_theme] ADD PRIMARY KEY ([pk_theme])
GO
-- ----------------------------
-- Table structure for [pt_theme_preference]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_theme_preference')
DROP TABLE [pt_theme_preference]
GO
CREATE TABLE [pt_theme_preference] (
[userid] varchar(50) NULL ,
[themeid] varchar(50) NOT NULL ,
[pk_theme_preference] varchar(22) NOT NULL ,
[system] varchar(50) NULL ,
[tenant] varchar(50) NULL ,
[ts] datetime NULL ,
[dr] int NULL ,
)
ALTER TABLE [pt_theme_preference] ADD PRIMARY KEY ([pk_theme_preference])
GO
-- ----------------------------
-- Table structure for [pt_more_menu]
-- ----------------------------
IF EXISTS(SELECT * FROM sys.tables WHERE type='U' and [Name]='pt_theme_preference')
DROP TABLE [pt_more_menu]
GO
CREATE TABLE [pt_more_menu] (
[code] varchar(50) NOT NULL,
[name] varchar(100) NOT NULL,
[url] varchar(300) NOT NULL,
[url_type] varchar(10) NOT NULL,
[icon] varchar(50) DEFAULT NULL,
[sort] int(255) DEFAULT NULL,
[tag] varchar(50) DEFAULT NULL,
[pk_more_menu] varchar(50) NOT NULL,
[isenable] char(1) DEFAULT NULL,
[system] varchar(50) DEFAULT NULL,
[tenant] varchar(50) DEFAULT NULL,
[ts] datetime default NULL,
[dr] int(255) default NULL,
)
ALTER TABLE [pt_more_menu] ADD PRIMARY KEY ([pk_more_menu])
GO