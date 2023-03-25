<%@ page language="java" contentType="text/html' charset-utf-8"
   pageEncoding="utf-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("utf-8");
   try{
      int mn_num, mn_price;
      String mn_name, mn_tem, mn_type, mn_img;
    //mn_num, mn_type, mn_img, mn_name, mn_price, mn_tem
    //mn_num, mn_name, mn_price, mn_type, mn_img, mn_tem
      mn_num = Integer.parseInt(request.getParameter("mn_num"));
      mn_name = request.getParameter("mn_name");
      mn_price = Integer.parseInt(request.getParameter("mn_price"));
      mn_type = request.getParameter("mn_type");
      mn_img = request.getParameter("mn_img");
      mn_tem = request.getParameter("mn_tem");
      
      
    
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      ConnectDB connectDB = ConnectDB.getInstance();
      con = connectDB.openConnection();
       String sql = "update menu set "
                   + "mn_name = ?, mn_price = ?, mn_type = ?, mn_img = ?, mn_tem = ? "
                   + "where mn_num = ?";
       pstmt = con.prepareStatement(sql);
       pstmt.setInt(1, mn_num);
       pstmt.setString(2, mn_name);
       pstmt.setInt(3, mn_price);
       pstmt.setString(4, mn_type);
       pstmt.setString(5, mn_img);
       pstmt.setString(6, mn_tem);
       
       pstmt.executeUpdate();
       pstmt.close();
       connectDB.closeConnection(con);
       response.sendRedirect("menu.jsp");
      
      
       return;
   } catch(Exception e) {
      e.printStackTrace();
   }
 %>