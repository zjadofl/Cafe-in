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
	String json = request.getParameter("json");
	
	System.out.println("받아온 JSON : " + json);

	
	ConnectDB connectDB = ConnectDB.getInstance();

	//String returns = connectDB.registerMenuDB(name, price, type);
	//out.print(returns);
	
	
	//json
	String fileName="basicMenuInfo.json";
	String realPath = request.getRealPath("/json/");
	realPath += fileName;
	String content;
	
	Path path = Paths.get(realPath);
	
	try {
		File f = new File(realPath); 
		//f.createNewFile();
		
		//파일 쓰기
		FileWriter fw = new FileWriter(realPath);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(json);
		JSONArray jsonArr = new JSONArray();
		JSONObject cafeNameObj = new JSONObject();
		jsonArr.add(jsonObject);
		cafeNameObj.put("카페드림", jsonArr);
		
		System.out.println("가게명 추가 : " + cafeNameObj);

		
		fw.write(cafeNameObj.toJSONString());
		fw.close();
		
	} catch (IOException e) {
		System.out.println(e.toString());
	}
	
	
	


	
	
	
	
%>


