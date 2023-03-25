package com.example.cafeinadmin;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SearchAddress extends AppCompatActivity {

    private WebView addressWebView;
    private Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

    } //onCreate 끝

    public void init_webView() {
        addressWebView = (WebView) findViewById(R.id.addressWebView);

        // JavaScript 허용
        addressWebView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        addressWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        addressWebView.addJavascriptInterface(new AndroidBridge(), "testApp");

        // web client 를 chrome 으로 설정
        addressWebView.setWebChromeClient(new WebChromeClient());

        addressWebView.getSettings().setDatabaseEnabled(false);
        addressWebView.getSettings().setAllowFileAccess(false);
        addressWebView.getSettings().setDomStorageEnabled(false);
        addressWebView.getSettings().setAppCacheEnabled(false);

        // webview url load. php 파일 주소
        addressWebView.loadUrl("http://cafein.freehost.kr/addressWebView.jsp");
    }


    private class AndroidBridge extends WebViewClient {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //txt_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }

    }



}
