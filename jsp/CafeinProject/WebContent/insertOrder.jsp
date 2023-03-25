<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ page import="cafein.ConnectDB"%>
<%
	request.setCharacterEncoding("UTF-8");
	String turn = request.getParameter("turn");
	String usNum = request.getParameter("usNum");
	String menuNum = request.getParameter("menuNum");
	String quantity = request.getParameter("quantity");
	String price = request.getParameter("price");
	String payment = request.getParameter("payment");
	String temperature = request.getParameter("temperature");
	String cup = request.getParameter("cup");
	String size = request.getParameter("size");

	
	ConnectDB connectDB = ConnectDB.getInstance();

	String returns = connectDB.insertOrderDB(turn, usNum, menuNum, quantity,
			price, payment, temperature, cup, size);
	out.print(returns);

%>


