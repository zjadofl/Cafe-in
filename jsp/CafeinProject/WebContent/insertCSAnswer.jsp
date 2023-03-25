<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String pAnswer = request.getParameter("answer");
	String pCpID = request.getParameter("cpID");

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.insertCSAnswerDB(pAnswer, pCpID);
	out.print(returns);

%>


