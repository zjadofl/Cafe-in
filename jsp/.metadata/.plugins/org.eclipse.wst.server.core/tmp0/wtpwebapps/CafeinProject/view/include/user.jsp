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
   <%@ include file ="aside/user_aside_all.jsp" %> 
</aside>

<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h5 class="m-0 text-dark">회원 관리</h5>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="index.jsp">홈</a></li>
              <li class="breadcrumb-item active">회원관리</li>
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
        <div class="col-12">
           <div class="card">
            <div class="card-header">
              <h3 class="card-title">회원정보</h3>
            </div>
            <!-- /.card-header -->
            <div class="card-body">
              <table id="menuTBL" class="table table-bordered table-striped">
                <thead>
                <tr>
                  <th style="color:blue">번호</th>
                  <th style="color:blue">사용자 ID</th>
                  <th style="color:blue">비밀번호</th>
                  <th style="color:blue">성함</th>
                  <th style="color:blue">E-머니</th>
                </tr>
                </thead>
                <tbody>
                
                <%
                   int userNum, eMoney;
                   String userID, userPW, userName;
                   ConnectDB connectDB = ConnectDB.getInstance();
                  Connection con = connectDB.openConnection();
                  Statement stmt = con.createStatement();
                  String sql = "select * from user";
                 ResultSet rs = stmt.executeQuery(sql);
                  
                 while(rs.next()) {
                    userNum = rs.getInt(1);
                    userID = rs.getString(2);
                    userPW = rs.getString(3);
                    userName = rs.getString(4);
                    eMoney = rs.getInt(5);
              %>   
                 <tr>
                    <td><%= userNum %></td>
                    <td><%= userID %></td>
                    <td><%= userPW %></td>
                    <td><%= userName %></td>
                    <td><%= eMoney %></td>
                 </tr>
              <% 
                 }
                 rs.close();
                 stmt.close();
                 connectDB.closeConnection(con);
              %>      
                </tbody>
                <tfoot>
                <tr>
                  <th style="color:blue">번호</th>
                  <th style="color:blue">사용자 ID</th>
                  <th style="color:blue">비밀번호</th>
                  <th style="color:blue">성함</th>
                  <th style="color:blue">E-머니</th>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
         
        
      </div>
      <!-- /.col-12 -->
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