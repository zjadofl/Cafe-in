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
   <%@ include file ="aside/sales_aside_all.jsp" %>
</aside>

<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">매출관리</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">홈</a></li>
              <li class="breadcrumb-item active">매출관리</li>
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
        <div class="card">
         <div class="card-header">
           <h3 class="card-title"></h3>
           <br>
           <!-- Date picker -->
           <div class="input-group col-md-4">
             <div class="input-group-prepend">
               <span class="input-group-text">
                 <i class="far fa-calendar-alt"></i>
               </span>
             </div>
             <input type="text" class="form-control float-right" id="salesDP">
           </div>
           <!-- /.input group -->
         </div>
         <!-- /.card-header -->
         <div class="card-body">
           <table id="menuTBL" class="table table-bordered table-striped">
             <thead>
             <tr>
               <th>No.</th>
               <th>메뉴명</th>
               <th>주문 날짜</th>
               <th>총 수량</th>
               <th>총 가격</th>
             </tr>
             </thead>
             <tbody>
             
             <%
                int mn_cnt = 0;
                String mn_name;
                String  od_date;
                int od_amnt;
                int mn_price;
                ConnectDB connectDB = ConnectDB.getInstance();
                Connection con = connectDB.openConnection();
                Statement stmt = con.createStatement();
                String sql = "select mn_name, date(od_date) as date, sum(od_amnt) as amnt_sum, (mn_price * sum(od_amnt)) as total_price "
                         + "from order_, menu "
                         + "where menu.mn_num = order_.mn_num "
                         + "group by menu.mn_num "
                         + "order by amnt_sum desc";
                ResultSet rs = stmt.executeQuery(sql);
               
              while(rs.next()) {
                 mn_cnt += 1;
                 mn_name = rs.getString(1);
                 od_date = rs.getString(2);
                 od_amnt = rs.getInt(3);
                 mn_price = rs.getInt(4);
                 
                 
           %>   
              <tr>
                 <td><%= mn_cnt %></td>
                 <td><%= mn_name %></td>
                 <td><%= od_date %></td>
                 <td><%= od_amnt %></td>
                 <td><%= mn_price %></td>
                
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
               <th>No.</th>
               <th>메뉴명</th>
               <th>주문 날짜</th>
               <th>총 수량</th>
               <th>총 가격</th>
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
