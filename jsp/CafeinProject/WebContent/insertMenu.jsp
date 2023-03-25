<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.io.*"%>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.JSONParser" %>

<%
	request.setCharacterEncoding("UTF-8");
	String img = request.getParameter("img");
	String name = request.getParameter("name");
	String price = request.getParameter("price");
	String type = request.getParameter("type");
	String json = request.getParameter("json");
	
	System.out.println(json);

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.registerMenuDB(name, price, type);
	out.print(returns);
	
	
	//이미지를 제외한 메뉴 정보를 json으로 바꿔서 저장.
	String fileName="menu.json";
	String realPath = request.getRealPath("/json/");
	realPath += fileName;
	
	try {
		File f = new File(realPath); 
		f.createNewFile();
		
		//파일 쓰기
		FileWriter fw = new FileWriter(realPath);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(json);
		JSONArray jsonArr = (JSONArray) obj;
		
		fw.write(jsonArr.toJSONString());
		fw.close();
		
	} catch (IOException e) {
		System.out.println(e.toString());
	}
	
	
	
	
%>


