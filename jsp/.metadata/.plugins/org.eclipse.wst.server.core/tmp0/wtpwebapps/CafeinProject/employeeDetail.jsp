<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.io.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	String epNum = request.getParameter("epNum");
	
	ConnectDB connectDB = ConnectDB.getInstance();

	JSONObject returns = connectDB.employeeManageDB(epNum);
	out.println(returns);

%>


