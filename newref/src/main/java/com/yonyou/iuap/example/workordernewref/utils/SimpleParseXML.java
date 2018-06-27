package com.yonyou.iuap.example.workordernewref.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.example.workordernewref.entity.RefParamVO;

/**
 * 简单解析指定的XML配置文件
 * 
 * @author taomk 2015-7-14
 */
public class SimpleParseXML {

	private static Logger logger = LoggerFactory.getLogger(SimpleParseXML.class);
	private static SimpleParseXML simpleXmlParser;
	private static Document refConfigDocument = null;
	private SimpleParseXML() {

	}
	
	public static SimpleParseXML getInstance() {
		if (simpleXmlParser == null) {
			synchronized (SimpleParseXML.class) {
				// 获取发送者信息
				refConfigDocument = getDocument("newref");
			}
			return new SimpleParseXML();
		} else {
			return simpleXmlParser;
		}
	}

	private static Document getDocument(String filePath) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		// 先从Java -D的变量中取值
		String filePath_absolute = System.getProperty(filePath);
		// 如果为空，再从java env的变量中取值
		if (filePath_absolute == null) {
			filePath_absolute = System.getenv().get(filePath);
		}
        // 从默认路径中读取
		if (filePath_absolute == null) {
			try {
				InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath+".xml");
				doc = reader.read(in);
			} catch (DocumentException e) {
				logger.error("指定文件路径：" + filePath + "不存在！", e);
			}
			
		} else{
			try {
				InputStream in = new FileInputStream(filePath_absolute);
				doc = reader.read(in);
			} catch (DocumentException e) {
				logger.error("解析文件：" + filePath + "时出错！", e);
			} catch (FileNotFoundException e) {
				logger.error("指定文件路径：" + filePath + "不存在！", e);
			}
		}
		return doc;
	}
	
	//根据table表名获取字段
	public RefParamVO getMSConfig(String tableName) {
		// 得到根节点
		Element root = refConfigDocument.getRootElement();
		List<Element> ele = root.elements("table");
		RefParamVO refParamVO = new RefParamVO();
		Map<String,String> map = new HashMap<String,String>();
		List<String> list = new ArrayList<String>();
		for(Element e:ele){
			String value = e.attributeValue("name");
			if(tableName != null && tableName.equals(value)){
				refParamVO.setTablename(value);
				List<Element> showele = e.elements();
				for(Element showe : showele){
					String code = showe.attributeValue("code");
					String name = showe.getText();
					if("pidfield".equals(code)){
						refParamVO.setPidfield(name);
					}else{
						map.put(code,name);
						list.add(code);
					}
				}
				refParamVO.setShowcol(map);
				refParamVO.setExtcol(list);
				return refParamVO;
			}
		}
		return null;
	}
	//根据table表名获取字段
	public RefParamVO getMSConfigTree(String tableName) {
		// 得到根节点
		Element root = refConfigDocument.getRootElement();
		List<Element> ele = root.elements("tableTree");
		RefParamVO refParamVO = new RefParamVO();
		Map<String,String> map = new HashMap<String,String>();
		List<String> list = new ArrayList<String>();
		for(Element e:ele){
			String value = e.attributeValue("name");
			if(tableName != null && tableName.equals(value)){
				refParamVO.setTablename(value);
				List<Element> showele = e.elements();
				for(Element showe : showele){
					String code = showe.attributeValue("code");
					String name = showe.getText();
					if("pidfield".equals(code)){
						refParamVO.setPidfield(name);
					}else if("idfield".equals(code)){
						refParamVO.setIdfield(name);
					}else if("codefield".equals(code)){
						refParamVO.setCodefield(name);
					}else if("namefield".equals(code)){
						refParamVO.setNamefield(name);
					}
				}
				return refParamVO;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {

		RefParamVO refParamVO = SimpleParseXML.getInstance().getMSConfig("EXAMPLE_CONTACTS");
		RefParamVO refParamVOTree = SimpleParseXML.getInstance().getMSConfigTree("EXAMPLE_ORGANIZATION");
		System.out.println("123");
	}

}
