<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cafein.ConnectDB"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.android.gcm.server.*"%>

<%
	request.setCharacterEncoding("UTF-8");
	//String json = request.getParameter("json");
	
	ArrayList<String> token = new ArrayList<String>();
	String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1);
	boolean SHOW_ON_IDLE = false;
	int LIVE_TIME = 1;
	int RETRY = 2;
	
	String simpleApiKey = "AIzaSyDIbvBXQMPiOFs5Z32tT_Vpnq7tFcyTdFk";
	String gcmUrl="https://fcm.googleapis.com/fcm/send";
	/*Connection conn = null; 

    PreparedStatement stmt = null; 
    ResultSet rs = null;*/

    try {
    	/*String jdbcUrl = "jdbc:mysql://localhost:3306/"'database이름'"; // MySQL 계정
    	String dbId = "userId"; // MySQL 계정
    	String dbPw = "userPwd"; // 비밀번호        
        String sql = "sql문"; // 등록된 token을 찾아오도록 하는 sql문
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
        stmt = conn.prepareStatement(sql);    
        rs = stmt.executeQuery();

        

        //모든 등록ID를 리스트로 묶음
        while(rs.next()){
              token.add(rs.getString("token")); //저장된 토큰을 가져와 ArrayList에 저장합니다.
        }

        conn.close();*/
        request.setCharacterEncoding("utf-8");
    	String title = new String("Notification 타이틀".getBytes("UTF-8"),"UTF-8");    
        //String msg = new String(request.getParameter("message").getBytes("UTF-8"), "UTF-8");   //메시지 한글깨짐 처리  // msg.jsp 에서 전달받은 메시지
        String msg = "테스트입니다.";
        out.print(msg);

        Sender sender = new Sender(simpleApiKey);
        Message message = new Message.Builder()
        .collapseKey(MESSAGE_ID)
        .delayWhileIdle(SHOW_ON_IDLE)
        .timeToLive(LIVE_TIME)
        .addData("message",msg)
        .addData("title",title)
        .build();


    }catch (Exception e) {
        e.printStackTrace();

    }



%>



