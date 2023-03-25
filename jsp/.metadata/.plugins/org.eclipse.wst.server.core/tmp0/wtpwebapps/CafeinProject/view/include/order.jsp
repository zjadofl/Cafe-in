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
   <%@ include file ="aside/order_req_aside_all.jsp" %> 
  </aside>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h5 class="m-0 text-dark">주문관리</h5>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="index.jsp">홈</a></li>
              <li class="breadcrumb-item active">주문관리</li>
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
      <!-- 카드 표 -->
      <div class="card">
       <div class="card-header">
         <h3 class="card-title">주문 요청 목록</h3>
       </div>
       <!-- /.card-header -->
       <div class="card-body">
         <table id="menuTBL" class="table table-bordered table-striped">
           <thead>
           <tr>
             <th style="color:blue">No.</th>
             <th style="color:blue">사용자</th>
             <th style="color:blue">메뉴번호</th>
             <th style="color:blue">가격</th> 
             <th style="color:blue">주문날짜</th>
             <th style="color:blue">주문 수량</th>
             <th style="color:blue">주문 승인/거부</th>
      
           </tr>
           </thead>
           <tbody>
           
           <%
              response.setIntHeader("Refresh", 2);
              int od_cnt = 0;
              int od_num;
              String us_name;
              String mn_name;
              String od_price;
              String od_date;
              int od_amnt;
              String od_status, od_reason;
              
              
              ConnectDB connectDB = ConnectDB.getInstance();
              Connection con = connectDB.openConnection();
              Statement stmt = con.createStatement();
              String sql = "select od_num, us_name, mn_name, format(od_price, 0), "
            		  	  +" date_format(od_date, '%h:%i:%s'), od_amnt, od_reason "
                       	  + "from order_, menu, user "
                          + "where od_status = '요청' "
                          + "and date(od_date) = date(now()) "
                          + "and order_.us_num = user.us_num "
                          + "and order_.mn_num = menu.mn_num";
              ResultSet rs = stmt.executeQuery(sql);
             
            while(rs.next()) {
               od_cnt += 1;
               od_num = rs.getInt(1);
               us_name = rs.getString(2);
               mn_name = rs.getString(3);
               od_price = rs.getString(4);
               od_date = rs.getString(5);
               od_amnt = rs.getInt(6);
               od_reason = rs.getString(7);
     
               
         %>   
            <tr>
               <td><%= od_num %></td>
               <td><%= us_name %></td>
               <td><%= mn_name %></td>
               <td><%= od_price %></td>
               <td><%= od_date %></td>
               <td><%= od_amnt %></td>

              <td>
                  <button type="button" class="btn btn-primary" 
                  onclick="location.href='ok_order.jsp?od_num=<%= od_num %>'">승인</button>
                  <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#ro_edit"
                  onclick="ro_edit('<%= od_num %>', '<%= mn_name %>', '<%= od_amnt %>', '<%= od_date %>', '<%= od_reason %>');">거부</button>
                  
                
              </td>
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
             <th style="color:blue">No.</th>
             <th style="color:blue">사용자</th>
             <th style="color:blue">메뉴번호</th>
             <th style="color:blue">가격</th> 
             <th style="color:blue">주문날짜</th>
             <th style="color:blue">주문 수량</th>
             <th style="color:blue">주문 승인/거부</th>
           </tr>
           </tfoot>
         </table>
       </div>
       <!-- /.card-body -->
     </div>
     <!-- /.card -->
     
     <!-- 거부 사유 모달 -->
      <div class="modal fade" id="ro_edit">
       <div class="modal-dialog">
         <div class="modal-content">
           <div class="modal-header">
                           거부 사유 작성
             <button type="button" class="close" data-dismiss="modal" aria-label="Close">
               <span aria-hidden="true">&times;</span>
             </button>
           </div>
           <div class="modal-body">
             <form name="orderEdit" method="post" action="">
                 <div class="form-group">
                 <label for="od_num" class="control-label">주문번호: </label>
                 <input type="text" class="form-control" id="od_num" name="od_num" readonly="readonly">
                 </div>
                 
                 <div class="form-group">
                 <label for="mn_name" class="control-label">메뉴이름: </label>
                 <input type="text" class="form-control" id="mn_name" name="mn_name" readonly="readonly">
                 </div>
                 
                 <div class="form-group">
                 <label for="mn_amnt" class="control-label">주문수량 : </label>
                 <input type="text" class="form-control" id="od_amnt" name="od_amnt" readonly="readonly">
                 </div>
                 
                 <div class="form-group">
                 <label for="od_date" class="control-label">주문 날짜 : </label>
                 <input type="text" class="form-control" id="od_date" name="od_date" readonly="readonly">
                 </div>
                      
                 <div class="form-group">
                   <label>거부사유</label>
                   <textarea name="od_reason" id="od_reason" class="form-control" rows="5" placeholder=""></textarea>
                 </div>
              </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
              <button type="button" id="od_edit_btn" class="btn btn-primary" onClick="odUpdate()">수정</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal --> 
      
      
      
      
      
      
        
         
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


  