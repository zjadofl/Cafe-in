<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8" import="org.json.simple.*"%>



<%
  	// 초기 선언
  	JSONObject menuObj = new JSONObject();
	JSONObject menuObj1 = new JSONObject();
	JSONObject menuObj2 = new JSONObject();
	JSONObject menuObj3 = new JSONObject();
	JSONArray menuArray = new JSONArray();
	
	menuObj1.put("name", "아메리카노");
	menuObj2.put("price","1,500");
	menuObj3.put("img", "http://14.33.171.115:8080/CafeinProject/img/americano.jpg");


	menuArray.add(0, menuObj1);

	menuArray.add(0, menuObj2);

	menuArray.add(0, menuObj3);


	menuObj.put("Menu", menuArray);

	

        // 안드로이드에 보낼 데이터를 출력

	out.println(menuObj.toJSONString());
    out.flush();   

	

%>
