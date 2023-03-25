<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("UTF-8");
   String name = request.getParameter("name");
   ConnectDB connectDB = ConnectDB.getInstance();
   String returns = connectDB.showDetailTypeDB(name);
   out.print(returns);

%>