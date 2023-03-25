<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="header.jsp" %>
<%
     int cnt2;
	 ConnectDB connectDB2 = ConnectDB.getInstance();
     Connection con2 = connectDB2.openConnection();
     Statement stmt2 = con2.createStatement();
     String sql2 = "select count(od_num) as cnt from order_ " 
     			  +"where od_status = '거부' "
                  +"and date(od_date) = date(now()) ";
     ResultSet rs2 = stmt2.executeQuery(sql2);
    
     while(rs2.next()) {
     	cnt2 = rs2.getInt("cnt");
     
      
%>   

<!-- 주문 거절 갯수 -->
<div class="col-md-3 col-sm-6 col-12" onClick="location.href='refuse_order_list.jsp'">
   <div class="info-box bg-danger">
     <span class="info-box-icon"><i class="far fa-bookmark"></i></span>

     <div class="info-box-content">
       <span class="info-box-text"  style="font-size:1rem">오늘 거절된 주문</span>
       <span class="info-box-number" style="font-size:1rem">
         <font style="font-size:1.5rem"><%= cnt2 %></font>개
       </span>
     </div>
     <!-- /.info-box-content -->
     <% 
        }
        rs2.close();
        stmt2.close();
        connectDB2.closeConnection(con2);
            
      %>       
  </div>
  <!-- /.info-box -->
</div>
<!-- /.col -->