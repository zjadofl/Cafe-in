<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>

<%
	request.setCharacterEncoding("utf-8");
	String pUsID, pUsPw, pUsName;
	try {
				pUsID  = null;
				pUsPw = null;
				pUsName = null;
		    
				pUsID  = request.getParameter("usID");
				pUsPw = request.getParameter("usPw");
				pUsName = request.getParameter("usName");
				

		    /*--------jdbc를 통해 database  연결 하기-------*/
				Class.forName("com.mysql.jdbc.Driver");
		    Connection conn = DriverManager.getConnection 
	   		("jdbc:mysql://localhost:3306/testcafein?useSSL=false&serverTimezone=UTC","root","1111");
		    if(conn != null)
		    {
		 	    System.out.println("DB 연결  : success" + "<br>");
		    }else{
		 	    System.out.println("DB 연결  : fail" + "<br>");
		    };
				java.sql.PreparedStatement stat = conn.prepareStatement("insert into user"+
                            "(us_id, us_pw, us_name) values(?,?,?)");
				stat.setString(1,pUsID);
				stat.setString(2,pUsPw);
				stat.setString(3,pUsName);
				
				stat.executeUpdate();
				stat.close();
				conn.close();
	} catch (Exception e) {
		out.println(e);	
	}
%>