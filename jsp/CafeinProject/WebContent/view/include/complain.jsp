<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ko">
<%@ include file ="header.jsp" %>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
<%@ include file ="nav_header.jsp" %>  
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
   <%@ include file ="aside/cs_aside_all.jsp" %>  
  </aside>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h5 class="m-0 text-dark">CS관리</h5>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="index.jsp">홈</a></li>
              <li class="breadcrumb-item active">cs관리</li>
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
           <h3 class="card-title">CS 관리 목록</h3>
         </div>
         <!-- /.card-header -->
         <div class="card-body">
           <table id="menuTBL" class="table table-bordered table-striped">
             <thead>
             <tr>
               <th style="color:blue">No.</th>
               <th style="color:blue">작성자</th>
               <th style="color:blue">제목</th>
               <th style="color:blue">사유</th>
               <th style="color:blue">컴플레인 날짜</th>
               <th style="color:blue">작성날짜</th>       
               <th style="color:blue">답변</th>
               <th style="color:blue">수정</th>
             </tr>
             </thead>
             <tbody>
             <% //컴플레인에 대한 정보 가져오기
                int cp_cnt = 0;
                int cp_id;
                String us_id;
                String cp_title;
                String cp_type;
                String cp_name;
                String cp_content;
                String cp_date;
                String cp_wdate;
                String cp_reply, cp_status;
                ConnectDB connectDB = ConnectDB.getInstance();
                Connection con = connectDB.openConnection();
                Statement stmt = con.createStatement();
                String sql = "select cp_id, us_id, cp_title, cp_type, cp_date, " 
                      + "date(cp_wdate) as wdate, cp_reply, cp_content "
                      + "from complain, user "
                      + "where complain.us_num = user.us_num "
                      + "order by cp_id asc";
                ResultSet rs = stmt.executeQuery(sql);
               
                while(rs.next()) {
                   cp_cnt += 1;
                   cp_id = rs.getInt(1);
                   us_id = rs.getString(2);
                   cp_title = rs.getString(3);
                   cp_type = rs.getString(4);
                   cp_date = rs.getString(5);
                   cp_wdate = rs.getString(6);
                   
                if (rs.getString(7)== null || rs.getString(7).equals("") 
                       || rs.getString(7).equals("null")) { //답변이 없을시에 미답변으로 표시.
                   cp_status = "미답변";
                } else { //답변이 있을시에 답변완료로 표시.
                   cp_status = "답변완료";
                }
                if (rs.getString(7)== null || rs.getString(7).equals("") 
                       || rs.getString(7).equals("null")) { //답변이 없을시에 빈칸으로 표시.
                   cp_reply = "";
                } else { //답변이 있을시에 해당 답변을 표시.
                   cp_reply = rs.getString(7);
                }
                cp_content = rs.getString(8);  
           %>   
              <tr>
                 <td><%= cp_cnt %></td>
                 <td><%= us_id %></td>
                 <td><%= cp_title %></td>
                 <td><%= cp_type %></td>
                 <td><%= cp_date %></td>
                 <td><%= cp_wdate %></td>
                 <td><%= cp_status %></td>
                 <td>
                 <button type="button" class="btn btn-primary" 
               data-toggle="modal" data-target="#cp_edit"
               onclick="cp_edit('<%= cp_id %>', '<%= us_id %>', '<%= cp_title %>', '<%= cp_type %>', '<%= cp_date %>', 
               '<%= cp_wdate %>','<%= cp_reply %>', '<%= cp_content %>', '<%= cp_status %>');">상세</button>
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
               <th style="color:blue">작성자</th>
               <th style="color:blue">제목</th>
               <th style="color:blue">사유</th>
               <th style="color:blue">컴플레인 날짜</th>
               <th style="color:blue">작성날짜</th>       
               <th style="color:blue">답변</th>
               <th style="color:blue">수정</th>
             </tr>
             </tfoot>
           </table>
         </div>
         <!-- /.card-body -->
       </div>
       <!-- /.card -->
       
        <!-- 컴플레인 수정 모달 -->
       <div class="modal fade" id="cp_edit">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <b>
                [<span id="cp_type"></span>] &nbsp;
                <span id="cp_title"></span>
              </b>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <form name="complainEdit" method="post" action="">
                 <input type="hidden" class="form-control" id="cp_id" name="cp_id">
                 <div class="form-group">
                 <label for="us_id" class="control-label">사용자 아이디 : </label>
                 <input type="text" class="form-control" id="us_id" name="us_id" readonly="readonly">
                 </div>
                 
                 <div class="form-group">
                 <label for="us_id" class="control-label">작성일자 : </label>
                 <input type="text" class="form-control" id="cp_wdate" name="cp_wdate" readonly="readonly">
                 </div>
                 
                 <div class="form-group">
                 <label for="us_id" class="control-label">컴플레인 날짜 : </label>
                 <input type="text" class="form-control" id="cp_date" name="cp_date" readonly="readonly">
                 </div>
                      
                 <div class="form-group">
                 <label>컴플레인 내용</label>
                 <textarea name="cp_content" id="cp_content" class="form-control" rows="5" placeholder="" readonly="readonly"></textarea>
                 </div>
                 
                 <div class="form-group">
                   <label>답변</label>
                   <textarea name="cp_reply" id="cp_reply" class="form-control" rows="5" placeholder=""></textarea>
                 </div>
              </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
              <button type="button" id="cp_edit_btn" class="btn btn-primary" onClick="cpUpdate()">수정</button>
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