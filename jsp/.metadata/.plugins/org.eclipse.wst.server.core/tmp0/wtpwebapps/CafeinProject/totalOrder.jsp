<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String sDate = request.getParameter("sDate");
	String eDate = request.getParameter("eDate");
	
	ConnectDB connectDB = ConnectDB.getInstance();

	JSONObject returns = connectDB.totalOrderDB(sDate, eDate);
	out.print(returns);



%>


