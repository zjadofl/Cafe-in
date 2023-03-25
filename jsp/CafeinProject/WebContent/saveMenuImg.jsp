<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>

<% 
	//이미지를 저장.
	String imgRealPath = application.getRealPath("/img/");
	
	int max = 10*1024*1024; //최대 크기.
	MultipartRequest mr = new MultipartRequest(request, imgRealPath, max, "UTF-8");

	String orgFileName = mr.getOriginalFileName("uploaded_file");
	String saveFileName = mr.getFilesystemName("uploaded_file");
%>