<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("UTF-8");

   
   ConnectDB connectDB = ConnectDB.getInstance();

   String returns = connectDB.paymentManageDB();
   out.print(returns);



%>