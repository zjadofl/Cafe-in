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
            <h1 class="m-0 text-dark">매장 등록</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">홈</a></li>
              <li class="breadcrumb-item active">매장 등록</li>
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
         <h3 class="card-title">매장 등록</h3>
       </div>
       <!-- /.card-header -->
       <div class="card-body">
         <form role="form" method="post" action="" enctype="Multipart/form-data">
           <!-- 이미지 -->
           <div class="row">
             <div class="col-sm-12">
               <div class="form-group">
               	 <a href="javascript:" onclick="fileUploadAction();" class="img_btn">이미지 업로드</a>
                 <label id="store_img">매장이미지</label>
                 <input type="file" class="form-control" id="store_img" multiple/>
               </div>
               
               <div class="form-group">
                 <div class="imgs_bg">
                   <img id="img"/>
                 </div>
               </div>
               
             </div>
           </div>
           
           <div class="row">
             <div class="col-sm-6">
               <div class="form-group">
                 <label>매장이름</label>
                 <input type="text" class="form-control">
               </div>
             </div>
             <div class="col-sm-6">
               <div class="form-group">
                 <label>Text Disabled</label>
                 <input type="text" class="form-control" placeholder="Enter ..." disabled>
               </div>
             </div>
           </div>
           <div class="row">
             <div class="col-sm-6">
               <!-- textarea -->
               <div class="form-group">
                 <label>Textarea</label>
                 <textarea class="form-control" rows="3" placeholder="Enter ..."></textarea>
               </div>
             </div>
             <div class="col-sm-6">
               <div class="form-group">
                 <label>Textarea Disabled</label>
                 <textarea class="form-control" rows="3" placeholder="Enter ..." disabled></textarea>
               </div>
             </div>
           </div>

           <!-- input states -->
           <div class="form-group">
             <label class="col-form-label" for="inputSuccess"><i class="fas fa-check"></i> Input with
               success</label>
             <input type="text" class="form-control is-valid" id="inputSuccess" placeholder="Enter ...">
           </div>
           <div class="form-group">
             <label class="col-form-label" for="inputWarning"><i class="far fa-bell"></i> Input with
               warning</label>
             <input type="text" class="form-control is-warning" id="inputWarning" placeholder="Enter ...">
           </div>
           <div class="form-group">
             <label class="col-form-label" for="inputError"><i class="far fa-times-circle"></i> Input with
               error</label>
             <input type="text" class="form-control is-invalid" id="inputError" placeholder="Enter ...">
           </div>

           <div class="row">
             <div class="col-sm-6">
               <!-- checkbox -->
               <div class="form-group">
                 <div class="form-check">
                   <input class="form-check-input" type="checkbox">
                   <label class="form-check-label">Checkbox</label>
                 </div>
                 <div class="form-check">
                   <input class="form-check-input" type="checkbox" checked>
                   <label class="form-check-label">Checkbox checked</label>
                 </div>
                 <div class="form-check">
                   <input class="form-check-input" type="checkbox" disabled>
                   <label class="form-check-label">Checkbox disabled</label>
                 </div>
               </div>
             </div> 
             <div class="col-sm-6">
               <!-- radio -->
               <div class="form-group">
                 <div class="form-check">
                   <input class="form-check-input" type="radio" name="radio1">
                   <label class="form-check-label">Radio</label>
                 </div>
                 <div class="form-check">
                   <input class="form-check-input" type="radio" name="radio1" checked>
                   <label class="form-check-label">Radio checked</label>
                 </div>
                 <div class="form-check">
                   <input class="form-check-input" type="radio" disabled>
                   <label class="form-check-label">Radio disabled</label>
                 </div>
               </div>
             </div>
           </div>

           <div class="row">
             <div class="col-sm-6">
               <!-- select -->
               <div class="form-group">
                 <label>Select</label>
                 <select class="form-control">
                   <option>option 1</option>
                   <option>option 2</option>
                   <option>option 3</option>
                   <option>option 4</option>
                   <option>option 5</option>
                 </select>
               </div>
             </div>
             <div class="col-sm-6">
               <div class="form-group">
                 <label>Select Disabled</label>
                 <select class="form-control" disabled>
                   <option>option 1</option>
                   <option>option 2</option>
                   <option>option 3</option>
                   <option>option 4</option>
                   <option>option 5</option>
                 </select>
               </div>
             </div>
           </div>

           <div class="row">
             <div class="col-sm-6">
               <!-- Select multiple-->
               <div class="form-group">
                 <label>Select Multiple</label>
                 <select multiple class="form-control">
                   <option>option 1</option>
                   <option>option 2</option>
                   <option>option 3</option>
                   <option>option 4</option>
                   <option>option 5</option>
                 </select>
               </div>
             </div>
             <div class="col-sm-6">
               <div class="form-group">
                 <label>Select Multiple Disabled</label>
                 <select multiple class="form-control" disabled>
                   <option>option 1</option>
                   <option>option 2</option>
                   <option>option 3</option>
                   <option>option 4</option>
                   <option>option 5</option>
                 </select>
               </div>
             </div>
           </div>
         </form>
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


  
