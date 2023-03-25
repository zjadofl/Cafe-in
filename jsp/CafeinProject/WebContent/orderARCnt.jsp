<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="org.json.simple.*"%>
<%
	request.setCharacterEncoding("UTF-8");

	ConnectDB connectDB = ConnectDB.getInstance();
	String returns = connectDB.orderARCntDB();
	out.print(returns);
	

%>


