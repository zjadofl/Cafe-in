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
        int ep_num;
        String result;
        String ep_name, ep_id, ep_pos, ep_phone, ep_address, ep_sdate, ep_stime, ep_etime;
        
        Connection con = null;
        PreparedStatement pstmt = null;
        result = "not ok";
        ep_name = null;
        ep_id = null;
        ep_pos = null;
        ep_phone = null;
        ep_address= null;
        ep_sdate = null;
        ep_stime = null;
        ep_etime = null;
        
        ep_num = Integer.parseInt(request.getParameter("ep_num"));
        ep_phone = request.getParameter("ep_phone");
        ep_sdate = request.getParameter("ep_sdate");
        ep_stime = request.getParameter("ep_stime");
        ep_etime = request.getParameter("ep_etime");
        
        
        ConnectDB connectDB = ConnectDB.getInstance();
        con = connectDB.openConnection();
        String sql = "update employee set ep_phone = ?, ep_address = ?, ep_sdate = ?, ep_stime = ?, ep_etime = ? "
                 + "where ep_num = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, ep_num);
        pstmt.setString(2, ep_phone);
        pstmt.setString(3, ep_address);
        pstmt.setString(4, ep_sdate);
        pstmt.setString(5, ep_stime);
        pstmt.executeUpdate();

        pstmt.close();
        connectDB.closeConnection(con);
        
        result = "ok";

        if (result.equals("ok")) {
           out.println("<script>alert('수정되었습니다.')</script>");
        } else if (result.equals("not ok")) {
           out.println("<script>alert('수정을 실패하셨습니다.')</script>");
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

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
    <div class="p-3">
      <h5>Title</h5>
      <p>Sidebar content</p>
    </div>
  </aside>
  <!-- /.control-sidebar -->

  
<%@ include file="footer.jsp" %>
</div>
<!-- ./wrapper -->    
