<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("UTF-8");
   String name = request.getParameter("name");
   out.println(name);
   
   
   ConnectDB connectDB = ConnectDB.getInstance();

   JSONObject returns = connectDB.odHDB(name);
   out.print(returns);

%>