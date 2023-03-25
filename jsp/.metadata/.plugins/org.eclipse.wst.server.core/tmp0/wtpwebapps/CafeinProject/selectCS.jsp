<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.io.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	ConnectDB connectDB = ConnectDB.getInstance();

	JSONObject returns = connectDB.selectCsDB();
	out.println(returns);

%>


