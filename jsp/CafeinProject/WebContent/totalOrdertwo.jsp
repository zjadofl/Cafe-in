<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("UTF-8");
   String sDate = request.getParameter("sDate");
   String eDate = request.getParameter("eDate");
   String status = request.getParameter("status");
   
   ConnectDB connectDB = ConnectDB.getInstance();

   JSONObject returns = connectDB.totalOrdertypeDB(sDate, eDate, status);
   out.print(returns);
   



%>