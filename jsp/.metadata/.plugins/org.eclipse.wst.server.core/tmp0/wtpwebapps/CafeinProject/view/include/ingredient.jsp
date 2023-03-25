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
   <%@ include file ="aside/ingredient_aside_all.jsp" %> 
</aside>

<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">재료관리</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">홈</a></li>
              <li class="breadcrumb-item active">재료관리</li>
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
         <td>
         <button type="button" class="btn btn-primary float-right" 
         data-toggle="modal" data-target="#ingredient_insert"
         onclick="ingredient_insert">등록하기</button>
         </td>
         </div>
         <!-- /.card-header -->
         <div class="card-body">
           <table id="menuTBL" class="table table-bordered table-striped">
             <thead>
             <tr>
               <th>No.</th>
               <th>재료명</th>
               <th>재고 수량</th>
               <th>수정</th>
             </tr>
             </thead>
             <tbody>
             
             <%
                int in_num;
                String in_name;
                int in_amnt;
                ConnectDB connectDB = ConnectDB.getInstance();
                Connection con = connectDB.openConnection();
                Statement stmt = con.createStatement();
                String sql = "select in_num, in_name, in_amnt "
                      + "from ingredient ";
                      
                ResultSet rs = stmt.executeQuery(sql);
               
              while(rs.next()) {
                 in_num = rs.getInt(1);
                 in_name = rs.getString(2);
                 in_amnt = rs.getInt(3);
                 String color = null;
                 String soldOut = null;
                 if (rs.getInt(3) <= 400 ){
                    color = "red";
                 } 
                 if (rs.getInt(3) == 0){
                	 soldOut = "(품절)";
                 } else {
                	 soldOut = "";
                 }
                 
           %>
              <tr>
                 <td><%= in_num %></td>
                 <td><%= in_name %></td>
                 <td style="color:<%= color %>"><%= in_amnt %><%= soldOut %></td>
                 <td>
                    <button type="button" class="btn btn-primary" 
               data-toggle="modal" data-target="#in_edit"
               onclick="in_edit('<%= in_num %>', '<%= in_name %>', '<%= in_amnt %>');">수정</button>
               <button type="button" class="btn btn-danger" 
               onclick="location.href='ingredient_delete.jsp?in_num=<%= in_num %>'">삭제</button>
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
               <th>No.</th>
               <th>재료명</th>
               <th>재고 수량</th>
               <th>수정</th>
               
             </tr>
             </tfoot>
           </table>
         </div>
         <!-- /.card-body -->
       </div>
       <!-- /.card -->
       
       <!-- 재고 수정 모달 -->
       <div class="modal fade" id="in_edit">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <b>재료 수정</b>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <form name="ingredientUpdate" method="post" action="">
                 <input type="hidden" class="form-control" id="in_num_edit" name="in_num_edit">
                 <div class="form-group">
                   <label for="in_name_edit" class="control-label">재료명 : </label>
                   <input type="text" class="form-control" id="in_name_edit" name="in_name_edit" >
                 </div>
                 <div class="form-group">
                   <label for="in_amnt_edit" class="control-label">재고 수량 : </label>
                   <input type="number" class="form-control" id="in_amnt_edit" name="in_amnt_edit" >
                 </div>
              </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
              <button type="button" class="btn btn-primary" onclick="inUpdate()">수정</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
       
       <!-- 재고 등록 모달 -->
       <div class="modal fade" id="ingredient_insert">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
           
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <form name="ingredientInsert" method="post" action="">
                 <div class="form-group">
                   <label for="in_name" class="control-label">재료명 : </label>
                   <input type="text" class="form-control" id="in_name" name="in_name" >
                 </div>
                 <div class="form-group">
                   <label for="in_amnt" class="control-label">재고 수량 : </label>
                   <input type="number" class="form-control" id="in_amnt" name="in_amnt" >
                 </div>
                 
              </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
              <button type="button" class="btn btn-primary" onClick="inInsert()">등록</button>
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
