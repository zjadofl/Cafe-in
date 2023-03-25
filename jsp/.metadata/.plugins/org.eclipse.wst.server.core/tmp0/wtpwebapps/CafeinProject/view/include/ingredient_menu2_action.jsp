<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
  
  <%
  	 request.setCharacterEncoding("utf-8");
     int mn_num = Integer.parseInt(request.getParameter("mn_num2"));
  	 String in_num3, in_name3, mf_amnt3;
     ConnectDB connectDB3 = ConnectDB.getInstance();
     Connection con3 = connectDB3.openConnection();
     PreparedStatement pstmt3 = null;
     String sql3 = "select ingredient.in_num, in_name, mn_num, mf_amnt " 
                    + "from manufacture, ingredient "
                    + "where mn_num = ? "
                    + "and manufacture.in_num = ingredient.in_num "
                    + "order by mn_num";
     pstmt3 = con3.prepareStatement(sql3);
     pstmt3.setInt(1, mn_num);
     ResultSet rs3 = pstmt3.executeQuery();
    
     while(rs3.next()) {
    	in_num3 = rs3.getString("in_num");
    	in_name3 = rs3.getString("in_name");
    	mf_amnt3 = rs3.getString("mf_amnt");
    	
    	out.println("<script>$('input[id=ingre" + in_name3 + "]').iCheck('check');</script>");
     }
     rs3.close();
     pstmt3.close();
     connectDB3.closeConnection(con3);
     
%>      