<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String pUsID = request.getParameter("usID");
	String pUsPw = request.getParameter("usPw");

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.empChangePwDB(pUsID, pUsPw);
	out.print(returns);

%>


