<%@page import="java.net.URLEncoder"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

 

<%@ page import="java.sql.*"%>

<%@ page import="java.util.*"%>

<%@ page import="com.google.android.gcm.server.*"%>

 

<%
	request.setCharacterEncoding("UTF-8");
	String token = request.getParameter("token");
	String title = request.getParameter("title");
	String message = request.getParameter("message");

    ArrayList<String> titleArr = new ArrayList<>();
    ArrayList<String> messageArr = new ArrayList<>();
    ArrayList<String> tokens = new ArrayList<>();
    
    tokens.add(token);


    String simpleApiKey = "AAAAKpxS_tM:APA91bEDlv8Xnr-C4soEG96WDaUquB_b8qrKC1sZME_tBXU6jTsvtxdH701lcbyY7i1PUUrO1aBlS_pdNOjCDaKt_XRu5TPtMkw0nxPC6uh4Jg7uImLd3HNyQiTVNIA82_sBnDm1BvPD";

    String gcmURL = "https://fcm.googleapis.com/fcm/send";    


    try {


        request.setCharacterEncoding("utf-8");

    	String title = new String("Notification 타이틀".getBytes("UTF-8"),"UTF-8");    

        String msg = new String(request.getParameter("message").getBytes("UTF-8"), "UTF-8");   //메시지 한글깨짐 처리  // msg.jsp 에서 전달받은 메시지



        out.print(msg);

        Sender sender = new Sender(simpleApiKey);

        Message message = new Message.Builder()

        .collapseKey(MESSAGE_ID)

        .delayWhileIdle(SHOW_ON_IDLE)

        .timeToLive(LIVE_TIME)

        .addData("message",msg)

        .addData("title",title)

        .build();


        MulticastResult result1 = sender.send(message,token,RETRY);

        if (result1 != null) {

            List<Result> resultList = result1.getResults();

            for (Result result : resultList) {

                System.out.println(result.getErrorCodeName()); 

            }

        }

    }catch (Exception e) {

        e.printStackTrace();

    }

%>
