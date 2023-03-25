<%@ page language="java" contentType="text/html' charset-utf-8"
   pageEncoding="utf-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="cafein.ConnectDB"%>
<%
   request.setCharacterEncoding("utf-8");
   try{
      int cp_id;
      String reply;
      
      cp_id = Integer.parseInt(request.getParameter("cp_id"));
      reply = request.getParameter("cp_reply");
      
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      ConnectDB connectDB = ConnectDB.getInstance();
      con = connectDB.openConnection();
       String sql = "update complain set "
             + "cp_reply = ? "
             + "where cp_id = ?";
       pstmt = con.prepareStatement(sql);
       pstmt.setString(1, reply);
       pstmt.setInt(2, cp_id);
       pstmt.executeUpdate();
       pstmt.close();
       connectDB.closeConnection(con);
       response.sendRedirect("./complain.jsp");
      
      
       return;
   } catch(Exception e) {
      e.printStackTrace();
   }
 %>