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
   <%@ include file ="aside/menu_aside_all.jsp" %> 
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
        String mn_name, mn_price, mn_type;
        String result;
        int mn_num;
       
        
        Connection con, con2 = null;
        PreparedStatement pstmt, pstmt2 = null;
        mn_name = null;
        mn_price = null;
        mn_type = null;
        
        mn_num = Integer.parseInt(request.getParameter("mn_num"));
        
        ConnectDB connectDB = ConnectDB.getInstance();
        con = connectDB.openConnection();
        String sql = "delete "
                 + "from menu "
                 + "where mn_num = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, mn_num);
        pstmt.executeUpdate();
        
        pstmt.close();
        connectDB.closeConnection(con);
      
        ConnectDB connectDB2 = ConnectDB.getInstance();
        con2 = connectDB2.openConnection();
        String sql2 = "delete "
                + "from manufacture "
                + "where mn_num = ?";
        pstmt2 = con2.prepareStatement(sql2);
        pstmt2.setInt(1, mn_num);
        pstmt2.executeUpdate();

        pstmt2.close();
        connectDB2.closeConnection(con2);
        


      
        out.println("<script>alert('삭제되었습니다.');location.href='menu.jsp';</script>");
        
        out.println("왜 안되지??");
        
        
        
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