<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page import="cafein.ConnectDB"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="org.json.simple.JSONObject"%>

<%  
try{
   ConnectDB connectDB1 = ConnectDB.getInstance();
   Connection con1 = connectDB1.openConnection();
   String sql1 = "select cp_type, count(cp_type) as cnt_type "
		         + "from complain " 
		         + "group by cp_type";
		  
   PreparedStatement pstmt1 = con1.prepareStatement(sql1);
   ResultSet rs1 = pstmt1.executeQuery();
   
   List listComplain     = new LinkedList();

   JSONObject responseObj = new JSONObject();
   
   JSONObject barComplain      = null;

   while (rs1.next()) {
	  barComplain = new JSONObject();
	  barComplain.put("type", rs1.getString("cp_type"));
	  barComplain.put("cntType", rs1.getInt("cnt_type"));
      listComplain.add(barComplain);
    }  
   
   responseObj.put("listComplain", listComplain);
   
   out.print(responseObj.toString());
   
   rs1.close();
   pstmt1.close();
   connectDB1.closeConnection(con1);
   
   
} catch (Exception e) {
   e.printStackTrace();   
}
%>