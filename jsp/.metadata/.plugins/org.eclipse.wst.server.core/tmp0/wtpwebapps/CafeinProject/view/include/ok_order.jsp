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
      
      
      <%
        request.setCharacterEncoding("utf-8");
        String us_name, mn_name, od_date, od_status, stockName;
        String result;
        int od_num, od_price, od_amnt;
       
        
        Connection con = null;
        PreparedStatement pstmt = null;
        result = "not ok";
        us_name = null;
        mn_name = null;
        od_date = null;
        od_status = null;
        od_price= 0;
        od_amnt = 0;
        
        od_num = Integer.parseInt(request.getParameter("od_num"));
        
        ConnectDB connectDB = ConnectDB.getInstance();
        con = connectDB.openConnection();
        String sql = "update order_ set od_status = '승인' "
                   + "where od_num = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, od_num);
        pstmt.executeUpdate();

        pstmt.close();
        connectDB.closeConnection(con);
        
        result = "ok";

        
        
        PreparedStatement pstmt2 = null;   
        ConnectDB connectDB2 = ConnectDB.getInstance();
        Connection con2 = connectDB2.openConnection();
        String sql3 = "update ingredient, order_, manufacture "
                     +"set in_amnt = in_amnt - (od_amnt*mf_amnt ) "
                     +"where order_.od_num = ? "
                 	 +"and ingredient.in_num = manufacture.mn_num "
                     +"and order_.mn_num = manufacture.mn_num ";
        
        pstmt2 = con2.prepareStatement(sql3);
        pstmt2.setInt(1, od_num);
        pstmt2.executeUpdate();
   
        pstmt2.close();
        connectDB2.closeConnection(con2);
        
        if (result.equals("ok")) {
              out.println("<script>alert('승인 처리하였습니다.');location.href='order.jsp';</script>");
        } else if (result.equals("not ok")) {
              out.println("<script>alert('승인 처리를 실패하셨습니다.');location.href='order.jsp';</script>");
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


  
