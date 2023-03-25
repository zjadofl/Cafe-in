<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String pUsID = request.getParameter("usID");
	
	ConnectDB connectDB = ConnectDB.getInstance();

	JSONObject returns = connectDB.checkUserDB(pUsID);
	out.print(returns);



%>


