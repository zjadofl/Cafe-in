<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="ko">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="x-ua-compatible" content="ie=edge">

  <title>Cafe-in Admin</title>

  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="../../assets/plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../../assets/dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
  <!-- iconic icon -->
  <link href="../../assets/open-iconic-master/font/css/open-iconic-bootstrap.css" rel="stylesheet">
  <!-- DataTables -->
  <link rel="stylesheet" href="../../assets/plugins/datatables-bs4/css/dataTables.bootstrap4.css">
  <!-- custom css -->
  <link rel="stylesheet" href="../../assets/custom/custom.css">
  
  <!-- checkBox css -->
  <link href="../../assets/square/blue.css" rel="stylesheet">
  <script src="../../assets/icheck.js"></script>
  
  <!-- 제이쿼리 cdn -->
  <script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
  
  <%@ page import = "java.sql.*" %>
  <%@ page import="cafein.ConnectDB"%>
  

  
</head>


<body>
<div class="wrapper">
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <div class="content">
      <div class="container-fluid">
        <div class="row">
		<!-- content 시작 -->
<%      request.setCharacterEncoding("utf-8");
		String usID = request.getParameter("usID");
		int num = Integer.parseInt(request.getParameter("num"));
		String name = null;
		
		
		ConnectDB connectDB = ConnectDB.getInstance();
	    Connection con = connectDB.openConnection();
        String sql = "select ep_name from employee where ep_id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, usID);
        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next()) {
        	name = rs.getString(1);
	        if (name == null) {
	        	out.println("<script>alert('접속에 실패하셨습니다. 다시 접속해주세요.');</script>");
	        }
        
%>
		<form name="attend" action="attendanceCheck2.jsp" method="post">
			<ul style="list-style:none;">
			  <li><%= name %>님, 안녕하세요. <br>근태 종류를 선택해주세요.</li>
			  <li><input type="hidden" name="usNum" value="<%= num %>"></li>
			  <li><input type="hidden" name="usName" value="<%= name %>"></li>
			  <li><input type="radio" name="status" value="s" checked>출근</li>
			  <li><input type="radio" name="status" value="e">퇴근</li>
			  <li><input type="submit" value="확인" id="btn"></li>
			</ul>
		</form> 
		<br>
        </div>
        
<%      
        }
        rs.close();
        pstmt.close();
        connectDB.closeConnection(con);
%>         
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  </div>
  
 
  
  </script>

 </body>