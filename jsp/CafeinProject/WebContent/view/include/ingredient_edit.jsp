<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="ko">
<%@ include file="header.jsp" %>
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
        
        <%
	        request.setCharacterEncoding("utf-8");
	        String in_name;
	        String result;
	        int in_num, in_amnt;
	       
	        
	        Connection con = null;
	        PreparedStatement pstmt= null;
	        in_num = Integer.parseInt(request.getParameter("in_num_edit"));
	        in_name = request.getParameter("in_name_edit");
	        in_amnt = Integer.parseInt(request.getParameter("in_amnt_edit"));
	        
	        out.println(in_num+" "+in_name+" "+in_amnt);
	        
	        ConnectDB connectDB = ConnectDB.getInstance();
	        con = connectDB.openConnection();
	        String sql = "update ingredient set in_name = ?, in_amnt = ? "
  					 + "where in_num = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, in_name);
	        pstmt.setInt(2, in_amnt);
	        pstmt.setInt(3, in_num);
	        pstmt.executeUpdate();
	        
	        pstmt.close();
	        connectDB.closeConnection(con);
	      
	        out.println("<script>location.href='ingredient.jsp';</script>");
	       
        
        
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