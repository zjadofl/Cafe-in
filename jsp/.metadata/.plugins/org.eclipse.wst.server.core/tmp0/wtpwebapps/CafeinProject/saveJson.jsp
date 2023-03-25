<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.io.*" %>
 <%@ page import="json.MakeJson"%>
 <%@ page import="org.json.simple.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 생성</title>
</head>
<body>
	<% 
		request.setCharacterEncoding("UTF-8");
		String userInfo = request.getParameter("userJson");
	

		String realPath = application.getRealPath("/json/");
		File f = new File(realPath);
		if (!f.isFile()) {
			if(!f.isDirectory()){
				if(!f.isDirectory()) {
					f.mkdir();
				}
			}
		}
		
		String filename = "userInfo.json";
		PrintWriter writer = null;
		MakeJson makeJson = new MakeJson();
		writer = new PrintWriter(realPath+filename);
		String content = userInfo;
		System.out.println(content);
		writer.print(content);
		writer.close();
		
	%>

</body>
</html>