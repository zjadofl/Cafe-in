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
     String stockName4, in_num4;
     ConnectDB connectDB4 = ConnectDB.getInstance();
     Connection con4 = connectDB4.openConnection();
     Statement stmt4 = con4.createStatement();
     String sql4 = "select in_num, in_name " +
                   "from ingredient "
                   +"order by in_num";
     ResultSet rs4 = stmt4.executeQuery(sql4);
    
     while(rs4.next()) {
    	in_num4 = rs4.getString(1);
    	stockName4 = rs4.getString(2);
        
%>   
   <tr>
      <td>
        <input type="checkbox" name="in_num2" id="ingre<%= stockName4 %>" value="ingre<%= stockName4 %>">
      </td>
      <td><%= stockName4 %></td>
      <td><input type="number" min="0" name="in_amnt2"></td>   
   </tr>
<% 
   }
   rs4.close();
   stmt4.close();
   connectDB4.closeConnection(con4);
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