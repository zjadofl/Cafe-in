<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<div class="form-group">
<table id="menuTBL" class="table table-bordered table-striped">
  <thead>
  <tr>
  
  <th></th>
  <th>이름</th>
  <th>양</th>
  </tr>
  </thead>
  <tbody>
  
  <%
     String stockName, in_num;
     ConnectDB connectDB2 = ConnectDB.getInstance();
     Connection con2 = connectDB2.openConnection();
     Statement stmt2 = con2.createStatement();
     String sql2 = "select in_num, in_name " +
                   "from ingredient "
                   +"order by in_num";
     ResultSet rs2 = stmt2.executeQuery(sql2);
    
     while(rs2.next()) {
    	in_num = rs2.getString(1);
    	stockName = rs2.getString(2);
        
%>   
   <tr>
      <td>
        <input type="checkbox" name="in_num" value=<%= in_num %>>
      </td>
      <td><%= stockName %></td>
      <td><input type="number" min="0" name="in_amnt"></td>   
   </tr>
<% 
   }
   rs2.close();
   stmt2.close();
   connectDB2.closeConnection(con2);
%>      
  </tbody>
  <tfoot>
  <tr>
  <th></th>
  <th>이름</th>
  <th>양</th>
  </tr>
  </tfoot>
</table>
</div>