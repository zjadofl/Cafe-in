<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.io.*"%>
<%@ page import="org.json.simple.*" %>
<%@ page import="java.nio.charset.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="org.json.simple.parser.JSONParser" %>

<%
	request.setCharacterEncoding("UTF-8");


	//json
	String fileName="basicMenuInfo.json";
	String realPath = request.getRealPath("/json/");
	realPath += fileName;
	String content;
	
	Path path = Paths.get(realPath);
	
	try {
		File f = new File(realPath); 
		
		
		Charset cs = StandardCharsets.UTF_8;
		List<String> list = new ArrayList<String>();
		list = Files.readAllLines(path, cs);
		
		
		
		for(String readLine : list) {
			content = readLine;
			PrintWriter pw = response.getWriter();
			out.println(content);
		}
		
		
	} catch (IOException e) {
		System.out.println(e.toString());
	}
	
	
	
	
%>


