<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.*"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="header.jsp" %>
<title>Insert title here</title>
</head>
 
<%
    // request.getRealPath("상대경로") 를 통해 파일을 저장할 절대 경로를 구해온다.
    // 운영체제 및 프로젝트가 위치할 환경에 따라 경로가 다르기 때문에 아래처럼 구해오는게 좋음
    String uploadPath = request.getRealPath("/uploadImg");
    //out.println("절대경로 : " + uploadPath +"<br/>");
     
    int maxSize =1024 *1024 *10;// 한번에 올릴 수 있는 파일 용량 : 10M로 제한
     
    String mnName ="";
     
    String fileName1 ="";// 중복처리된 이름
    String originalName1 ="";// 중복 처리전 실제 원본 이름
    long fileSize =0;// 파일 사이즈
    String fileType ="";// 파일 타입
    
    String menuType = null;
    String menuName = null;
    String menuTem = "";
    String menuIngre = "";
    String ingreAmnt = "";
    int maxNum = 0;
    int menuPrice = 0;
    String[] menuTemArr = null;
    String[] menuIngreArr = null;
    String[] ingreAmntArr = null;
     
    MultipartRequest multi = null;
     
    try{
        // request,파일저장경로,용량,인코딩타입,중복파일명에 대한 기본 정책
        multi =new MultipartRequest(request,uploadPath,maxSize,"UTF-8",new DefaultFileRenamePolicy());
         
        // name="mnName" 인 녀석 value를 가져옴
        mnName = multi.getParameter("mn_name");
         
        // 전송한 전체 파일이름들을 가져옴
        Enumeration files = multi.getFileNames();
         
        while(files.hasMoreElements()){
            // form 태그에서 <input type="file" name="여기에 지정한 이름" />을 가져온다.
            String file1 = (String)files.nextElement();// 파일 input에 지정한 이름을 가져옴
            // 그에 해당하는 실재 파일 이름을 가져옴
            originalName1 = multi.getOriginalFileName(file1);
            // 파일명이 중복될 경우 중복 정책에 의해 뒤에 1,2,3 처럼 붙어 unique하게 파일명을 생성하는데
            // 이때 생성된 이름을 filesystemName이라 하여 그 이름 정보를 가져온다.(중복에 대한 처리)
            fileName1 = multi.getFilesystemName(file1);
            // 파일 타입 정보를 가져옴
            fileType = multi.getContentType(file1);
            // input file name에 해당하는 실재 파일을 가져옴
            File file = multi.getFile(file1);
            // 그 파일 객체의 크기를 알아냄
            fileSize = file.length();
        }
           
        menuName = multi.getParameter("mn_name");
        menuPrice = Integer.parseInt(multi.getParameter("mn_price"));
        menuType = multi.getParameter("mn_Type");
        menuTemArr = multi.getParameterValues("mn_tem");
        menuIngreArr = multi.getParameterValues("in_num");
        //String[] arrMenuIngre = menuIngre.split(",");
        ingreAmntArr = multi.getParameterValues("in_amnt");
        
        //out.println("<script>alert('"+ menuType +"')</script>"); 
        //out.println("<script>alert('1+"+ maxNum +"');</script>");
        
        //여러 개의 체크박스의 값을 가져온다. (온도)
        for(int i=0; i <  menuTemArr.length; i++) {
             menuTem += menuTemArr[i];
        }
        
        /*for(int i=0; i <  menuIngreArr.length; i++) {
           ingreAmnt += ingreAmntArr[i] + ",";
        }
        out.println("<script>alert('1+"+ ingreAmnt +"');</script>");*/
        
        //테스트
        /*try{
            // 선택한 값이 없으면 back.
            if(menuIngre == null || menuIngreArr.length <= 0){
              out.println("<script>alert('선택한 항목이 없습니다.');history.back();</script>");
              return;
            }
            for(int i=0; i < menuIngreArr.length; i++){
              out.println("선택한 값 : " + menuIngreArr[i] + "<br>");
            }
            //out.println("<a href='javascript:history.back();'>뒤로</a>");
        }catch(Exception e){
            System.out.println(e);
        }*/
       
        
         
    }catch(Exception e){
        e.printStackTrace();
    }
    
    PreparedStatement pstmt = null;
   ConnectDB connectDB = ConnectDB.getInstance();
   Connection con = connectDB.openConnection();
   String sql = "insert into menu(mn_name, mn_price, mn_type, mn_img, mn_tem) values (?,?,?,?,?)";
   pstmt = con.prepareStatement(sql);
   pstmt.setString(1, menuName);
   pstmt.setInt(2, menuPrice);
   pstmt.setString(3, menuType);
   pstmt.setString(4, fileName1);
   pstmt.setString(5, menuTem);   
   pstmt.executeUpdate();
   
   
   //가장 큰 번호를 가져오는 것.
    PreparedStatement pstmt3 = null;
   ConnectDB connectDB3 = ConnectDB.getInstance();
   Connection con3 = connectDB3.openConnection();
   String sql3 = "select max(mn_num) from menu";
   pstmt3 = con3.prepareStatement(sql3);
    ResultSet rs3 = pstmt3.executeQuery();       
   
    while(rs3.next()) {
       maxNum = rs3.getInt(1);
    }
    rs3.close();
    pstmt3.close();
    connectDB3.closeConnection(con3);
	
    //빈값 제거.
    List<String> list = new ArrayList<String>();

    for(String s : ingreAmntArr) {
       if(s != null && s.length() > 0) {
          list.add(s);
       }
    }

    ingreAmntArr = list.toArray(new String[list.size()]);
    
    for(int i=0; i <  ingreAmntArr.length; i++) {
        out.println("ingreAmntArr : "+ ingreAmntArr[i] +";<br>");
        //out.println("ingreAmntArr : "+ ingreAmntArr[number] +";<br>");
    }
    
    int number;
    for(int i=0; i <  menuIngreArr.length; i++) {
       out.println("menuIngreArr : "+ menuIngreArr[i] +";<br>");
       //number = Integer.parseInt(menuIngreArr[i]); //i=0, 1;
       out.println("ingreAmntArr : "+ ingreAmntArr[i] +";<br>");
       //out.println("ingreAmntArr : "+ ingreAmntArr[number] +";<br>");
       
       //제조에 대한 쿼리.
        PreparedStatement pstmt2 = null;
       ConnectDB connectDB2 = ConnectDB.getInstance();
       Connection con2 = connectDB2.openConnection();
       String sql2 = null;
       
       sql2 = "insert into manufacture(in_num, mn_num, mf_amnt) values (?,?,?)";
       pstmt2 = con2.prepareStatement(sql2);
       pstmt2.setInt(1, Integer.parseInt(menuIngreArr[i]));
       pstmt2.setInt(2, maxNum);
       pstmt2.setInt(3, Integer.parseInt(ingreAmntArr[i]));
       pstmt2.executeUpdate();
       //out.println("<script>alert('"+ maxNum +"');</script>");
       
       pstmt2.close();
       connectDB2.closeConnection(con2);
    }
    
    
  
    
     //재료
    /*for(int i=0; i <  menuIngreArr.length; i++) {
       
       //제조에 대한 쿼리.
        PreparedStatement pstmt2 = null;
       ConnectDB connectDB2 = ConnectDB.getInstance();
       Connection con2 = connectDB2.openConnection();
       String sql2 = null;
       
       sql2 = "insert into manufacture(in_num, mn_num, mf_amnt) values (?,?,?)";
       pstmt2 = con2.prepareStatement(sql2);
       pstmt2.setInt(1, Integer.parseInt(menuIngreArr[i]));
       pstmt2.setInt(2, maxNum);
       pstmt2.setInt(3, Integer.parseInt(ingreAmntArr[i]));
       pstmt2.executeUpdate();
       //out.println("<script>alert('"+ maxNum +"');</script>");
       
       pstmt2.close();
       connectDB2.closeConnection(con2);
    }*/
   
   
   
   
   
   
   
   
%>
<!--
    해당 페이지는 사용자에게 보여줄 필요가 없고 해당 정보를 전달만 해주면 되기 때문에 hidden으로 했다.
 -->
<form action="fileCheck.jsp" method="post" name="fileCheckFormName">
    <input type="hidden" value="<%=mnName%>" name="mn_name" />
    <input type="hidden" value="<%=fileName1%>" name="fileName1" />
    <input type="hidden" value="<%=originalName1%>" name="originalName1" />
</form>

 
<!--
    a태그로 클릭시 파일체크하는 jsp페이지로 이동하도록 함
    javascript를 이용해서 onclick시 폼태그를 잡아와 submit()을 호출해 폼태그를 전송
 -->
<a href="#" onclick="javascript:document.fileCheckFormName.submit()">업로드 파일 확인하기 :<%=fileName1 %></a>

<% out.println("<script>history.back();</script>"); %>