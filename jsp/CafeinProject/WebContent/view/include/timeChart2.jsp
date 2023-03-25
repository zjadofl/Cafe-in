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
   String sql1 = "select count(*) as od_cnt, date_format(od_date,'%H') as od_time "
	         	 + "from order_ "
	         	 + "group by  date_format(od_date,'%H')";
		  
   PreparedStatement pstmt1 = con1.prepareStatement(sql1);
   ResultSet rs1 = pstmt1.executeQuery();
   
   List listTime     = new LinkedList();

   JSONObject responseObj = new JSONObject();
   
   JSONObject barTime      = null;

   while (rs1.next()) {
	  barTime = new JSONObject();
	  barTime.put("count", rs1.getInt("od_cnt"));
	  barTime.put("time", rs1.getString("od_time"));
      listTime.add(barTime);
    }  
   
   responseObj.put("listTime", listTime);
   
   out.print(responseObj.toString());
   
   rs1.close();
   pstmt1.close();
   connectDB1.closeConnection(con1);
   
   
} catch (Exception e) {
   e.printStackTrace();   
}
%>