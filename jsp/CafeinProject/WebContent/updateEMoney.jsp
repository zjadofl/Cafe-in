<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String eMoney = request.getParameter("eMoney");
	String usID = request.getParameter("usID");

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.updateEmoney(eMoney, usID);
	out.print(returns);

%>


