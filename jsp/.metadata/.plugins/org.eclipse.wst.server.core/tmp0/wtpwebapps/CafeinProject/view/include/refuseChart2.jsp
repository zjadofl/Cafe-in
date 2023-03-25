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
   String sql1 = "select od_reason, count(od_reason) as cnt_reason "
               + "from order_ " 
               + "where od_status = '거부'"
               + "group by od_reason";
        
   PreparedStatement pstmt1 = con1.prepareStatement(sql1);
   ResultSet rs1 = pstmt1.executeQuery();
   
   List listRefuse     = new LinkedList();

   JSONObject responseObj = new JSONObject();
   
   JSONObject barRefuse      = null;

   while (rs1.next()) {
     barRefuse = new JSONObject();
     barRefuse.put("reason", rs1.getString("od_reason"));
     barRefuse.put("cntReason", rs1.getInt("cnt_reason"));
     listRefuse.add(barRefuse);
    }  
   
   responseObj.put("listRefuse", listRefuse);
   
   out.print(responseObj.toString());
   
   rs1.close();
   pstmt1.close();
   connectDB1.closeConnection(con1);
   
   
} catch (Exception e) {
   e.printStackTrace();   
}
%>