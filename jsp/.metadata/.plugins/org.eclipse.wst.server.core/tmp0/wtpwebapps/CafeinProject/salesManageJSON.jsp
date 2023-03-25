<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("UTF-8");
   String date = request.getParameter("date");

   
   ConnectDB connectDB = ConnectDB.getInstance();

   JSONObject returns = connectDB.salesManageDB(date);
   out.print(returns);



%>