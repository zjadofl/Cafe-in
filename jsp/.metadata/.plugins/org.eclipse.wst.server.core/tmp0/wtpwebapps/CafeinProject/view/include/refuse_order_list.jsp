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
   <%@ include file ="aside/order_ref_aside_all.jsp" %> 
  </aside>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h5 class="m-0 text-dark">거부 목록</h5>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="index.jsp">홈</a></li>
              <li class="breadcrumb-item active">거부 목록</li>
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
      
      <%@ include file ="order_req_cnt.jsp" %>
      <%@ include file ="order_ref_cnt.jsp" %>
      <div class="col-12">
        <div class="card">
         <div class="card-header">
           <h3 class="card-title">거부 주문 목록</h3>
         </div>
         <!-- /.card-header -->
         <div class="card-body">
           <table id="menuTBL" class="table table-bordered table-striped">
             <thead>
             <tr>
               <th style="color:blue">주문번호</th>
               <th style="color:blue">메뉴이름</th>
               <th style="color:blue">메뉴수량</th>
               <th style="color:blue">거부 사유</th>
               <th style="color:blue">주문날짜</th>
               
             </tr>
             </thead>
             <tbody>
             
             <%
                int od_cnt = 0;
                int od_num;
                String mn_name;
                String od_amnt;
                String od_date;
                String od_reason;
                
                
                ConnectDB connectDB = ConnectDB.getInstance();
                Connection con = connectDB.openConnection();
                Statement stmt = con.createStatement();
                String sql = "select od_num, mn_name, od_amnt, od_reason, "
                         + "date_format(od_date, '%y-%m-%d %h:%m:%s') as date "
                         + "from order_, menu "
                         + "where order_.mn_num = menu.mn_num "
                         + "and od_status = '거부' "
                         + "order by od_date DESC";
                ResultSet rs = stmt.executeQuery(sql);
               
              while(rs.next()) {
                 od_num = rs.getInt(1);
                 mn_name = rs.getString(2);
                 od_amnt = rs.getString(3);
                 od_date = rs.getString(4);
                 od_reason = rs.getString(5);
       
                 
           %>   
              <tr>
                 <td><%= od_num %></td>
                 <td><%= mn_name %></td>
                 <td><%= od_amnt %></td>
                 <td><%= od_date %></td>
                 <td><%= od_reason %></td>
                 
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
               <th style="color:blue">주문번호</th>
               <th style="color:blue">메뉴이름</th>
               <th style="color:blue">메뉴수량</th>
               <th style="color:blue">거부 사유</th>
               <th style="color:blue">주문날짜</th>
             </tr>
             </tfoot>
           </table>
         </div>
         <!-- /.card-body -->
       </div>
       <!-- /.card -->
         
      
      
        
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


  