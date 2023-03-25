<%@ page language="java" contentType="text/html; charset=EUC-KR"
pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
  <%@ page import="cafein.ConnectDB"%>

<script>
function display_alert(){
   alert("매장이 등록 되었습니다!");
   }
</script>


<%
request.setCharacterEncoding("utf-8");
int st_cnt = 0;
int st_num =0 ;
String st_img;
String st_name;
String st_time;
String st_etime;
String st_content;
String st_postcode; 
String st_address;
String st_address2;

try{
   st_cnt +=1;
   st_num = 0;
   st_img = null;
   st_name = null;
   st_time = null;
   st_etime = null;
   st_content = null;
   st_postcode = null;
   st_address = null;
   st_address2 = null;

   
   st_num = Integer.parseInt(request.getParameter("st_num"));
   st_img = request.getParameter("st_img");
   if(st_img == null || st_img.trim().equals("")) {
      st_img = "0";
   } else {
      
   }
   st_name = request.getParameter("st_name");
   if(st_name == null || st_name.trim().equals("")) {
      st_name = "0";
   } else {
      
   }
   st_time = request.getParameter("st_time");
   if(st_time == null || st_time.trim().equals("")) {
      st_time = "0";
   } else {
      
   }
   st_etime = request.getParameter("st_etime");
   if(st_etime == null || st_etime.trim().equals("")) {
      st_etime = "0";
   } else {
      
   }
   st_content = request.getParameter("st_content");
   if(st_content == null || st_content.trim().equals("")) {
      st_content = "0";
   } else {
      
   }
   st_postcode = request.getParameter("st_postcode");
   if(st_postcode == null || st_postcode.trim().equals("")) {
      st_postcode = "0";
   } else {
      
   }
   st_address = request.getParameter("st_address");
   if(st_address == null || st_address.trim().equals("")) {
      st_address = "0";
   } else {
      
   }
   st_address2 = request.getParameter("st_address2");
   if(st_img == null || st_img.trim().equals("")) {
      st_img = "0";
   } else {
      
   }
   /*-------jdbc를 통해 database 연결 하기 -------*/
   ConnectDB connectDB = ConnectDB.getInstance();
   Connection conn = connectDB.openConnection();
   Statement stmt = conn.createStatement();
   
   if(conn != null){
      System.out.println("DB 연결 : success" + "<br>");
      } else {
         System.out.println("DB 연결 : fail" + "<br>");
   };
         
         java.sql.PreparedStatement stat = conn.prepareStatement("insert into store" + "(st_num, st_img, st_name, st_time, st_etime, st_content, st_postcode, st_address, st_address2) values(?,?,?,?,?,?,?,?,?)");
         stat.setInt(1, st_num);
         stat.setString(2, st_img);
         stat.setString(3, st_name);
         stat.setString(4, st_time);
         stat.setString(5, st_etime);
         stat.setString(6, st_content);
         stat.setString(7, st_postcode);
         stat.setString(8, st_address);
         stat.setString(9, st_address2);
         
         out.println(st_num);
         out.println(st_img);
         out.println(st_name);
         out.println(st_time);
         out.println(st_etime);
         out.println(st_content);
         out.println(st_postcode);
         out.println(st_address);
         out.println(st_address2);
         
         stat.executeUpdate();
         //conn.commit();
         stat.close(); 
         conn.close();
         response.sendRedirect("index.jsp");
         }
catch (Exception e){
   out.println(e);   }
%>