<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@page import="java.io.*"%>



<%
  // 데이터를 안드로이드에서 받음
	String covertImg = request.getParameter("covertImg");
	String name = request.getParameter("name");
	String price = request.getParameter("price");
	String explain = request.getParameter("explain");
	String radioCategory = request.getParameter("radioCategory");

	JSONObject jsonObj = new JSONObject();
	JSONArray menuArray = new JSONArray();
	JSONObject menuObj = new JSONObject();
	menuObj.put("convertImg", covertImg);
	menuObj.put("name", name);
	menuObj.put("price", price);
	menuObj.put("explain", explain);
	menuObj.put("type", radioCategory);
	menuArray.add(menuObj);
	jsonObj.put("menu", menuArray);
	
	try {
		 
		FileWriter file = new FileWriter("D:\\jsp\\CafeinProject\\WebContent\\json\\menu.json", true);
		file.write(jsonObj.toJSONString());
		file.flush();
		file.close();
 
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	out.println(jsonObj);

%>


