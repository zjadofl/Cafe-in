<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	Connection con = null;
	PreparedStatement pstmt= null;
	int usNum = Integer.parseInt(request.getParameter("usNum"));
	String usName = request.getParameter("usName");
	String status = request.getParameter("status");
	
	ConnectDB connectDB = ConnectDB.getInstance();
	con = connectDB.openConnection();
	String sql = "insert into attendance (ep_num, at_status) values (?,?)";
    pstmt = con.prepareStatement(sql);
    pstmt.setInt(1, usNum);
    pstmt.setString(2, status);
    pstmt.executeUpdate();
    
    if (status.equals("s")) {
    	status = "출근";
    } else {
    	status = "퇴근";
    }

	out.println("<script>alert('"+ status + "(으)로 근태체크 되었습니다!'); self.close();</script>");



%>
	