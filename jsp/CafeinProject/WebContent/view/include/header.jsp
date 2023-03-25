<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="x-ua-compatible" content="ie=edge">

  <title>Cafe-in Admin</title>

  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="../../assets/plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../../assets/dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
  <!-- iconic icon -->
  <link href="../../assets/open-iconic-master/font/css/open-iconic-bootstrap.css" rel="stylesheet">
  <!-- DataTables -->
  <link rel="stylesheet" href="../../assets/plugins/datatables-bs4/css/dataTables.bootstrap4.css">
  <!-- custom css -->
  <link rel="stylesheet" href="../../assets/custom/custom.css">
  
  <!-- checkBox css -->
  <link href="../../assets/square/blue.css" rel="stylesheet">
  <script src="../../assets/icheck.js"></script>
  
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
  
  <!-- Tempusdominus Bbootstrap 4 -->
  <link rel="stylesheet" href="../../assets/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
  
  
  
  <%@ page import = "java.sql.*" %>
  <%@page import="java.util.*"%>
  <%@ page import="cafein.ConnectDB"%>
  <%@page import="org.json.simple.JSONObject"%>
</head>