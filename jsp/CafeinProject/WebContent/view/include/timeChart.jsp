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
      var minTime = "10"; //영업시작시간
      var maxTime = "20"; //영업마감시간
      var chart1 = document.getElementById("canvas1");
      var myChart1 = new Chart(chart1, {
        type: 'line',
        data: {
          labels: [],
          datasets: [{
            data: [],
            borderWidth: 1,
            backgroundColor: [
            	'rgba(255, 99, 132, 0.1)',
            	'rgba(54, 162, 235, 0.1)',
            	'rgba(255, 206, 86, 0.1)',
            	'rgba(75, 192, 192, 0.1)',
            	'rgba(153, 102, 255, 0.1)',
            	'rgba(255, 159, 64, 0.1)'
            ],
            borderColor: [
   				'rgba(255, 99, 132, 1)', 
   				'rgba(54, 162, 235, 1)',
   				'rgba(255, 206, 86, 1)',
   				'rgba(75, 192, 192, 1)',
   				'rgba(153, 102, 255, 1)',
   				'rgba(255, 159, 64, 1)'
            ],
            datalabels: {
				align: 'end',
				anchor: 'end'
			},
            label: '수량',
            fill: false
          }]
        },
        options: {
          responsive: true,
          title: {
            display: true,
            text: "주문건수에 따른 시간표",
          },
          legend: {
            display: true
          },
          scales: {
			xAxes: [{
				type: 'time',
				time: {
					format: "HH",
					unit: 'hour',
					min: minTime,
					max: maxTime
				},
				scaleLabel: {
					display: true,
					labelString: '시간'
				}
			}],
			yAxes: [{
				scaleLabel: {
					display: true,
					labelString: '주문수'
				}
			}]
		  }
        }
      });

      var queryObject1 = "";
      var queryObjectLen1 = "";
      // logic to get new data
      $.ajax({
   	    method : 'POST',
        url: 'timeChart2.jsp',
        dataType : 'json',
        success: function(data) {
      	queryObject1 = eval('(' + JSON.stringify(data,null, 2) + ')');
      	queryObjectLen1 = queryObject1.listTime.length;
      	//alert(queryObject1.listTime[0].count);
        //alert(queryObjectLen1);
          for (var i = 0; i < queryObjectLen1; i++) {
             var count = queryObject1.listTime[i].count;
             //alert("개수: "+count);
             var time = queryObject1.listTime[i].time + "시";
           	 //alert("시간: "+time);
             //label은 x축(date), data은 y축(수량)
             myChart1.data.labels.push(time);
             myChart1.data.datasets[0].data.push(count);
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


  
