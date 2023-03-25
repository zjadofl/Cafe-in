<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String userNum = request.getParameter("userNum");
	String cTitle = request.getParameter("cTitle");
	String cType = request.getParameter("cType");
	String cDate = request.getParameter("cDate");
	String cContent = request.getParameter("cContent");
	String cWriteDate = request.getParameter("cWriteDate");
	
	System.out.println(userNum + "" + cTitle+ "" +cType+ "" +cDate+ "" +cContent+ "" +cWriteDate);
	
	
	System.out.println(userNum);

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.complainDB(userNum, cTitle, cType, cDate, cContent, cWriteDate);
	out.print(returns);



%>


