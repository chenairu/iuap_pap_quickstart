var meta = {
	meta : {
		id : {

		},
		code : {
			enable : false
		},
		name : {
			required : true,
			nullMsg : "不能为空"
		},
		ly_code : {
			required : true,
			nullMsg : "不能为空"
		},
		ly_sm : {

		},
		zr_dw : {
			'refmodel' : JSON.stringify(refinfo['organization']),
			'refcfg' : '{"ctx":"/uitemplate_web"}',
			'refparam' : '{"isUseDataPower":"true"}'
		},
		zr_dw_name : {

		},
		zrr : {

		},
		xb_dw : {
			'refmodel' : JSON.stringify(refinfo['organization']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		xb_dw_name : {

		},
		xbr : {

		},
		begin_date : {

		},
		end_date : {

		},
		zy_cd : {
			required : true,
			nullMsg : "不能为空"
		},
		qt_ld : {

		},
		zbr : {
			'refmodel' : JSON.stringify({
				"refClientPageInfo" : {
					"currPageIndex" : 0,
					"pageCount" : 0,
					"pageSize" : 100
				},
				"refCode" : "people",
				"refModelUrl" : "/basedoc/peopledocRef/",
				"refName" : "人员",
				"refUIType" : "RefGrid",
				"rootName" : "档案列表"
			}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		zbr_name : {},
		dbr : {

		},
		jfyq : {

		},
		db_info : {

		},
		jd_bl : {

		},
		rwpf : {

		},
		kpi_flag : {
			required : true,
			nullMsg : "不能为空"
		},
		kpi_level : {
			required : true,
			nullMsg : "不能为空"
		},
		state : {
			enable : false,
			required : true,
			nullMsg : "不能为空"
		},
		create_name : {
			'refmodel' : JSON.stringify(refinfo['wbUser']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		create_name_name : {

		},
		create_time : {

		},
		update_name : {
			'refmodel' : JSON.stringify(refinfo['wbUser']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		update_name_name : {

		},
		update_time : {

		},
		unitid : {
			'refmodel' : JSON.stringify(refinfo['organization']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		unitid_name : {

		},
		id_ygdemo_yw_sub : {

		},
	}
};

var meta_sub = {
	meta : {
		fk_id_ygdemo_yw_sub : {

		},
		sub_id : {

		},
		sub_code : {

		},
		sub_name : {

		},
		zbr : {
			'refmodel' : JSON.stringify({
				"refClientPageInfo" : {
					"currPageIndex" : 0,
					"pageCount" : 0,
					"pageSize" : 100
				},
				"refCode" : "people",
				"refModelUrl" : "/basedoc/peopledocRef/",
				"refName" : "人员",
				"refUIType" : "RefGrid",
				"rootName" : "档案列表"
			}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		zbr_name : {},
		sub_ms : {

		},
		begin_date : {

		},
		end_date : {

		},
		create_name : {
			'refmodel' : JSON.stringify(refinfo['wbUser']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		create_name_name : {

		},
		create_time : {

		},
		update_name : {
			'refmodel' : JSON.stringify(refinfo['wbUser']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		update_name_name : {

		},
		update_time : {

		},
	}
};

var searchData = {
	meta : {
		id : {},
		code : {},
		name : {},
		ly_code : {},
	}

};
//end userjob meta
