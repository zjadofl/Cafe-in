<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="ko">
<%@ include file ="header.jsp" %>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
<%@ include file ="nav_header.jsp" %>  
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
	<%@ include file ="main_aside.jsp" %> 
  </aside>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">Starter Page</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Starter Page</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

	<!-- Main content -->
    <div class="content">
      <div class="container-fluid">
        <div class="row">
      <!-- content 시작 -->
      <div class="col-12">
      <script>
	   function display_alert() {
	      alert("회원정보가 등록 되었습니다.");
	   }
	  </script>
	
	<%
	   request.setCharacterEncoding("utf-8");
	   String ep_name, ep_id, ep_pw, ep_pos, ep_phone, ep_address, ep_sdate, ep_stime, ep_etime;;
	   try {
	      ep_name = null;
	      ep_id = null;
	      ep_pw = null;
	      ep_pos = null;
	      ep_phone = null;
	      
	      ep_name = request.getParameter("ep_name");
	      ep_id = request.getParameter("ep_id");
	      ep_pw = request.getParameter("ep_pw");
	      ep_pos = request.getParameter("ep_pos");
	      ep_phone = request.getParameter("ep_phone");
	      ep_address = request.getParameter("ep_address");
	      ep_sdate = request.getParameter("ep_sdate");
	      ep_stime = request.getParameter("ep_stime");
	      ep_etime = request.getParameter("ep_etime");
	      
	      ConnectDB connectDB = ConnectDB.getInstance();
	      Connection conn = connectDB.openConnection();
	      PreparedStatement stat = 
	            conn.prepareStatement("Insert into employee "
	                           + "(ep_name, ep_id, ep_pw, ep_pos, ep_phone, ep_address, ep_sdate, ep_stime, ep_etime) "
	                           + "values(?,?,?,?,?,?,?,?,?)");
	      stat.setString(1, ep_name);
	      stat.setString(2, ep_id);
	      stat.setString(3, ep_pw);
	      stat.setString(4, ep_pos);
	      stat.setString(5, ep_phone);
	      stat.setString(6, ep_address);
	      stat.setString(7, ep_sdate);
	      stat.setString(8, ep_stime);
	      stat.setString(9, ep_etime);
	      
	      stat.executeUpdate();
	      //conn.commit();
	      stat.close();
	      conn.close();
	      response.sendRedirect("index.jsp");
	   }
	   catch (Exception e) {
	      out.println(e);
	   }
	   
	%>
		
       
      </div>
      <!-- /.col-12 -->  

      <!-- content 끝 -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<%@ include file="footer.jsp" %>
</div>
<!-- ./wrapper -->    


  
