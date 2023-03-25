<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String num = request.getParameter("num");
	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.refuseOrderDB(num);
	out.print(returns);

%>
