<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String phone = request.getParameter("phone");
	String address = request.getParameter("address");
	String date = request.getParameter("date");
	String part1 = request.getParameter("part1");
	String part2 = request.getParameter("part2");
	String num = request.getParameter("num");

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.updateEmployeeInfoDB(phone, address, date, part1, part2, num);
	out.print(returns);

%>