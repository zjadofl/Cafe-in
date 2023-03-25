<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page import="cafein.ConnectDB"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="org.json.simple.JSONObject"%>

<%  
try{
   ConnectDB connectDB = ConnectDB.getInstance();
   Connection con = connectDB.openConnection();
   String sql = "SELECT menu.mn_num, mn_name, mn_img, mn_price, SUM( od_amnt ) AS order_sum, mn_type FROM order_, menu "
            + "WHERE mn_type = 'c' "
            + "AND menu.mn_num = order_.mn_num AND od_status = '완료' "
            + "GROUP BY mn_name ORDER BY order_sum DESC LIMIT 3";
   PreparedStatement pstmt = con.prepareStatement(sql);
   ResultSet rs = pstmt.executeQuery();
   
   
   ConnectDB connectDB2 = ConnectDB.getInstance();
   Connection con2 = connectDB2.openConnection();
   String sql2 = "SELECT menu.mn_num, mn_name, mn_img, mn_price, SUM( od_amnt ) AS order_sum, mn_type FROM order_, menu "
            + "WHERE mn_type = 'n' "
            + "AND menu.mn_num = order_.mn_num AND od_status = '완료' "
            + "GROUP BY mn_name ORDER BY order_sum DESC LIMIT 3";
   PreparedStatement pstmt2 = con2.prepareStatement(sql2);
   ResultSet rs2 = pstmt2.executeQuery();
   
   ConnectDB connectDB3 = ConnectDB.getInstance();
   Connection con3 = connectDB3.openConnection();
   String sql3 = "SELECT menu.mn_num, mn_name, mn_img, mn_price, SUM( od_amnt ) AS order_sum, mn_type FROM order_, menu "
            + "WHERE mn_type = 'b' "
            + "AND menu.mn_num = order_.mn_num AND od_status = '완료' "
            + "GROUP BY mn_name ORDER BY order_sum DESC LIMIT 3";
   PreparedStatement pstmt3 = con3.prepareStatement(sql3);
   ResultSet rs3 = pstmt3.executeQuery();
   
   
   //DB에서 뽑아온 데이터(JSON) 을 담을 객체. 후에 responseObj에 담기는 값
   List listCoffee     = new LinkedList();
   List listNonCoffee     = new LinkedList();
   List listBakery     = new LinkedList();
   
   //ajax에 반환할 JSON 생성
   JSONObject responseObj = new JSONObject();
   
   JSONObject barCoffee      = null;
   JSONObject barNonCoffee     = null;
   JSONObject barBakery    = null;
   while (rs.next()) {
      barCoffee = new JSONObject();
      barCoffee.put("menuName", rs.getString("mn_name"));
      barCoffee.put("amount", rs.getInt("order_sum"));
      listCoffee.add(barCoffee);
    }
   
   while (rs2.next()) {
           barNonCoffee  = new JSONObject();
           barNonCoffee .put("menuName2", rs2.getString("mn_name"));
           barNonCoffee .put("amount2", rs2.getInt("order_sum"));
           listNonCoffee.add(barNonCoffee);
       }
   
   while (rs3.next()) {
       barBakery  = new JSONObject();
       barBakery .put("menuName3", rs3.getString("mn_name"));
       barBakery .put("amount3", rs3.getInt("order_sum"));
       listBakery.add(barBakery);
   }
   
   
   responseObj.put("listCoffee", listCoffee);
   responseObj.put("listNonCoffee", listNonCoffee);
   responseObj.put("listBakery", listBakery);
   
   
   
   out.print(responseObj.toString());
   
   rs.close();
   pstmt.close();
   connectDB.closeConnection(con);
   
   rs2.close();
   pstmt2.close();
   connectDB2.closeConnection(con2);
   
   rs3.close();
   pstmt3.close();
   connectDB3.closeConnection(con3);
   
   
} catch (Exception e) {
   e.printStackTrace();   
}
%>