<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<script >
   function display_alert() {
      alert("사원정보가 등록 되었습니다!.");
   }
</script>

<%

	request.setCharacterEncoding("utf-8");
	String szCustno,szCustname,szPhone,szAddress,szJdate,szCgrade,szCity;
	try {
				szCustno    = null;
				szCustname  = null;
				szPhone     = null;
				szAddress   = null;
				szJdate     = null;
				szCgrade	  = null;
				szCity      = null;
		    
				szCustno    = request.getParameter("custno");
				szCustname  = request.getParameter("custname");
				szPhone     = request.getParameter("phone");
				szAddress   = request.getParameter("address");
				szJdate     = request.getParameter("joindate");
				szCgrade    = request.getParameter("grade");
				szCity      = request.getParameter("city");

		    /*--------jdbc를 통해 database  연결 하기-------*/
				Class.forName("com.mysql.jdbc.Driver");
		    Connection conn = DriverManager.getConnection 
	   		("jdbc:mysql://localhost:3306/JSPBookDB?useSSL=false&serverTimezone=UTC","root","1234");
		    if(conn != null)
		    {
		 	    System.out.println("DB 연결  : success" + "<br>");
		    }else{
		 	    System.out.println("DB 연결  : fail" + "<br>");
		    };
				java.sql.PreparedStatement stat = conn.prepareStatement("insert into member_tbl_02"+
                            "(custno ,custname ,phone ,address ,joindate, grade,city) values(?,?,?,?,?,?,?)");
				stat.setString(1,szCustno);
				stat.setString(2,szCustname);
				stat.setString(3,szPhone);
				stat.setString(4,szAddress);
				stat.setString(5,szJdate);
				stat.setString(6,szCgrade);
				stat.setString(7,szCity);
				
				stat.executeUpdate();
				//conn.commit();
				stat.close();
				conn.close();
				response.sendRedirect("index.jsp?section=member_list.jsp");
		}
	catch (Exception e) {
		out.println(e);	}
%>






