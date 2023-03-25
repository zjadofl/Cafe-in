<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.io.*"%>
<%@ page import="org.json.simple.*" %>
<%@ page import="java.nio.charset.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="org.json.simple.parser.JSONParser" %>

<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String json = request.getParameter("json");
	
	System.out.println("받아온 JSON : " + json);

	
	ConnectDB connectDB = ConnectDB.getInstance();

	
	//json
	String fileName="employeeInfo.json";
	String realPath = request.getRealPath("/json/");
	realPath += fileName;
	String content;
	
	Path path = Paths.get(realPath);
	
	try {
		File f = new File(realPath); 

		
		
		//파일 쓰기
		FileWriter fw = new FileWriter(realPath);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(json);
		
		fw.write(jsonObject.toJSONString());
		fw.close();
		
	} catch (IOException e) {
		System.out.println(e.toString());
	}
	
	
	


	
	
	
	
%>


