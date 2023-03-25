<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.io.*"%>
<%
	request.setCharacterEncoding("UTF-8");

	String userID = request.getParameter("usID");
	String num = request.getParameter("num");
	
	ConnectDB connectDB = ConnectDB.getInstance();
	String returns = connectDB.attendanceCheckDB(num);
	out.println(returns);
	if (returns.equals("ok")) {
		out.println("<script>alert('"+ userID + "님(으)로 근태체크 되었습니다!');</script>");
	} else {
		out.println("<script>alert('실패하였습니다.');</script>");
	}
%>
	

