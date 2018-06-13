package com.yonyou.iuap.example.goods.directory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ListDataEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.goods.directory.dao.GoodsDirectoryMapper;
import com.yonyou.iuap.example.goods.directory.entity.GoodsDirectory;

import yonyou.bpm.rest.utils.StringUtils;

@Service
@SuppressWarnings("all")
public class GoodsDirectoryService extends GenericService<GoodsDirectory>{

	public List<GoodsDirectory> searchTree(String searchParam){
		//获取所有数据
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderDesc", "level");		
		List<GoodsDirectory> listData = this.queryList(params);				//按正序层级获取数据集合
    	if(StringUtils.isBlank(searchParam)) {
    		return listData;
    	}
		
		//构造树形结构，递归检索
		Map<String, GoodsDirectory> treeMap = this.buildTreeMap(listData);
		List<GoodsDirectory> listNodes = new ArrayList<GoodsDirectory>();
		Set<String> keySet = treeMap.keySet();
		Iterator<String> itor = keySet.iterator();
		while(itor.hasNext()) {
			String curKey = itor.next();
			this.doFindNode(treeMap.get(curKey), searchParam, listNodes);	//未找到
		}
		return listNodes;
	}
	
	private Map<String, GoodsDirectory> buildTreeMap(List<GoodsDirectory> listData){
		Map<String, GoodsDirectory> treeMap = new HashMap<String, GoodsDirectory>();
		Map<String, GoodsDirectory> tempMap = new HashMap<String, GoodsDirectory>();
		for(int i=0; i<listData.size(); i++) {
			GoodsDirectory curNode = listData.get(i);
			tempMap.put(curNode.getId(), curNode);					//暂存数据Node
			GoodsDirectory pNode = tempMap.get(curNode.getParentId());
			if(pNode==null) {
				treeMap.put(curNode.getId(), curNode);				//设为根节点
			}else {
				pNode.getChildren().add(curNode);					//添加到父节点的子节点中
			}
		}
		return treeMap;
	}
	
	/**
	 * 递归检索
	 * @param node
	 * @param searchParam
	 * @return
	 */
	private boolean doFindNode(GoodsDirectory node, String searchParam, List<GoodsDirectory> listNodes) {
		List<GoodsDirectory> children = node.getChildren();
		boolean ibFlag = false;
		if(children != null && children.size()>0) {
			for(GoodsDirectory curNode : children) {
				if(this.doFindNode(curNode, searchParam, listNodes)) {
					ibFlag = true;							//子节点中存在符合条件的数据
				}
			}
		}
		if(ibFlag==true || node.getName().indexOf(searchParam) != -1) {
			listNodes.add(node);
			ibFlag = true;
		}
		return ibFlag;
	}
	
	
	/*****************************************/
	private GoodsDirectoryMapper productDirectoryMapper;

	@Autowired
	public void setProductDirectoryMapper(GoodsDirectoryMapper productDirectoryMapper) {
		this.productDirectoryMapper = productDirectoryMapper;
		super.setIbatisMapper(productDirectoryMapper);
	}
	
}