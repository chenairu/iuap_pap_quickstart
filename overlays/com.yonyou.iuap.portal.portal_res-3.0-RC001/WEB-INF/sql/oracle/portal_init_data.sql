insert into PT_LAYOUT_TEMPLATE  values ('column4-8', '两列4-8', '<div class="row"><div class="col-md-4 ui-grid" id="widgetbox1"></div><div class="col-md-8 ui-grid" id="widgetbox2">col-md-8</div></div>', '12122t33j', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('column6-6', '两列6-6', '<div class="row"><div class="col-md-6 ui-grid" id="widgetbox1"></div><div class="col-md-6 ui-grid" id="widgetbox2">col-md-6</div></div>', '1212qweq4', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('column12', '一列12', '<div class="col-md-12 ui-grid" id="widgetbox1"></div>', '121312332', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('column8-4', '两列8-4', '<div class="row"><div class="col-md-8 ui-grid" id="widgetbox1"></div><div class="col-md-4 ui-grid" id="widgetbox2">col-md-4</div></div>', '12wwq656', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('column4-4-4', '三列4-4-4', '<div class="row"><div class="col-md-4 ui-grid" id="widgetbox1"></div><div class="col-md-4 ui-grid" id="widgetbox2">col-md-4</div><div class="col-md-4 ui-grid" id="widgetbox3">col-md-4</div></div>', '1qweqweq', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('row2-column2-home', '二行二列-首页', '<div class="row"><div class="col-md-6 ui-grid-out"><div><div class="col-md-6 ui-grid" id="widgetbox1"></div><div class="col-md-6 ui-grid" id="widgetbox2"></div></div><div><div class="col-md-12 ui-grid" id="widgetbox3"></div></div></div><div class="col-md-6 ui-grid-out"><div><div class="col-md-6 ui-grid" id="widgetbox4"></div><div class="col-md-6 ui-grid" id="widgetbox5"></div></div><div><div class="col-md-12 ui-grid" id="widgetbox6"></div></div></div></div>', 'pt00000000000000000001', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('row2-column3-column2', '二行三列二列', '<div class="row"><div><div class="col-md-4 ui-grid" id="widgetbox1"></div><div class="col-md-4 ui-grid" id="widgetbox2"></div><div class="col-md-4 ui-grid" id="widgetbox3"></div></div><div><div class="col-md-6 ui-grid" id="widgetbox4"></div><div class="col-md-6 ui-grid" id="widgetbox5"></div></div></div>', 'pt00000000000000000003', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('row2-column4-column1', '二行三列一列', '<div class="row"><div><div class="col-md-3 ui-grid" id="widgetbox1"></div><div class="col-md-3 ui-grid" id="widgetbox2"></div><div class="col-md-3 ui-grid" id="widgetbox3"></div><div class="col-md-3 ui-grid" id="widgetbox4"></div></div><div><div class="col-md-12 ui-grid" id="widgetbox5"></div></div></div>', 'pt00000000000000000009', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('row3-column1-3-2', '三行一列三列二列', '<div class="row"><div><div class="col-md-12 ui-grid" id="widgetbox1"></div></div><div><div class="col-md-4 ui-grid" id="widgetbox2"></div><div class="col-md-4 ui-grid" id="widgetbox3"></div><div class="col-md-4 ui-grid" id="widgetbox4"></div></div><div><div class="col-md-6 ui-grid" id="widgetbox5"></div><div class="col-md-6 ui-grid" id="widgetbox6"></div></div></div>', 'pt00000000000000000002', null, null, null,null,null);
insert into PT_LAYOUT_TEMPLATE  values ('row3-column1-3-2-84', '三行一列三列二列84', '<div class="row"><div><div class="col-md-12 ui-grid" id="widgetbox1"></div></div><div><div class="col-md-4 ui-grid" id="widgetbox2"></div><div class="col-md-4 ui-grid" id="widgetbox3"></div><div class="col-md-4 ui-grid" id="widgetbox4"></div></div><div><div class="col-md-6 ui-grid" id="widgetbox5"></div><div class="col-md-6 ui-grid" id="widgetbox6"></div></div><div><div class="col-md-8 ui-grid" id="widgetbox7"></div><div class="col-md-4 ui-grid" id="widgetbox8"></div></div></div>', 'pt00000000000000000004', null, null, null,null,null);


insert into PT_WIDGET_CATEGORY  values ('pt00000000000000000001', 'portal', '门户', null, 'Y', 1, null, null,null,null);

insert into PT_THEME  values ('blue', '蓝色', 'N', TIMESTAMP '2016-06-16 10:43:50','pt00000000000000000001', null, null,null,null);
insert into PT_THEME  values ('primary', '默认', 'Y', TIMESTAMP '2016-06-16 10:42:50','pt00000000000000000002', null, null,null,null);

-- ----------------------------
-- Records of PT_MORE_MENU
-- ----------------------------
insert into pt_more_menu values ('design', '个性化', '/design', 'view', 'portalfont icon-personalized', 1, 'PT_000000000000001', 'PT_000000000000001', 'Y', null, null, null, null);
insert into pt_more_menu values ('skintools', '皮肤设置', './js/ext/skintools.js', 'js', 'portalfont icon-skin', 2, 'PT_00000000002', 'PT_00000000002', 'Y', null, null, null, null);
-- ----------------------------
-- Records of PT_WIDGET
-- ----------------------------
INSERT INTO PT_WIDGET VALUES ('autoplay', '轮播图', '轮播图', 'portal', 'xml', '/widget/autoplay/autoplay.xml', '4T4jNh7QYRUSBCNqNfJXUc', 'Y', NULL, NULL, TO_DATE('2016-10-09 14:38:42', 'SYYYY-MM-DD HH24:MI:SS'), NULL, TO_DATE('2016-10-10 11:33:44', 'SYYYY-MM-DD HH24:MI:SS'), NULL, TO_DATE('2016-10-10 11:33:44', 'SYYYY-MM-DD HH24:MI:SS'), NULL,'1', NULL);
