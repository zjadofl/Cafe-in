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

      <form method="post" action="join_action.jsp" name="frmmemberInsert">
      <table>
      
      <tr>
         <td id="ep_name">이름</td>
         <td>
             <input type="text" name="ep_name" maxlength="50">
         </td>
      </tr>
      
      <tr>
         <td id="ep_id">아이디</td>
         <td>
             <input type="text" name="ep_id" maxlength="50" >
             <!--  <input type="button" name="checkid" value="중복확인" onclick="openIdchk()"> -->
         </td>
      </tr>
      
      <tr>
         <td id="ep_pw">비밀번호</td>
         <td>
             <input type="password" name="ep_pw" maxlength="50" >
         </td>
      </tr>
      
      <!--  <tr>
         <td id="ep_pw">비밀번호 확인</td>
         <td>
             <input type="password" name="ep_pwcheck" maxlength="50" >
         </td>
      </tr>-->
      
      <tr>
         <td id="ep_pos">직급</td>
         <td>
         <input type = "radio" name = "ep_pos" value = "점장">점장
         <input type = "radio" name = "ep_pos" value = "직원">직원
         </td>
      </tr>
      
      <tr>
         <td id="ep_phone">폰번호</td>
         <td>
             <input type="password" name="ep_phone" maxlength="50" >
         </td>
      </tr>
      
	  <tr>
         <td id="ep_address">지점 주소</td>
         <td>
             <input type="text" size="50" name="ep_address"/>
         </td>
      </tr>
         
      <tr>
         <td id ="ep_sdate"> 입사일 </td>
         <td>
            <input type='date' name="ep_sdate"/>
         </td>
      </tr>
       
      <tr>
         <td id ="ep_stime"> 출근 시간 </td>
         <td>
            <input type='time' name="ep_etime"/>
         </td>
      </tr>
       
      <tr>
         <td id ="ep_etime"> 퇴근 시간 </td>
         <td>
            <input type='time' name="ep_etime"/>
         </td>
      </tr>
      
      <tr align = "center">
 		 <td colspan="2" style="width:600px"> 
         <INPUT TYPE="button" class="btn btn-danger" onclick="location.href='join_action.jsp'" value="가입">
         <INPUT TYPE="button" name="btnQuery" onclick="location.href='index.jsp'" value="취소">
         </td>
      </tr>

      </table>
      </form>
      
      
      
      
      
      
        
         
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


  
