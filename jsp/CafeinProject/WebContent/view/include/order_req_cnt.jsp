<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="header.jsp" %>
<%
	 response.setIntHeader("Refresh", 2);
	 int cnt;
	 ConnectDB connectDB1 = ConnectDB.getInstance();
     Connection con1 = connectDB1.openConnection();
     Statement stmt1 = con1.createStatement();
     String sql1 = "select count(od_num) as cnt from order_ " 
     			  +"where od_status = '요청' "
                  +"and date(od_date) = date(now()) ";
     ResultSet rs1 = stmt1.executeQuery(sql1);
    
     while(rs1.next()) {
     	cnt = rs1.getInt("cnt");
     
      
%>   

<!-- 주문 요청 갯수 -->
<div class="col-md-3 col-sm-6 col-12" onClick="location.href='order.jsp'">
   <div class="info-box bg-success">
     <span class="info-box-icon"><i class="far fa-bookmark"></i></span>

     <div class="info-box-content">
       <span class="info-box-text"  style="font-size:1rem">요청된 주문</span>
       <span class="info-box-number" style="font-size:1rem">
         <font style="font-size:1.5rem"><%= cnt %></font>개
       </span>
     </div>
     <!-- /.info-box-content -->
     <% 
        }
        rs1.close();
        stmt1.close();
        connectDB1.closeConnection(con1);
            
      %>       
  </div>
  <!-- /.info-box -->
</div>
<!-- /.col -->