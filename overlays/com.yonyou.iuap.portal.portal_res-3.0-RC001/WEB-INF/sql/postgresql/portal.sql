-- ----------------------------
-- Table structure for "pt_layout"
-- ----------------------------
DROP TABLE IF EXISTS "pt_layout";
CREATE TABLE "pt_layout" (
"templateid" varchar(50),
"setting" text,
"pk_layout" varchar(22) NOT NULL,
"id" varchar(50) NOT NULL,
"name" varchar(100) NOT NULL,
"ctime" timestamp(6),
"parentid" varchar(50),
"userid" varchar(50),
"system" varchar(50),
"tenant" varchar(50),
"isdefault" char(1) DEFAULT NULL::bpchar,
"modifytime" timestamp(6),
"wlevel" char(1),
"isenable" char(1),
"rnd" char(30),
"ts" timestamp(6),
"dr" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "pt_layout_template"
-- ----------------------------
DROP TABLE IF EXISTS  "pt_layout_template";
CREATE TABLE "pt_layout_template" (
"id" varchar(50) NOT NULL,
"name" varchar(100) NOT NULL,
"tpl" text,
"pk_layout_template" varchar(22) NOT NULL,
"i18n" varchar(50),
"system" varchar(50),
"tenant" varchar(50),
"ts" timestamp(6),
"dr" int4
)
WITH (OIDS=FALSE)

;
-- ----------------------------
-- Table structure for "pt_widget"
-- ----------------------------
DROP TABLE IF EXISTS "pt_widget";
CREATE TABLE "pt_widget" (
"id" varchar(100) NOT NULL,
"name" varchar(200) NOT NULL,
"descr" varchar(300),
"category" varchar(100) NOT NULL,
"wtype" varchar(50) NOT NULL,
"url" varchar(300) NOT NULL,
"pk_widget" varchar(22) NOT NULL,
"isenable" char(1),
"system" varchar(50),
"tenant" varchar(50),
"ctime" timestamp(6),
"cnf" text,
"modifytime" timestamp(6),
"setting" text,
"ts" timestamp(6),
"dr" int4,
"wlevel" char(1),
"tag" char(30)
)
WITH (OIDS=FALSE)

;
-- ----------------------------
-- Table structure for "pt_widget_category"
-- ----------------------------
DROP TABLE IF EXISTS  "pt_widget_category";
CREATE TABLE "pt_widget_category" (
"id" varchar(50) NOT NULL,
"pk_category" varchar(22) NOT NULL,
"name" varchar(100) NOT NULL,
"i18n" varchar(50),
"isenable" char(1) DEFAULT 'Y'::bpchar NOT NULL,
"sort" int2,
"system" varchar(50),
"tenant" varchar(50),
"ts" timestamp(6),
"dr" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "pt_widget_preference"
-- ----------------------------
DROP TABLE IF EXISTS  "pt_widget_preference";
CREATE TABLE "pt_widget_preference" (
"widgetid" varchar(150) NOT NULL,
"viewid" varchar(150) NOT NULL,
"id" varchar(50) NOT NULL,
"name" varchar(100) NOT NULL,
"value" varchar(100) NOT NULL,
"ctime" timestamp(6),
"pk_widget_preference" varchar(22) NOT NULL,
"userid" varchar(50),
"system" varchar(50),
"tenant" varchar(50),
"ts" timestamp(6),
"dr" int4,
"wstatus" int4
)
WITH (OIDS=FALSE)

;
-- ----------------------------
-- Table structure for "pt_theme"
-- ----------------------------
DROP TABLE IF EXISTS  "pt_theme";
CREATE TABLE "pt_theme" (
"id" varchar(50) NOT NULL,
"name" varchar(100) NOT NULL,
"isdefault" char(1) DEFAULT NULL::bpchar,
"ctime" timestamp(6),
"pk_theme" varchar(22) NOT NULL,
"system" varchar(50),
"tenant" varchar(50),
"ts" timestamp(6),
"dr" int4
)
WITH (OIDS=FALSE)
;
-- ----------------------------
-- Table structure for "pt_theme_preference"
-- ----------------------------
DROP TABLE IF EXISTS  "pt_theme_preference";
CREATE TABLE "pt_theme_preference" (
"userid" varchar(50) NOT NULL,
"themeid" varchar(100) NOT NULL,
"pk_theme_preference" varchar(22) NOT NULL,
"system" varchar(50),
"tenant" varchar(50),
"ts" timestamp(6),
"dr" int4
)
WITH (OIDS=FALSE)
;
-- ----------------------------
-- Table structure for "pt_more_menu"
-- ----------------------------
DROP TABLE IF EXISTS  "pt_more_menu";
CREATE TABLE "pt_more_menu" (
"code" varchar(50) NOT NULL,
"name" varchar(100) NOT NULL,
"url" varchar(300) NOT NULL,
"url_type" varchar(10) NOT NULL,
"icon" varchar(50) DEFAULT NULL,
"sort" int2,
"tag" varchar(50) DEFAULT NULL,
"pk_more_menu" varchar(50) NOT NULL,
"isenable" char(1) DEFAULT NULL,
"system" varchar(50),
"tenant" varchar(50),
"ts" timestamp(6),
"dr" int4
)
WITH (OIDS=FALSE)
;

-- ----------------------------
-- Primary Key structure for table "pt_more_menu"
-- ----------------------------
ALTER TABLE "pt_more_menu" ADD PRIMARY KEY ("pk_more_menu");

-- ----------------------------
-- Primary Key structure for table "pt_theme_preference"
-- ----------------------------
ALTER TABLE "pt_theme_preference" ADD PRIMARY KEY ("pk_theme_preference");

-- ----------------------------
-- Primary Key structure for table "pt_theme"
-- ----------------------------
ALTER TABLE "pt_theme" ADD PRIMARY KEY ("pk_theme");
-- ----------------------------
-- Primary Key structure for table "pt_layout"
-- ----------------------------
ALTER TABLE "pt_layout" ADD PRIMARY KEY ("pk_layout");

-- ----------------------------
-- Primary Key structure for table "pt_layout_template"
-- ----------------------------
ALTER TABLE "pt_layout_template" ADD PRIMARY KEY ("pk_layout_template");

-- ----------------------------
-- Primary Key structure for table "pt_widget"
-- ----------------------------
ALTER TABLE "pt_widget" ADD PRIMARY KEY ("pk_widget");

-- ----------------------------
-- Primary Key structure for table "pt_widget_category"
-- ----------------------------
ALTER TABLE "pt_widget_category" ADD PRIMARY KEY ("pk_category");

-- ----------------------------
-- Primary Key structure for table "pt_widget_preference"
-- ----------------------------
ALTER TABLE "pt_widget_preference" ADD PRIMARY KEY ("pk_widget_preference");
