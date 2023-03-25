<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("UTF-8");
   String pUsNum = request.getParameter("usNum");

   
   ConnectDB connectDB = ConnectDB.getInstance();

   String returns = connectDB.useStampDB(pUsNum);
   out.print(returns);

%>