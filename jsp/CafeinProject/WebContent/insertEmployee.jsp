<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String pUsID = request.getParameter("usID");
	String pUsPw = request.getParameter("usPw");
	String pUsName = request.getParameter("usName");
	String ePos = request.getParameter("ePos");

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.regEmployeeDB(pUsID, pUsPw, pUsName, ePos);
	out.print(returns);
	
	//out.println(pUsID +"/"+pUsPw+"/"+pUsName);


%>


