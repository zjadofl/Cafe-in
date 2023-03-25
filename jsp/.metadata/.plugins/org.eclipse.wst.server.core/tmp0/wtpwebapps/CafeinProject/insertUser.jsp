<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="org.json.simple.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	String pUsID = request.getParameter("usID");
	String pUsPw = request.getParameter("usPw");
	String pUsName = request.getParameter("usName");
	String pUsBirth = request.getParameter("usBirth");

	
	ConnectDB connectDB = ConnectDB.getInstance();
	String returns = connectDB.registerDB(pUsID, pUsPw, pUsName);
	out.print(returns);
	
	//MakeJson makeJson = MakeJson.getInstance();
	
	//if (returns.equals("ok")) {
		//JSONObject userStrArr = makeJson.saveUser(pUsID, pUsPw, pUsName, pUsBirth);
		//System.out.println(userStrArr);
		
		//response.sendRedirect("saveJson.jsp?userJson="+userStrArr); 
	//}




%>


