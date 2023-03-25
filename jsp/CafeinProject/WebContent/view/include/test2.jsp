<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>매출현황</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
//-------------------------첫번째 차트-------------------------------------
    var queryObject1    = "";
    var queryObjectLen1 = "";
    $.ajax({
        type : 'POST',
        url : 'DBConnection.jsp',
        dataType : 'json',
        success : function(data) {
            queryObject1 = eval('(' + JSON.stringify(data,null, 2) + ')');             
            // stringify : 인자로 전달된 자바스크립트의 데이터(배열)를 JSON문자열로 바꾸기.   
            // eval: javascript 코드가 맞는지 검증하고 문자열을 자바스크립트 코드로 처리하는 함수 
            // queryObject.barlist[0].city ="korea"
 
            queryObjectLen1 = queryObject1.listGender.length;
            // queryObject.empdetails 에는 4개의 Json 객체가 있음 
 
            alert('queryObjectLength : ' + queryObjectLen1);
            // alert(queryObject.barlist[0].city +queryObject.barlist[0].per );
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
    });

    google.charts.load('current', { packages : [ 'corechart', 'bar' ] });
    google.charts.setOnLoadCallback(drawMultSeries);
 
    function drawMultSeries() {
         var data = new google.visualization.DataTable();
         data.addColumn('string', 'gender');
         data.addColumn('number', 'cntGender');
 
        //alert('data생성');
        for (var i = 0; i < queryObjectLen1; i++) {
            var gender    = queryObject1.listGender[i].gender;
            var cntGender = queryObject1.listGender[i].cntGender;
            data.addRows([[gender,cntGender]]);
        }
 
        var options = {
            title : '회원 성비',
            chartArea : {
                width : '50%'
            },
            hAxis : {
                title : '사용률',
                minValue : 0
            },
            vAxis : {
                title : 'Gender'
            }
        };
        var chart = new google.visualization.PieChart(document.getElementById('chart_userGender'));
        chart.draw(data, options);
    }
</script>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
//-------------------------두번째 차트-------------------------------------
    var queryObject2    = "";
    var queryObjectLen2 = "";
    $.ajax({
        type : 'POST',
        url : 'DBConnection.jsp',
        dataType : 'json',
        success : function(data) {
            queryObject2 = eval('(' + JSON.stringify(data,null, 2) + ')');             
            // stringify : 인자로 전달된 자바스크립트의 데이터(배열)를 JSON문자열로 바꾸기.   
            // eval: javascript 코드가 맞는지 검증하고 문자열을 자바스크립트 코드로 처리하는 함수 
            // queryObject.barlist[0].city ="korea"
 
            queryObjectLen2 = queryObject2.listProduct.length;
            // queryObject.empdetails 에는 4개의 Json 객체가 있음 
 
            alert('queryObjectLength : ' + queryObjectLen2);
            // alert(queryObject.barlist[0].city +queryObject.barlist[0].per );
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
    });

    google.charts.load('current', { packages : [ 'corechart', 'bar' ] });
    google.charts.setOnLoadCallback(drawMultSeries);
 
    function drawMultSeries() {
         var data = new google.visualization.DataTable();
         data.addColumn('string', 'category');
         data.addColumn('number', 'cntProduct');
 
        //alert('data생성');
        for (var i = 0; i < queryObjectLen2; i++) {
            var category   = queryObject2.listProduct[i].category;
            var cntProduct = queryObject2.listProduct[i].cntProduct;
            data.addRows([[category,cntProduct]]);
        }
 
        var options = {
            title : '상품 별 이용량',
            chartArea : {
                width : '50%'
            },
            hAxis : {
                title : '사용률',
                minValue : 0
            },
            vAxis : {
                title : 'Product'
            }
        };
        var chart = new google.visualization.BarChart(document.getElementById('chart_uselyProduct'));
        var chart2 = new google.visualization.ComboChart(document.getElementById('chart_uselyProduct2'));
        chart.draw(data, options);
        chart2.draw(data, options);
    }
</script>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
//-------------------------세번째 차트-------------------------------------
    var queryObject3    = "";
    var queryObjectLen3 = "";
    $.ajax({
        type : 'POST',
        url : 'DBConnection.jsp',
        dataType : 'json',
        success : function(data) {
            queryObject3 = eval('(' + JSON.stringify(data,null, 2) + ')');             
            // stringify : 인자로 전달된 자바스크립트의 데이터(배열)를 JSON문자열로 바꾸기.   
            // eval: javascript 코드가 맞는지 검증하고 문자열을 자바스크립트 코드로 처리하는 함수 
            // queryObject.barlist[0].city ="korea"
 
            queryObjectLen3 = queryObject3.listStation.length;
            // queryObject.empdetails 에는 4개의 Json 객체가 있음 
 
            alert('queryObjectLength : ' + queryObjectLen3);
            // alert(queryObject.barlist[0].city +queryObject.barlist[0].per );
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
    });

    google.charts.load('current', { packages : [ 'corechart', 'bar' ] });
    google.charts.setOnLoadCallback(drawMultSeries);
 
    function drawMultSeries() {
         var data = new google.visualization.DataTable();
         data.addColumn('string', 'st_no');
         data.addColumn('number', 'cntStation');
 
        //alert('data생성');
        for (var i = 0; i < queryObjectLen3; i++) {
            var st_no      = queryObject3.listStation[i].st_no;
            var cntStation = queryObject3.listStation[i].cntStation;
            data.addRows([[st_no,cntStation]]);
        }
 
        var options = {
            title : '정거장 별 이용량',
            chartArea : {
                width : '50%'
            },
            hAxis : {
                title : '이용률',
                minValue : 0
            },
            vAxis : {
                title : '정거장'
            }
        };
        var chart = new google.visualization.PieChart(document.getElementById('chart_uselyStation'));
        var chart2 = new google.visualization.BarChart(document.getElementById('chart_uselyStation2'));
        chart.draw(data, options);
        chart2.draw(data, options);
    }
</script>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
//-------------------------네번째 차트-------------------------------------
    var queryObject4    = "";
    var queryObjectLen4 = "";
    $.ajax({
        type : 'POST',
        url : 'DBConnection.jsp',
        dataType : 'json',
        success : function(data) {
            queryObject4 = eval('(' + JSON.stringify(data,null, 2) + ')');             
            // stringify : 인자로 전달된 자바스크립트의 데이터(배열)를 JSON문자열로 바꾸기.   
            // eval: javascript 코드가 맞는지 검증하고 문자열을 자바스크립트 코드로 처리하는 함수 
            // queryObject.barlist[0].city ="korea"
 
            queryObjectLen4 = queryObject4.listDate.length;
            // queryObject.empdetails 에는 4개의 Json 객체가 있음 
 
            alert('queryObjectLength : ' + queryObjectLen4);
            // alert(queryObject.barlist[0].city +queryObject.barlist[0].per );
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
    });

    google.charts.load('current', { packages : [ 'corechart', 'bar' ] });
    google.charts.setOnLoadCallback(drawMultSeries);
 
    function drawMultSeries() {
         var data = new google.visualization.DataTable();
         data.addColumn('string', 'WEEK_NAME');
         data.addColumn('number', 'cntDate');
 
        //alert('data생성');
        for (var i = 0; i < queryObjectLen4; i++) {
            var WEEK_NAME = queryObject4.listDate[i].WEEK_NAME;
            var cntDate   = queryObject4.listDate[i].cntDate;
            data.addRows([[WEEK_NAME,cntDate]]);
        }
 
        var options = {
            title : '요일 별 TAGO 이용량',
            chartArea : {
                width : '50%'
            },
            hAxis : {
                title : '이용률',
                minValue : 0
            },
            vAxis : {
                title : '요일'
            }
        };
        var chart = new google.visualization.AreaChart(document.getElementById('chart_Date'));
        chart.draw(data, options);
    }
</script>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
//-------------------------일곱번째 차트-------------------------------------
    var queryObject7    = "";
    var queryObjectLen7 = "";
    $.ajax({
        type : 'POST',
        url : 'DBConnection.jsp',
        dataType : 'json',
        success : function(data) {
            queryObject7 = eval('(' + JSON.stringify(data,null, 2) + ')');             
            // stringify : 인자로 전달된 자바스크립트의 데이터(배열)를 JSON문자열로 바꾸기.   
            // eval: javascript 코드가 맞는지 검증하고 문자열을 자바스크립트 코드로 처리하는 함수 
            // queryObject.barlist[0].city ="korea"
 
            queryObjectLen7 = queryObject7.listUsingGender.length;
            // queryObject.empdetails 에는 4개의 Json 객체가 있음 
 
            alert('queryObjectLength : ' + queryObjectLen7);
            // alert(queryObject.barlist[0].city +queryObject.barlist[0].per );
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
    });

var chartDrowFun = {
 
    chartDrow : function(){
        var chartData = '';
 
        //날짜형식 변경하고 싶으시면 이 부분 수정하세요.
        var chartDateformat     = 'yyyy년MM월dd일';
        //라인차트의 라인 수
        var chartLineCount    = 10;
        //컨트롤러 바 차트의 라인 수
        var controlLineCount    = 10;
 
 
        function drawDashboard() {
 
           var data = new google.visualization.DataTable();
            data.addColumn('datetime', 'date');
            data.addColumn('number', 'cntGender');
 
          //그래프에 표시할 데이터
          for (var i = 0; i < queryObjectLen7; i++) {
             var date      = queryObject7.listUsingGender[i].date;
              var cntGender = queryObject7.listUsingGender[i].cntGender;
              
              dataRow = [[date,cntGender]];
              data.addRows(dataRow);
          }
 
 
            var chart = new google.visualization.ChartWrapper({
              chartType   : 'LineChart',
              containerId : 'lineChartArea', //라인 차트 생성할 영역
              options     : {
                              isStacked   : 'percent',
                              focusTarget : 'category',
                              height          : 500,
                              width              : '100%',
                              legend          : { position: "top", textStyle: {fontSize: 13}},
                              pointSize        : 5,
                              tooltip          : {textStyle : {fontSize:12}, showColorCode : true,trigger: 'both'},
                              hAxis              : {format: chartDateformat, gridlines:{count:chartLineCount,units: {
                                                                  years : {format: ['yyyy년']},
                                                                  months: {format: ['MM월']},
                                                                  days  : {format: ['dd일']}}
                                                                },textStyle: {fontSize:12}},
                vAxis              : {minValue: 100,viewWindow:{min:0},gridlines:{count:-1},textStyle:{fontSize:12}},
                animation        : {startup: true,duration: 1000,easing: 'in' },
                annotations    : {pattern: chartDateformat,
                                textStyle: {
                                fontSize: 15,
                                bold: true,
                                italic: true,
                                color: '#871b47',
                                auraColor: '#d799ae',
                                opacity: 0.8,
                                pattern: chartDateformat
                              }
                            }
              }
            });
 
            var control = new google.visualization.ControlWrapper({
              controlType: 'ChartRangeFilter',
              containerId: 'controlsArea',  //control bar를 생성할 영역
              options: {
                  ui:{
                        chartType: 'LineChart',
                        chartOptions: {
                        chartArea: {'width': '60%','height' : 80},
                          hAxis: {'baselineColor': 'none', format: chartDateformat, textStyle: {fontSize:12},
                            gridlines:{count:controlLineCount,units: {
                                  years : {format: ['yyyy년']},
                                  months: {format: ['MM월']},
                                  days  : {format: ['dd일']}}
                            }}
                        }
                  },
                    filterColumnIndex: 0
                }
            });
 
            var date_formatter = new google.visualization.DateFormat({ pattern: chartDateformat});
            date_formatter.format(data, 0);
 
            var dashboard = new google.visualization.Dashboard(document.getElementById('Chart_Controls_UsingGender'));
            window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
            dashboard.bind([control], [chart]);
            dashboard.draw(data);
 
        }
          google.charts.setOnLoadCallback(drawDashboard);
 
      }
    }
$(document).ready(function(){
     google.charts.load('current', {'packages':['line','controls']});
     chartDrowFun.chartDrow(); //chartDrow() 실행
});
</script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
//-------------------------여덟번째 차트-------------------------------------

var chartDrowFun = {
 
    chartDrow : function(){
        var chartData = '';
 
        //날짜형식 변경하고 싶으시면 이 부분 수정하세요.
        var chartDateformat     = 'yyyy년MM월dd일';
        //라인차트의 라인 수
        var chartLineCount    = 10;
        //컨트롤러 바 차트의 라인 수
        var controlLineCount    = 10;
 
 
        function drawDashboard() {
 
          var data = new google.visualization.DataTable();
          //그래프에 표시할 컬럼 추가
          data.addColumn('datetime' , '날짜');
          data.addColumn('number'   , '남성');
          data.addColumn('number'   , '여성');
          data.addColumn('number'   , '전체');
 
          //그래프에 표시할 데이터
          var dataRow = [];
 
          for(var i = 0; i <= 29; i++){ //랜덤 데이터 생성
            var total   = Math.floor(Math.random() * 300) + 1;
            var man     = Math.floor(Math.random() * total) + 1;
            var woman   = total - man;
 
            dataRow = [new Date('2017', '09', i , '10'), man, woman , total];
            data.addRow(dataRow);
          }
 
 
            var chart = new google.visualization.ChartWrapper({
              chartType   : 'LineChart',
              containerId : 'lineChartArea', //라인 차트 생성할 영역
              options     : {
                              isStacked   : 'percent',
                              focusTarget : 'category',
                              height          : 500,
                              width              : '100%',
                              legend          : { position: "top", textStyle: {fontSize: 13}},
                              pointSize        : 5,
                              tooltip          : {textStyle : {fontSize:12}, showColorCode : true,trigger: 'both'},
                              hAxis              : {format: chartDateformat, gridlines:{count:chartLineCount,units: {
                                                                  years : {format: ['yyyy년']},
                                                                  months: {format: ['MM월']},
                                                                  days  : {format: ['dd일']},
                                                                  hours : {format: ['HH시']}}
                                                                },textStyle: {fontSize:12}},
                vAxis              : {minValue: 100,viewWindow:{min:0},gridlines:{count:-1},textStyle:{fontSize:12}},
                animation        : {startup: true,duration: 1000,easing: 'in' },
                annotations    : {pattern: chartDateformat,
                                textStyle: {
                                fontSize: 15,
                                bold: true,
                                italic: true,
                                color: '#871b47',
                                auraColor: '#d799ae',
                                opacity: 0.8,
                                pattern: chartDateformat
                              }
                            }
              }
            });
 
            var control = new google.visualization.ControlWrapper({
              controlType: 'ChartRangeFilter',
              containerId: 'controlsArea',  //control bar를 생성할 영역
              options: {
                  ui:{
                        chartType: 'LineChart',
                        chartOptions: {
                        chartArea: {'width': '60%','height' : 80},
                          hAxis: {'baselineColor': 'none', format: chartDateformat, textStyle: {fontSize:12},
                            gridlines:{count:controlLineCount,units: {
                                  years : {format: ['yyyy년']},
                                  months: {format: ['MM월']},
                                  days  : {format: ['dd일']},
                                  hours : {format: ['HH시']}}
                            }}
                        }
                  },
                    filterColumnIndex: 0
                }
            });
 
            var date_formatter = new google.visualization.DateFormat({ pattern: chartDateformat});
            date_formatter.format(data, 0);
 
            var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Chart'));
            window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
            dashboard.bind([control], [chart]);
            dashboard.draw(data);
 
        }
          google.charts.setOnLoadCallback(drawDashboard);
 
      }
    }
 
$(document).ready(function(){
  google.charts.load('current', {'packages':['line','controls']});
  chartDrowFun.chartDrow(); //chartDrow() 실행
});
</script>

</head>
<body>
<%@ include file="../../dbconn.jsp" %>
<h2>매출 현황</h2>

<form action="../index/index.jsp?section=../sales/sales_search_action.jsp" method="post">
<INPUT TYPE="button" name="btnInsert"  value="등록하기"   ONCLICK="document.location.href='index.jsp?section=../sales/sales_insert.jsp'" style="float: right;">
<select name="sk">
   <option value="sl.no">매출번호</option>
   <option value="sl.rt_no">반납번호</option>
</select>
&nbsp;
<input type="text" size="50" name="sv"><!--  검색값 : search value -->
<input type="submit" size="10" value="검색">
</form>

<table class="tchart">
<tr>
   <td><div id="chart_userGender"></div></td>
   <td><div id="chart_uselyProduct"></div></td>
   <td><div id="chart_uselyStation"></div></td>
</tr>

<tr>
   <td><div id="chart_Date"></div></td>
   <td><div id="chart_uselyProduct2"></div></td>
   <td><div id="chart_uselyStation2"></div></td>
</tr>

<tr>
   <td><div id="chart_userGender"></div></td>
   <td><div id="chart_uselyProduct2"></div></td>
   <td><div id="chart_uselyStation2"></div></td>
</tr>


<tr>
   <td colspan='3'>
      <div id="Chart_Controls_UsingGender">
      <!-- 라인 차트 생성할 영역 -->
      <div id="lineChartArea" style="padding:0px 20px 0px 0px;"></div>
      <!-- 컨트롤바를 생성할 영역 -->
      <div id="controlsArea" style="padding:0px 20px 0px 0px;"></div>
   </div>
</td>
</tr>
<tr>
   <td colspan='3'>
      <div id="Line_Controls_Chart">
      <!-- 라인 차트 생성할 영역 -->
      <div id="lineChartArea" style="padding:0px 20px 0px 0px;"></div>
      <!-- 컨트롤바를 생성할 영역 -->
      <div id="controlsArea" style="padding:0px 20px 0px 0px;"></div>
   </div>
</td>
</tr>
</table>
</body>

</html>