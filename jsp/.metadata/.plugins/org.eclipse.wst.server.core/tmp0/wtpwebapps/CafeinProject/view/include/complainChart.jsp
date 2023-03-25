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
      
      <div>
  		<canvas id="canvas1"></canvas>
	  </div>
      
      <script>
      // create initial empty chart
      var chart1 = document.getElementById("canvas1");
      var myChart1 = new Chart(chart1, {
        type: 'doughnut',
        data: {
          labels: [],
          datasets: [{
            data: [],
            borderWidth: 1,
            backgroundColor: [
            	'rgba(255, 99, 132, 0.2)',
            	'rgba(54, 162, 235, 0.2)',
            	'rgba(255, 206, 86, 0.2)',
            	'rgba(75, 192, 192, 0.2)',
            	'rgba(153, 102, 255, 0.2)',
            	'rgba(255, 159, 64, 0.2)'
            ],
            label: '수량'
          }]
        },
        options: {
          responsive: false,
          title: {
            display: true,
            text: "컴플레인 유형 비율",
          },
          legend: {
            display: true
          }
        }
      });

      var queryObject1 = "";
      var queryObjectLen1 = "";
      // logic to get new data
      $.ajax({
     	  method : 'POST',
        url: 'complainChart2.jsp',
        dataType : 'json',
        success: function(data) {
      	queryObject1 = eval('(' + JSON.stringify(data,null, 2) + ')');
      	queryObjectLen1 = queryObject1.listComplain.length;
      	//alert(queryObject1.listComplain[0].type);
        //alert(queryObjectLen1);
          for (var i = 0; i < queryObjectLen1; i++) {
             var type = queryObject1.listComplain[i].type;
             //alert(menuName);
             var type_cnt = queryObject1.listComplain[i].cntType;
             //alert(amount);
             myChart1.data.labels.push(type);
             myChart1.data.datasets[0].data.push(type_cnt);
          }
          myChart1.update();
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
      });
      

      
      
      </script>
      
      
      
        
         
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


  
