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
   <%@ include file ="aside/employee_aside_all.jsp" %> 
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
        <div class="card">
         <div class="card-header">
           <h3 class="card-title">테이블</h3>
         </div>
         <!-- /.card-header -->
         <div class="card-body">
           <table id="menuTBL" class="table table-bordered table-striped">
             <thead>
             <tr>
               <th style="color:blue">직원번호</th>
               <th style="color:blue">이름</th>
               <th style="color:blue">ID</th>
               <th style="color:blue">직급</th>
               <th style="color:blue">출근 시간</th>
               <th style="color:blue">퇴근 시간</th>
               <th style="color:blue">수정/상세</th>
             </tr>
             </thead>
             <tbody>
             <!-- ep_num, ep_name, ep_id, pos, stime, etime -->
             <%
                int ep_num;
                String ep_name, ep_id, ep_pos, ep_phone, ep_address, ep_sdate, ep_stime, ep_etime;
               
                
                ConnectDB connectDB = ConnectDB.getInstance();
                Connection con = connectDB.openConnection();
                Statement stmt = con.createStatement();
                String sql = "select ep_num, CASE WHEN ep_pos ='o' THEN '점주' WHEN ep_pos ='e' THEN '직원' "
                        + "END AS pos, ep_name, ep_id, ep_phone, ep_address, DATE_FORMAT(ep_sdate, '%Y-%m-%d') "
                        + "AS sdate, DATE_FORMAT(ep_stime, '%r') "
                        + "AS stime, DATE_FORMAT(ep_etime, '%r') "
                        + "AS etime from employee";
                ResultSet rs = stmt.executeQuery(sql);
               
              while(rs.next()) {
                 ep_num = rs.getInt("ep_num");
                 ep_pos = rs.getString("pos");
                 ep_name = rs.getString("ep_name");
                 ep_id = rs.getString("ep_id");
                  ep_phone = rs.getString("ep_phone");
                  ep_sdate = rs.getString("sdate");
                  ep_stime = rs.getString("stime");
                  ep_etime = rs.getString("etime");        
                 
           %>   
              <tr>
                 <td><%= ep_num %></td>
                 <td><%= ep_name %></td>
                 <td><%= ep_id %></td>
                 <td><%= ep_pos%></td>
                 <td><%= ep_stime %></td>
                 <td onclick="test()"><%= ep_etime %></td>
                 <td>
                    <button type="button" class="btn btn-block btn-primary" 
               data-toggle="modal" data-target="#ep_edit"
               onclick="ep_edit('<%= ep_num %>', '<%= ep_pos %>', '<%= ep_name %>', '<%= ep_id %>', '<%= ep_phone %>',
               '<%= ep_sdate %>', '<%= ep_stime %>', '<%= ep_etime %>');">수정</button>
               
               <button type="button" class="btn btn-block btn-primary" 
               data-toggle="modal" data-target="#ep_detail"
               onclick="ep_detail('<%= ep_num %>', '<%= ep_pos %>', '<%= ep_name %>', '<%= ep_id %>', '<%= ep_phone %>',
               '<%= ep_sdate %>', '<%= ep_stime %>', '<%= ep_etime %>');">상세</button>
               
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
               <th style="color:blue">직원번호</th>
               <th style="color:blue">이름</th>
               <th style="color:blue">ID</th>
               <th style="color:blue">직급</th>
               <th style="color:blue">출근 시간</th>
               <th style="color:blue">퇴근 시간</th>
               <th style="color:blue">수정/상세</th>
             </tr>
             </tfoot>
           </table>
         </div>
         <!-- /.card-body -->
       </div>
       <!-- /.card -->
       
      <!-- 직원 수정 모달 -->
       <div class="modal fade" id="ep_edit">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <b>
                 [
                <span id="ep_pos"></span>
                ] 
                <span id="ep_name"></span>
              </b>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <form name="employeeEdit" method="post" action="">
                 <input type="hidden" class="form-control" id="ep_num" name="ep_num">
                 <div class="form-group">
                   <label for="ep_id" class="control-label">아이디 : </label>
                   <input type="text" class="form-control" id="ep_id" name="ep_id" readonly="readonly">
                 </div>
                 <div class="form-group">
                   <label for="ep_phone" class="control-label">폰번호 : </label>
                   <input type="text" class="form-control" id="ep_phone" name="ep_phone" maxlength="11">
                 </div>
                 <div class="form-group">
                   <label for="ep_sdate" class="control-label">입사일 : </label>
                   <input type="text" class="form-control" data-inputmask-alias="datetime" 
                   id="ep_sdate" name="ep_sdate"
                   data-inputmask-inputformat="yyyy-mm-dd" data-mask>
                 </div>
                 

               <div class="bootstrap-timepicker">
                 <div class="form-group">
                   <label>출근 시간 :</label>

                   <div class="input-group date" id="timepicker2" data-target-input="nearest">
                     <input type="text" class="form-control datetimepicker-input" data-target="#timepicker2"
                     id="ep_stime" name="ep_stime"/>
                     <div class="input-group-append" data-target="#timepicker2" data-toggle="datetimepicker">
                         <div class="input-group-text"><i class="far fa-clock"></i></div>
                     </div>
                   </div>
                   <!-- /.input group -->
                 </div>
                 <!-- /.form group -->
               </div>
               
               <div class="bootstrap-timepicker">
                 <div class="form-group">
                   <label>퇴근 시간 :</label>

                   <div class="input-group date" id="timepicker" data-target-input="nearest">
                     <input type="text" class="form-control datetimepicker-input" data-target="#timepicker"
                     id="ep_etime" name="ep_etime"/>
                     <div class="input-group-append" data-target="#timepicker" data-toggle="datetimepicker">
                         <div class="input-group-text"><i class="far fa-clock"></i></div>
                     </div>
                   </div>
                   <!-- /.input group -->
                 </div>
                 <!-- /.form group -->
               </div>

              </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
              <button type="button" class="btn btn-primary" onClick="epUpdate()">수정</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
      
      <!-- 직원 상세 모달 -->
       <div class="modal fade" id="ep_detail">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <b>
                 [
                <span id="ep_pos_detail"></span>
                ] 
                <span id="ep_name_detail"></span>
              </b>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <form name="employeeEdit" method="post" action="">
                 <input type="hidden" class="form-control" id="ep_num" name="ep_num" readonly="readonly">
                 <div class="form-group">
                   <label for="ep_id" class="control-label">아이디 : </label>
                   <input type="text" class="form-control" id="ep_id" name="ep_id" readonly="readonly">
                 </div>
                 <div class="form-group">
                   <label for="ep_phone" class="control-label">폰번호 : </label>
                   <input type="text" class="form-control" id="ep_phone" name="ep_phone" readonly="readonly" maxlength="11">
                 </div>
                 <div class="form-group">
                   <label for="ep_sdate" class="control-label">입사일 : </label>
                   <input type="text" class="form-control" id="ep_sdate" name="ep_sdate" readonly="readonly">
                 </div>
                 <div class="form-group">
                   <label for="ep_stime" class="control-label">출근 시간 : </label>
                   <input type="text" class="form-control" id="ep_stime" name="ep_stime" readonly="readonly"> 
                 </div>
                 <div class="form-group">
                   <label for="ep_etime" class="control-label">퇴근 시간 : </label>
                   <input type="text" class="form-control" id="ep_etime" name="ep_etime" readonly="readonly">
                 </div>
              </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
              <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
             
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
