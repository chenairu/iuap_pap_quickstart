define(['text!./goodsDirectory.html',"require","dialogmin",
    "css!../../../style/common.css",
    "css!./goodsDirectory.css",
    "../../../config/sys_const.js",
    "../../../utils/utils.js",
    "../../../utils/ajax.js",
    "../../../utils/tips.js",
    "./viewModel.js"], 
    function (template,require) {
	
        var ctx, element;
        var treeUrl, asyncUrl;
        var dialogmin = require("dialogmin")
        
        function init(element, cookie) {
            element = element;
            ctx = cookie.appCtx + "/goodsDirectory";
            treeUrl = ctx + "/searchTree"; 							//树数据URL
            asyncUrl = ctx + "/children";	 						//子节点数据URL
            window.csrfDefense();									//跨站请求伪造防御
            $(element).html(template);
            viewModel.event.pageinit(element);
        }

        //viewModel通用事件
        viewModel.event = {
        	pageinit: function (element) {
            	treeid = [];
                viewModel.app = u.createApp({
                	el: element,
                    model: viewModel
                });
                
                //树查询事件
                $('#searchInput').keydown(function(e){
                	if(e.keyCode==13){
                		viewModel.tree.searchTree($('#searchInput').val());
                	}
                });
                
        	},
        	
        	
        }
        
        //viewModel树事件
        viewModel.tree = {
        	treeId : "tree1",
        	removedNodes : [],
        	
        	setting: {
                view: {
                    showLine: false,
                    selectedMulti: false
                },
                async:{
                    enable:true,
                    url: "/iuap-example/goodsDirectory/children",
                    autoParam:["id","level"]
                },
                callback: {
                	onClick:function(event, treeId, treeNode){
                		alert(treeNode.id+"----"+treeNode.name);
                	}
                }
            },
        	
            clearTree : function(){
       			viewModel.treeData.removeAllRows();
                viewModel.treeData.clear();
            },
            
           	//一次性加载树
           	loadTree : function(element){
           		//获取Tree数据
           		$ajax(treeUrl, {}, viewModel.tree.succCallback4All, viewModel.tree.errorCallback4All);
           	},
           	
           	//一次性加载树成功回调
           	succCallback4All: function(data){
           		if (data.detailMsg.data) {
           			viewModel.treeData.removeAllRows();
                    viewModel.treeData.clear();
                    viewModel.treeData.setSimpleData(data.detailMsg.data, {
                    	unSelect: true
                    });
           		}
           	},
            	
            //一次性加载树失败回调
            errorCallback4All: function(data){
            },
            
            //树检索
            searchTree : function(param){
            	$ajax( treeUrl+"?searchParam="+param, {}, 
            		   function(result){
            				//重置并加载整棵树
                			viewModel.tree.clearTree();
                			viewModel.treeData.setSimpleData(result.detailMsg.data, {
                            	unSelect: true
                            });
            		   },
            		   function(result){
            			   alert("出错:"+result)
            		   });
            },
            
            //递归查找所有子节点， 废弃不再使用
            doFindNode : function(zTreeObj, pNode){
            	var searchName = $("#searchInput").val();
            	var pShow = false;
            	if(pNode!="undefined" && pNode!=null){
            		var children = pNode.children;
            		if(children!="undefined" && children!=null && children.length>0){
            			for(var i=0; i<children.length; i++){
            				if(viewModel.tree.doFindNode(zTreeObj, children[i])==true){
            					pShow = true;
            				};
            			}
            		}else{
            			if(pNode.level<2 || pNode.name.indexOf(searchName)!=-1 && searchName!=""){
            				pShow = true;
            			}else{
            				pShow = false;
            				viewModel.tree.removedNodes[viewModel.tree.removedNodes.length]=pNode;
            			}
            		}
            	}
            	return pShow;
            },
            
        }

        return {
            template: template,
            init: init
        };
                
    });