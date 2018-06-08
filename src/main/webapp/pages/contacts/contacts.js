define(
	["text!./contacts.html", "css!./contacts.css", "./meta.js",
		"../../config/sys_const.js",
		"../sever.js",
		"css!../../style/common.css"],

	function (html) {

        // 定义初始化对象
		var init = function (element, cookie) {

            // 定义调用服务地址
			var ctx = cookie.appCtx;
			var treeListUrl = ctx + "/instit/list";
			var treeDelUrl = ctx + "/instit/del/";
			var treeSaveUrl = ctx + "/instit/save";
			var tableListUrl = ctx + "/telBook/list";
			var tableDelUrl = ctx + "/telBook/del/";
			var tableSaveUrl = ctx + "/telBook/save";


			var viewModel = {
				app: {},
				draw: 1,
				totalPage: 0,
				pageSize: 5,
				totalCount: 0,
				/* 树数据 */
				institdata: new u.DataTable(meta),
				/* 编辑框树数据 */
				institdatanew: new u.DataTable(meta),
				/* 电话本数据 */
				telbookdata: new u.DataTable(metaTelbook),
				/* 电话本数据 */
				telbookdatanew: new u.DataTable(metaTelbook),
				/* 树设置 */
				treeSetting: {
					view: {
						showLine: false,
						selectedMulti: false
					},
					callback: {
						onClick: function (e, id, node) {
							treeid = [];
							viewModel.event.getAllChildren(node, treeid);
							var pid = node.pId;
							viewModel.event.loadTelbook(treeid);
						}
					}
				},


				event: {
					//页面初始化
					pageInit: function () {
						treeid = [];
						viewModel.app = u.createApp({
							el: element,
							model: viewModel
						});

						//分页初始化
						var paginationDiv = $(element).find("#pagination")[0];
						this.comps = new u.pagination({
							el: paginationDiv,
							jumppage: true
						});
						this.comps.update({
							pageSize: 5
						}); //默认每页显示5条数据
						this.loadTree();
						viewModel.event.pageChange();
						viewModel.event.sizeChange();

						//回车搜索
						$(".input_enter").keydown(function (e) {
							if (e.keyCode == 13) {
								viewModel.event.searchClick();
								u.stopEvent(e);
							}
						});
					},

					// 加载组织机构树
					loadTree: function () {
						$.ajax({
							type: "get",
							url: treeListUrl,
							dataType: "json",
							success: function (res) {
								if (res) {
									if (res.success == "success") {
										if (res.detailMsg.data) {
											viewModel.institdata.removeAllRows();
											viewModel.institdata.clear();
											viewModel.institdata.setSimpleData(res.detailMsg.data, {
												unSelect: true
											});
											$("#orgTree")[0]["u-meta"].tree.expandAll(true);
										}
									} else {
										var msg = "";
										if (res.success == "fail_global") {
											msg = res.message;
										} else {
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key] + "<br/>";
											}
										}
										u.messageDialog({
											msg: msg,
											title: "请求错误",
											btnText: "确定"
										});
									}
								} else {
									u.messageDialog({
										msg: "后台返回数据格式有误，请联系管理员",
										title: "数据错误",
										btnText: "确定"
									});
								}
							},
							error: function (er) {
								u.messageDialog({
									msg: "请求超时",
									title: "请求错误",
									btnText: "确定"
								});
							}
						});

					},

					// 加载人员信息
					loadTelbook: function (instit) {
						var jsonData = {
							pageIndex: viewModel.draw - 1,
							pageSize: viewModel.pageSize,
							sortField: "peocode",
							sortDirection: "asc"
						};
						/*右表的上面详细信息显示*/
						var infoDiv = document.getElementById("infoPanel");
						var dtVal = viewModel.institdata.getValue("institname");
						infoDiv.innerHTML = dtVal;

						$(element).find("#search")
							.each(
								function () {
									if (this.value == undefined
										|| this.value == ""
										|| this.value.length == 0) {
										//不执行操作
									} else {
										jsonData["search_searchParam"] = this.value
											.replace(/(^\s*)|(\s*$)/g, ""); //去掉空格
									}
								});
						if (instit) {
							if (instit != "" || instit.length != 0) {
								jsonData["institid"] = instit.join();
							}
						}

						$ajax(
							tableListUrl,
							jsonData,
							function (res) {
								if (res) {
									if (res.success == "success") {
										if (!res.detailMsg.data) {
											viewModel.totalCount = 0;
											viewModel.totalPage = 1;
											viewModel.event.comps
												.update({
													totalPages: viewModel.totalPage,
													pageSize: viewModel.pageSize,
													currentPage: viewModel.draw,
													totalCount: viewModel.totalCount
												});
											viewModel.telbookdata
												.removeAllRows();
										} else {
											viewModel.totalCount = res.detailMsg.data.totalElements;
											viewModel.totalPage = res.detailMsg.data.totalPages;
											viewModel.event.comps
												.update({
													totalPages: viewModel.totalPage,
													pageSize: viewModel.pageSize,
													currentPage: viewModel.draw,
													totalCount: viewModel.totalCount
												});
											viewModel.telbookdata
												.removeAllRows();
											viewModel.telbookdata
												.setSimpleData(
													res.detailMsg.data.content,
													{
														unSelect: true
													});
										}
									} else {
										var msg = "";
										if (res.success == "fail_global") {
											msg = res.message;
										} else {
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key]
													+ "<br/>";
											}
										}
										u.messageDialog({
											msg: msg,
											title: "请求错误",
											btnText: "确定"
										});
									}
								} else {
									u.messageDialog({
										msg: "后台返回数据格式有误，请联系管理员",
										title: "数据错误",
										btnText: "确定"
									});
								}
							}, function () {
								u.messageDialog({
									msg: "请求超时",
									title: "请求错误",
									btnText: "确定"
								});
							},
							"get"
						)
					},

					//分页相关
					pageChange: function () {
						viewModel.event.comps.on("pageChange", function (
							pageIndex) {
							viewModel.draw = pageIndex + 1;
							viewModel.event.loadTelbook(treeid);
						});
					},
					// 改变每页数量
					sizeChange: function () {
						viewModel.event.comps.on("sizeChange",
							function (arg) {
								viewModel.pageSize = parseInt(arg);
								viewModel.draw = 1;
								viewModel.event.loadTelbook(treeid);
							});
					},


					// 新增组织机构按钮点击
					addinstitClick: function () {
						$("#institcode").removeAttr("readonly");
						$("#dialog_content_instit").find(".u-msg-title")
							.html("<h4>新增机构</h4>");
						viewModel.event.clearDt(viewModel.institdatanew);
						var row = viewModel.institdata.getSelectedRows()[0];

						if (row) {
							var parentId = row.getValue("institid");
							var parentName = row.getValue("institname");
						}

						var newr = viewModel.institdatanew.createEmptyRow();
						viewModel.institdatanew.setRowSelect(newr);

						if (row) {
							var newrow = viewModel.institdatanew.getSelectedRows()[0];
							newrow.setValue("parentid", parentId);
							newrow.setValue("parentname", parentName);
						}

						window.md = u.dialog({
							id: "add_depart",
							content: "#dialog_content_instit",
							hasCloseMenu: true
						});
					},
					// 修改组织机构按钮被点击
					editinstitClick: function () {
						$("#dialog_content_instit").find(".u-msg-title")
							.html("<h4>编辑机构</h4>");
						viewModel.event.clearDt(viewModel.institdatanew);
						var row = viewModel.institdata.getSelectedRows()[0];
						if (row) {
							if (row.data.parentid.value) {
								row
									.setValue(
										"parentname",
										$("#orgTree")[0]["u-meta"].tree
											.getNodeByParam(
												"id",
												row
													.getValue("parentid")).name);
							}

							viewModel.institdatanew
								.setSimpleData(viewModel.institdata
									.getSimpleData({
										type: "select"
									}));
							$("#institcode").attr("readonly", "readonly");
							window.md = u.dialog({
								id: "edit_depart",
								content: "#dialog_content_instit",
								hasCloseMenu: true
							});
							$('.u-msg-dialog').css('width', '800px')
						} else {
							u.messageDialog({
								msg: "请选择要编辑的数据！",
								title: "操作提示",
								btnText: "确定"
							});
						}
					},

					// 删除组织机构按钮被点击
					delinstitClick: function () {
						var row = viewModel.institdata.getSelectedRows()[0];
						if (row) {
							var data = viewModel.institdata
								.getSelectedRows()[0].getSimpleData();
							u.confirmDialog({
								msg: "是否删除" + data.institname + "?",
								title: "删除确认",
								onOk: function () {
									viewModel.event.deleteTree(data);
								},
								onCancel: function () {
								}
							});
						} else {
							u.messageDialog({
								msg: "请选择要删除的数据！",
								title: "操作提示",
								btnText: "确定"
							});
						}
					},

					// 新增或修改组织机构保存按钮被点击
					saveinstitClick: function () {
						var data = viewModel.institdatanew.getSelectedRows()[0].getSimpleData();
						if (!viewModel.app.compsValidate($("#dialog_content_instit")[0])) {
							return;
						}
						viewModel.event.saveTree(data);
					},

					// 新增或修改组织机构取消按钮被点击
					cancelinstitClick: function () {
						md.close();
					},

					//新增和更新组织机构
					saveTree: function (data) {
						var list = viewModel.event.genDataList(data);
						$.ajax({
							type: "post",
							url: treeSaveUrl,
							dataType: "json",
							contentType: "application/json",
							data: JSON.stringify(list),
							success: function (res) {
								if (res) {
									if (res.success == "success") {
										u.messageDialog({
											msg: "保存成功！",
											btnText: "确定"
										});
										viewModel.event.loadTree();
										md.close();
									} else {
										var msg = "";
										if (res.success == "fail_global") {
											msg = res.message;
										} else {
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key]
													+ "<br/>";
											}
										}
										u.messageDialog({
											msg: msg,
											title: "操作提示",
											btnText: "确定"
										});
									}
								} else {
									u.messageDialog({
										msg: "没有返回数据",
										title: "操作提示",
										btnText: "确定"
									});
								}
							}
						});
					},
					//删除组织机构
					deleteTree: function (data) {
						var datalist = viewModel.event.genDataList(data);
						var json = JSON.stringify(datalist);
						$.ajax({
							url: treeDelUrl,
							data: json,
							dataType: "json",
							type: "post",
							contentType: "application/json",
							success: function (res) {
								if (res) {
									if (res.success == "success") {
										viewModel.institdata
											.removeRow(viewModel.institdata
												.getSelectedIndexs());
										u.messageDialog({
											msg: "删除成功",
											title: "操作提示",
											btnText: "确定"
										});
									} else {
										var msg = "";
										if (res.success == "fail_global") {
											msg = res.message;
											if (msg == "Data had been referenced,remove is forbidden!") {
												msg = "该部门含有子部门不能删除";
											}
										} else {
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key]
													+ "<br/>";
											}
										}
										u.messageDialog({
											msg: msg,
											title: "操作提示",
											btnText: "确定"
										});
									}
								} else {
									u.messageDialog({
										msg: "无返回数据",
										title: "操作提示",
										btnText: "确定"
									});
								}
							},
							error: function (er) {
								u.messageDialog({
									msg: "操作请求失败，" + er,
									title: "操作提示",
									btnText: "确定"
								});
							}
						});
					},

					// 新增人员信息按钮被点击
					addManClick: function () {
						$("#dialog_content_man").find(".u-msg-title").html(
							"<h4>新增人员</h4>");
						viewModel.event.clearDt(viewModel.telbookdatanew);
						var row = viewModel.institdata.getSelectedRows()[0];
						if (row) {
							var institId = row.getValue("institid");
							var instit = row.getValue("institname");
							var newr = viewModel.telbookdatanew
								.createEmptyRow();
							viewModel.telbookdatanew.setRowSelect(newr);
							var newrow = viewModel.telbookdatanew
								.getSelectedRows()[0];
							newrow.setValue("institid", institId);
							newrow.setValue("institname", instit);
							$("#add_peocode").removeAttr("readonly");
							window.md = u.dialog({
								id: "add_man",
								content: "#dialog_content_man",
								hasCloseMenu: true
							});
							$('.u-msg-dialog').css('width', '800px');
						} else {
							u.messageDialog({
								msg: "请选择部门！",
								title: "操作提示",
								btnText: "确定"
							});
						}
					},

					// 修改人员信息按钮被点击
					editManClick: function (rowId) {
						if (!rowId || rowId === -1) {
							return;
						}

						$("#dialog_content_man").find(".u-msg-title").html(
							"<h4>编辑人员</h4>");
						viewModel.event.clearDt(viewModel.telbookdatanew);
						var row = viewModel.telbookdata
							.getRowByRowId(rowId);
						if (row) {
							viewModel.telbookdatanew.setSimpleData(row
								.getSimpleData());
							$("#add_peocode").attr("readonly", "readonly");
							window.md = u.dialog({
								id: "edit_man",
								content: "#dialog_content_man",
								hasCloseMenu: true
							});
							$('.u-msg-dialog').css('width', '800px');
						} else {
							u.messageDialog({
								msg: "请选择要编辑的数据！",
								title: "操作提示",
								btnText: "确定"
							});
						}
					},

					// 删除人员信息按钮被点击
					delManClick: function (rowId) {
						if (!rowId || rowId === -1) {
							return;
						}
						var row = viewModel.telbookdata
							.getRowByRowId(rowId);
						if (row) {
							var data = row.getSimpleData();
							u
								.confirmDialog({
									msg: "是否删除" + data.peoname + "?",
									title: "删除确认",
									onOk: function () {
										viewModel.event.delMan(data);
										viewModel.telbookdata
											.removeRow(viewModel.telbookdata
												.getSelectedIndexs());
									},
									onCancel: function () {
									}
								});
						} else {
							u.messageDialog({
								msg: "请选择要删除的数据！",
								title: "操作提示",
								btnText: "确定"
							});
						}
					},

					// 新增或修改人员信息保存按钮被点击
					saveManClick: function () {
						var data = viewModel.telbookdatanew
							.getSelectedRows()[0].getSimpleData();
						if (!viewModel.app.compsValidate($("#add-form")[0])) {
							return;
						}
						viewModel.event.saveMan(data);
					},

					// 新增或修改人员信息取消按钮被点击
					cancelManClick: function () {
						md.close();
					},

					// 查询人员信息
					searchClick: function () {
						viewModel.draw = 1;
						viewModel.event.loadTelbook(treeid);
					},
					//更新和保存人员       
					saveMan: function (data) {
						var list = viewModel.event.genDataList(data);
						$
							.ajax({
								type: "post",
								url: tableSaveUrl,
								dataType: "json",
								contentType: "application/json",
								data: JSON.stringify(list),
								success: function (res) {
									if (res) {
										if (res.success == "success") {
											u.messageDialog({
												msg: "保存成功！",
												btnText: "确定"
											});
											viewModel.event
												.loadTelbook(treeid);
											md.close();
										} else {
											var msg = "";
											if (res.success == "fail_global") {
												msg = res.message;
											} else {
												for (var key in res.detailMsg) {
													msg += res.detailMsg[key]
														+ "<br/>";
												}
											}
											u.messageDialog({
												msg: msg,
												title: "操作提示",
												btnText: "确定"
											});
										}
									} else {
										u.messageDialog({
											msg: "没有返回数据",
											title: "操作提示",
											btnText: "确定"
										});
									}
								}
							});
					},
					//删除人员
					delMan: function (data) {
						var list = viewModel.event.genDataList(data);
						$.ajax({
							type: "post",
							url: tableDelUrl,
							dataType: "json",
							contentType: "application/json",
							data: JSON.stringify(list),
							success: function (res) {
								if (res.success == "success") {
									u.messageDialog({
										msg: "删除成功！",
										btnText: "确定"
									});
									//md.close();
								} else {
									u.messageDialog({
										msg: "删除失败！",
										btnText: "确定"
									});
								}
							}
						});
					},




					//清除datatable数据
					clearDt: function (dt) {
						dt.removeAllRows();
					},

					/* 获得树节点的所有子节点 */
					getAllChildren: function (node, childrenlist) {
						childrenlist.push(node.id);
						if (node.children) {
							var i;
							for (i = 0; i < node.children.length; i++) {
								viewModel.event.getAllChildren(
									node.children[i], childrenlist);
							}
						}
					},

					//组装list
					genDataList: function (data) {
						var datalist = [];
						datalist.push(data);
						return datalist;
					},

					rowHoverHandel: function (obj) {
						$(".editTable").hide();
						let num = obj["rowIndex"];
						let a = $("#gridaddress_content_tbody").find("tr")[num];
						let ele = a.getElementsByClassName("editTable");
						ele[0].style.display = "block";
					},
					mouseoverFun: function () {
						$(".editTable").hide();
					},
					//定义操作列的内容
					optFun: function (obj) {
						var dataTableRowId = obj.row.value["$_#_@_id"];
						// 绑定删除事件
						var delfun = "data-bind=click:event.delManClick.bind($data,"
							+ dataTableRowId + ")";
						// 绑定修改事件
						var editfun = "data-bind=click:event.editManClick.bind($data,"
							+ dataTableRowId + ")";
						// 绑定查看事件
						obj.element.innerHTML = '<div class="editTable" style="display:none;"><button class="u-button u-button-border" title="修改"'
							+ editfun
							+ ">修改</button>"
							+ '<button class="u-button u-button-border" title="删除" '
							+ delfun + ">删除</button></div>";
						ko.applyBindings(viewModel, obj.element);
					}

				}
			};

			$(element).html(html);
			viewModel.event.pageInit();
			if (u.isIE8) {
				$(".ie8-bg").css("display", "block");
			}

            //  调整div高度适应满屏，树的高度固定后才会有滚动条
			$("#myLayout").height(document.body.scrollHeight);
			$("#orgTree").height(document.body.scrollHeight-100);
		};


		return {
			template: html,
			init: init
		};
	});
