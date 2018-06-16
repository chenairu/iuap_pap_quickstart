<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.OutputStream"
	import="java.io.FileInputStream"
	import="com.baidu.ueditor.ConfigManager"
	import="com.yonyou.iuap.example.base.utils.system.SystemUtil"
	import="org.springframework.core.io.support.PropertiesLoaderUtils"
	import="java.util.Properties"
	import="java.io.IOException"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

	String filePath = request.getParameter("path");
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=UTF-8");
    response.setContentType("application/octet-stream"); // 设置返回的文件类型
    if(filePath.indexOf("file")>-1){
        response.setContentType("application/x-msdownload");
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }
    
    String rootPath = "";
    String operationSystemName = SystemUtil.getOperationSystemName();
    Properties props = new Properties();
    try {
        props = PropertiesLoaderUtils.loadAllProperties("application.properties");
    } catch (IOException e) {
        e.printStackTrace();
    }
    if (operationSystemName.startsWith("Windows")) {
        Object windowsPath = props.get("file.windows.path");
        if (null != windowsPath) {
            rootPath = windowsPath.toString();
        }
    } else {
        Object linuxPath = props.get("file.linux.path");
        if (null != linuxPath) {
            rootPath = linuxPath.toString();
        }
    }

    FileInputStream fis = new FileInputStream(rootPath+filePath);
    int size = fis.available(); // 得到文件大小
    byte[] data = new byte[size];
    int count = 0;
    while (count != -1) {
        count = fis.read(data); // 读数据
    }
    fis.close();
    
    OutputStream os = response.getOutputStream();
    os.write(data);
    os.flush();
    os.close();
	
%>