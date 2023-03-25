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
   <%@ include file ="aside/home_aside_all.jsp" %> 
  </aside>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">

      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

   <!-- Main content -->
    <div class="content">
      <div class="container-fluid">
         
         <!-- 메뉴별 매출통계 -->
         <div class="row">
         
        <div class="col-md-4 chart-container">
           <canvas id="canvas1" style="height:250px; min-height:250px"></canvas>
        </div>

        <div class="col-md-4 chart-container">
           <canvas id="canvas2" style="height:250px; min-height:250px"></canvas>
        </div>

        <div class="col-md-4 chart-container">
           <canvas id="canvas3" style="height:250px; min-height:250px"></canvas>
        </div>

        </div>

        <script>
        //coffee 기본 차트 설정.
        var chart1 = document.getElementById("canvas1");
        var myChart1 = new Chart(chart1, {
          type: 'bar',
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
              borderColor: [
              'rgba(255, 99, 132, 1)', 
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)',
              'rgba(153, 102, 255, 1)',
              'rgba(255, 159, 64, 1)'
              ],
              label: '수량',
            }]
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            title: {
              display: true,
              text: "커피 카테고리에서 인기있는 메뉴 3종류",
            },
            legend: {
              display: false
            },
            scales: {
              yAxes: [{
                ticks: {
                  beginAtZero: true,
                }
              }]
            }
          }
        });

        var queryObject1 = "";
        var queryObjectLen1 = "";
          //데이터 삽입.
          $.ajax({
              method : 'POST',
            url: 'salesChartTest2.jsp',
            dataType : 'json',
            success: function(data) {
             queryObject1 = eval('(' + JSON.stringify(data,null, 2) + ')');
             queryObjectLen1 = queryObject1.listCoffee.length;
             //alert(queryObject1.listCoffee[0].menuName);
              //alert(queryObjectLen1);
              for (var i = 0; i < queryObjectLen1; i++) {
                 var menuName = queryObject1.listCoffee[i].menuName;
                 //alert(menuName);
                 var amount = queryObject1.listCoffee[i].amount;
                 //alert(amount);
                 myChart1.data.labels.push(menuName);
                 myChart1.data.datasets[0].data.push(amount);
              }
              myChart1.update();
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
            }
          });
        
          //non-coffee 기본 차트 설정.
          var chart2 = document.getElementById("canvas2");
          var myChart2 = new Chart(chart2, {
            type: 'bar',
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
                borderColor: [
                'rgba(255, 99, 132, 1)', 
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
                ],
                label: '수량',
              }]
            },
            options: {
               responsive: true,
                maintainAspectRatio: false,
              title: {
                display: true,
                text: "논커피 카테고리에서 인기있는 메뉴 3종류",
              },
              legend: {
                display: false
              },
              scales: {
                yAxes: [{
                  ticks: {
                    beginAtZero: true,
                  }
                }]
              }
            }
          });

          var queryObject2 = "";
          var queryObjectLen2 = "";
            //데이터 삽입.
            $.ajax({
                method : 'POST',
              url: 'salesChartTest2.jsp',
              dataType : 'json',
              success: function(data) {
               queryObject2 = eval('(' + JSON.stringify(data,null, 2) + ')');
               queryObjectLen2 = queryObject2.listNonCoffee.length;
               //alert(queryObject1.listCoffee[0].menuName);
                //alert(queryObjectLen1);
                for (var i = 0; i < queryObjectLen2; i++) {
                   var menuName2 = queryObject2.listNonCoffee[i].menuName2;
                   //alert(menuName);
                   var amount2 = queryObject2.listNonCoffee[i].amount2;
                   //alert(amount);
                   myChart2.data.labels.push(menuName2);
                   myChart2.data.datasets[0].data.push(amount2);
                }
                myChart2.update();
              },
              error : function(request,status,error) {
                  alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
              }
            });
          
            //backery 기본 차트 설정.
            var chart3 = document.getElementById("canvas3");
            var myChart3 = new Chart(chart3, {
              type: 'bar',
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
                  borderColor: [
                  'rgba(255, 99, 132, 1)', 
                  'rgba(54, 162, 235, 1)',
                  'rgba(255, 206, 86, 1)',
                  'rgba(75, 192, 192, 1)',
                  'rgba(153, 102, 255, 1)',
                  'rgba(255, 159, 64, 1)'
                  ],
                  label: '수량',
                }]
              },
              options: {
                 responsive: true,
                  maintainAspectRatio: false,
                title: {
                  display: true,
                  text: "베이커리 카테고리에서 인기있는 메뉴 3종류",
                },
                legend: {
                  display: false
                },
                scales: {
                  yAxes: [{
                    ticks: {
                      beginAtZero: true,
                    }
                  }]
                }
              }
            });

            var queryObject3 = "";
            var queryObjectLen3 = "";
              //데이터 삽입.
              $.ajax({
                  method : 'POST',
                url: 'salesChartTest2.jsp',
                dataType : 'json',
                success: function(data) {
                 queryObject3 = eval('(' + JSON.stringify(data,null, 2) + ')');
                 queryObjectLen3 = queryObject3.listBakery.length;
                 //alert(queryObject1.listCoffee[0].menuName);
                  //alert(queryObjectLen1);
                  for (var i = 0; i < queryObjectLen3; i++) {
                     var menuName3 = queryObject3.listBakery[i].menuName3;
                     //alert(menuName);
                     var amount3 = queryObject2.listBakery[i].amount3;
                     //alert(amount);
                     myChart3.data.labels.push(menuName3);
                     myChart3.data.datasets[0].data.push(amount3);
                  }
                  myChart3.update();
                },
                error : function(request,status,error) {
                    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
                }
              });
        
        
        </script>
		
	  <br><br><br><br>
      <!-- 주문 거절 유형 비율 & 컴플레인 접수 유형 비율  -->
      <div class="row">
         
           <!-- 주문 거절 유형 비율 -->
        <div class="col-md-6 chart-container">
           <canvas id="canvas4" style="height:250px; min-height:250px"></canvas>
        </div>
        
         <script>
	      // create initial empty chart
	      var chart4 = document.getElementById("canvas4");
	      var myChart4 = new Chart(chart4, {
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
	          responsive: true,
	          maintainAspectRatio: false,
	          title: {
	            display: true,
	            text: "거부사유에 대한 순위",
	          },
	          legend: {
	            display: true
	          }
	        }
	      });
	
	      var queryObject4 = "";
	      var queryObjectLen4 = "";
	      // logic to get new data
	      $.ajax({
	          method : 'POST',
	        url: 'refuseChart2.jsp',
	        dataType : 'json',
	        success: function(data) {
	         queryObject4 = eval('(' + JSON.stringify(data,null, 2) + ')');
	         queryObjectLen4 = queryObject4.listRefuse.length;
	         //alert(queryObject1.listRefuse[0].type);
	         //alert(queryObjectLen1);
	          for (var i = 0; i < queryObjectLen4; i++) {
	             var reason = queryObject4.listRefuse[i].reason;
	             //alert(reason);
	             var reason_cnt = queryObject4.listRefuse[i].cntReason;
	             //alert(reason_cnt);
	             myChart4.data.labels.push(reason);
	             myChart4.data.datasets[0].data.push(reason_cnt);
	          }
	          myChart4.update();
	        },
	        error : function(request,status,error) {
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
	        }
	      });
	      
	    </script>
        
        
        
        <!-- 컴플레인 접수 유형 비율 -->
        <div class="col-md-6 chart-container">
           <canvas id="canvas5" style="height:250px; min-height:250px"></canvas>
        </div>
         <script>
	      // create initial empty chart
	      var chart5 = document.getElementById("canvas5");
	      var myChart5 = new Chart(chart5, {
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
	          responsive: true,
	          maintainAspectRatio: false,
	          title: {
	            display: true,
	            text: "컴플레인 유형 비율",
	          },
	          legend: {
	            display: true
	          }
	        }
	      });
	
	      var queryObject5 = "";
	      var queryObjectLen5 = "";
	      // logic to get new data
	      $.ajax({
	          method : 'POST',
	        url: 'complainChart2.jsp',
	        dataType : 'json',
	        success: function(data) {
	         queryObject5 = eval('(' + JSON.stringify(data,null, 2) + ')');
	         queryObjectLen5 = queryObject5.listComplain.length;
	         //alert(queryObject1.listComplain[0].type);
	        //alert(queryObjectLen1);
	          for (var i = 0; i < queryObjectLen1; i++) {
	             var type = queryObject5.listComplain[i].type;
	             //alert(menuName);
	             var type_cnt = queryObject5.listComplain[i].cntType;
	             //alert(amount);
	             myChart5.data.labels.push(type);
	             myChart5.data.datasets[0].data.push(type_cnt);
	          }
	          myChart5.update();
	        },
	        error : function(request,status,error) {
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
	        }
	      });
	      
	
	      
	      
	      </script>
        
        
        </div>
        
        <br><br><br><br>
        <!-- 주문이 몰리는 시간에 대한 차트  -->
        <div class="row">
        <div class="col-lg-12 chart-container">
           <canvas id="canvas6" style="height:450px; min-height:450px"></canvas>
        </div> 
        </div>
        <script>
	      // create initial empty chart
	      var minTime = "10"; //영업시작시간
	      var maxTime = "20"; //영업마감시간
	      var chart6 = document.getElementById("canvas6");
	      var myChart6 = new Chart(chart6, {
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
	          maintainAspectRatio: false,
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
	
	      var queryObject6 = "";
	      var queryObjectLen6 = "";
	      // logic to get new data
	      $.ajax({
	          method : 'POST',
	        url: 'timeChart2.jsp',
	        dataType : 'json',
	        success: function(data) {
	         queryObject6 = eval('(' + JSON.stringify(data,null, 2) + ')');
	         queryObjectLen6 = queryObject6.listTime.length;
	         //alert(queryObject1.listTime[0].count);
	        //alert(queryObjectLen1);
	          for (var i = 0; i < queryObjectLen6; i++) {
	             var count = queryObject6.listTime[i].count;
	             //alert("개수: "+count);
	             var time = queryObject6.listTime[i].time + "시";
	               //alert("시간: "+time);
	             //label은 x축(date), data은 y축(수량)
	             myChart6.data.labels.push(time);
	             myChart6.data.datasets[0].data.push(count);
	          }
	          myChart6.update();
	        },
	        error : function(request,status,error) {
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
	        }
	      });
	      
	
	      
	      
	      </script>
      


      <!-- content 끝 -->

      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<%@ include file="footer.jsp" %>
</div>
<!-- ./wrapper -->    


  