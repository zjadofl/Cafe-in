 <%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("UTF-8");
   String pOdNum = request.getParameter("odNum");

   
   ConnectDB connectDB = ConnectDB.getInstance();

   String returns = connectDB.updatestockDB(pOdNum);
   out.print(returns);

%>