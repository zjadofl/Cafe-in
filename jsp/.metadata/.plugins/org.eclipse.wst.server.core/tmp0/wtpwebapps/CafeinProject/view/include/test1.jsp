<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="org.json.simple.JSONObject"%>

<%    
   //커넥션 선언
   Connection con = null;
   try {
    //드라이버 호출, 커넥션 연결
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    con = DriverManager.getConnection("jdbc:mysql://j3woody.cafe24.com/j3woody", "j3woody", "p3tjdwns");
   ResultSet rsGender      = null;
   ResultSet rsUsingGender = null;
   ResultSet rsProduct     = null;
   ResultSet rsStation     = null;
   ResultSet rsDate        = null;
   ResultSet rsTime        = null;


   //DB에서 뽑아온 데이터(JSON) 을 담을 객체. 후에 responseObj에 담기는 값
   List listGender      = new LinkedList();
   List listUsingGender = new LinkedList();
   List listProduct     = new LinkedList();
   List listStation     = new LinkedList();
   List listDate        = new LinkedList();
   List listTime        = new LinkedList();
   
   String queryGender      = "SELECT gender,count(*) AS cntGender FROM customer group by gender";
   String queryUsingGender = "SELECT DATE_FORMAT(rn.start_time, '%Y %M %D') AS date,gender,count(*) AS cntGender" +
                       " FROM customer AS cs " +
                       " INNER JOIN act_rent rn on cs.no = rn.cs_no group by date, gender;";
   String queryProduct     = "SELECT pr.category, count(rn.pr_no) AS cntProduct FROM act_rent AS rn " +
                        " INNER JOIN product pr ON rn.pr_no = pr.no GROUP BY category";
   String queryStation     = "SELECT pr.st_no AS st_no,count(pr.st_no) AS cntStation FROM act_rent AS rn " +
                        " INNER JOIN product pr ON rn.pr_no = pr.no GROUP BY st_no ORDER BY st_no ASC";
   String queryDate        = "SELECT CASE WHEN DAYOFWEEK(start_time) = 1 THEN '일' " +
                        " WHEN DAYOFWEEK(start_time) = 2 THEN '월' " +
                        " WHEN DAYOFWEEK(start_time) = 3 THEN '화' " +
                        " WHEN DAYOFWEEK(start_time) = 4 THEN '수' " +
                        " WHEN DAYOFWEEK(start_time) = 5 THEN '목' " +
                        " WHEN DAYOFWEEK(start_time) = 6 THEN '금' " +
                        " WHEN DAYOFWEEK(start_time) = 7 THEN '토' " +
                        " ELSE '오류' END WEEK_NAME, count(*)  AS cntDate " +
                        " FROM act_rent WHERE start_time GROUP BY DAYOFWEEK(start_time);";
   String queryTime    = "SELECT ";
   
   PreparedStatement pstmGender      = con.prepareStatement(queryGender);
   PreparedStatement pstmUsingGender = con.prepareStatement(queryUsingGender);
   PreparedStatement pstmProduct     = con.prepareStatement(queryProduct);
   PreparedStatement pstmStation     = con.prepareStatement(queryStation);
   PreparedStatement pstmDate        = con.prepareStatement(queryDate);
   
   rsGender      = pstmGender.executeQuery();
   rsUsingGender = pstmUsingGender.executeQuery();
   rsProduct     = pstmProduct.executeQuery();
   rsStation     = pstmStation.executeQuery();
   rsDate        = pstmDate.executeQuery();
   
   //ajax에 반환할 JSON 생성
   JSONObject responseObj = new JSONObject();
   
   JSONObject barObjGender      = null;
   JSONObject barObjUsingGender = null;
   JSONObject barObjProduct     = null;
   JSONObject barObjStation     = null;
   JSONObject barObjDate        = null;
   
   //소수점 2번째 이하로 자름
   DecimalFormat f1 = new DecimalFormat("");
   //rs의 다음값이 존재할 경우   
   while (rsGender.next()) {
      String gender   = rsGender.getString("gender");
      float cntGender = rsGender.getFloat("cntGender");
      barObjGender = new JSONObject();
      barObjGender.put("gender", gender);
      barObjGender.put("cntGender", (int)cntGender);
      listGender.add(barObjGender);
   }
   while (rsUsingGender.next()) {     
      String date     = rsUsingGender.getString("date");
      String gender   = rsUsingGender.getString("gender");
      float cntGender = rsUsingGender.getFloat("cntGender");
      barObjUsingGender = new JSONObject();
      barObjUsingGender.put("date", date);
      barObjUsingGender.put("gender", gender);
      barObjUsingGender.put("cntGender", (int)cntGender);
      listUsingGender.add(barObjUsingGender);    
   }                                                        
   while (rsProduct.next()) {
      String category  = rsProduct.getString("category");
      float cntProduct = rsProduct.getFloat("cntProduct");
      barObjProduct = new JSONObject();
      barObjProduct.put("category", category);
      barObjProduct.put("cntProduct", (int)cntProduct);
      listProduct.add(barObjProduct);
   }
   while (rsStation.next()) {
      String st_no     = rsStation.getString("st_no");
      float cntStation = rsStation.getFloat("cntStation");
      barObjStation = new JSONObject();
      barObjStation.put("st_no", st_no);
      barObjStation.put("cntStation", (int)cntStation);
      listStation.add(barObjStation);
   }
   while (rsDate.next()) {
      String WEEK_NAME = rsDate.getString("WEEK_NAME");
      float cntDate    = rsDate.getFloat("cntDate");
      barObjDate = new JSONObject();
      barObjDate.put("WEEK_NAME", WEEK_NAME);
      barObjDate.put("cntDate", (int)cntDate);
      listDate.add(barObjDate);
   }
   
   responseObj.put("listGender", listGender);
   responseObj.put("listUsingGender", listUsingGender);
   responseObj.put("listProduct", listProduct);
   responseObj.put("listStation", listStation);
   responseObj.put("listDate", listDate);
   
   out.print(responseObj.toString());
   
} catch (Exception e) {
   e.printStackTrace();
} finally {
   if (con != null) {
      try {
         con.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }   
}
%>