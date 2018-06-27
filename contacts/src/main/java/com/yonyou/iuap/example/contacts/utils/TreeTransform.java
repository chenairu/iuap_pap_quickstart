package com.yonyou.iuap.example.contacts.utils;

import java.util.ArrayList;
import java.util.List;

import com.yonyou.iuap.example.contacts.entity.Organization;

public class TreeTransform {

	public static List<Organization> get(List<Organization> list){
		List<Organization> rootList = new ArrayList<Organization>();
		List<Organization> childrenList = new ArrayList<Organization>();
		
		if(list != null && list.size() > 0){
			for(Organization t:list){
				if(t.getParentid() == null || "".equals(t.getParentid())){
					rootList.add(t);
				}else{
					childrenList.add(t);
				}
			}
			getNext(rootList,childrenList);
		}
		return rootList;
	}
	
	public static void getNext(List<Organization> root,List<Organization> child){
		List<Organization> newroot = new ArrayList<Organization>();
		if(root != null && root.size()>0 && child != null && child.size() > 0){
			for(Organization r:root){
				List<Organization> list = new ArrayList<Organization>();
				for(Organization c:child){
					if(r.getInstitid().equals(c.getParentid())){
						list.add(c);
						newroot.add(c);
					}
				}
				r.setChildren(list);
			}
			if(newroot.size()>0){
				getNext(newroot,child);
			}
		}
	}
}
