package com.example.cafeinadmin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shuhart.stepview.StepView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class RegStep extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private int currentStep = 0; //step
    private Boolean validateVal;
    final Context context = this;
    private String ePos;

    //정규식
    //이름 조건: 한글 이름만
    public static final Pattern NAME_REGEX
            = Pattern.compile("^[가-힣]*$"); //공백불가 추가하기


    //아이디 조건: 필수 영숫자 7 ~ 13자리.
    public static final Pattern ID_REGEX
            = Pattern.compile("^"+
            "(?=.*[0-9])" +       //at least 1 digit
            "(?=.*[a-zA-Z])" +    //at least 1 lower/upper case letter
            "(?=\\S+$)" +        //no white spaces
            ".{7,13}" +           //at least 7
            "$");

    //비밀번호 조건: 필수 영숫자, 특수문자 8 ~ 15자리.
    public static final Pattern PW_REGEX
            = Pattern.compile("^"+
            "(?=.*[0-9])" +        //at least 1 digit
            "(?=.*[a-zA-Z])" +   //at least 1 lower/upper case letter
            "(?=.*[@#$%^&!?])" +   //at least 1 special character
            "(?=\\S+$)" +          //no white spaces
            ".{8,15}" +            //at least 8
            "$");



    StepView regStepView;
    CheckBox allChkBox, chkBox1, chkBox2;
    Boolean allChk, chk1, chk2 = false; //check 상태.
    Button step1NextBtn, step2NextBtn, step3NextBtn, stepEndNextBtn, diaAgreeBtn1, diaAgreeBtn2;
    TextInputLayout nameEdit, birthEdit, regIdEdit, regPwEdit, regPwConfirmEdit;
    ImageView stepClose, popUp1Close, popUp2Close;
    RelativeLayout step1, step2, step3, stepEnd, chkBox1PopUp, chkBox2PopUp;
    RadioGroup userTypeRadio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_step);

        regStepView = (StepView) findViewById(R.id.regStepView);
        allChkBox = (CheckBox) findViewById(R.id.allChkBox);
        chkBox1 = (CheckBox) findViewById(R.id.chkBox1);
        chkBox2 = (CheckBox) findViewById(R.id.chkBox2);

        stepClose = (ImageView) findViewById(R.id.stepClose);

        step1NextBtn = (Button) findViewById(R.id.step1NextBtn);
        step2NextBtn = (Button) findViewById(R.id.step2NextBtn);
        step3NextBtn = (Button) findViewById(R.id.step3NextBtn);
        stepEndNextBtn = (Button) findViewById(R.id.stepEndNextBtn);

        step1 = (RelativeLayout) findViewById(R.id.step1);
        step2 = (RelativeLayout) findViewById(R.id.step2);
        step3 = (RelativeLayout) findViewById(R.id.step3);

        stepEnd = (RelativeLayout) findViewById(R.id.stepEnd);

        chkBox1PopUp = (RelativeLayout) findViewById(R.id.chkBox1PopUp);
        chkBox2PopUp = (RelativeLayout) findViewById(R.id.chkBox2PopUp);

        nameEdit = (TextInputLayout) findViewById(R.id.nameEdit);
        birthEdit = (TextInputLayout) findViewById(R.id.birthEdit);
        regIdEdit = (TextInputLayout) findViewById(R.id.regIdEdit);
        regPwEdit = (TextInputLayout) findViewById(R.id.regPwEdit);
        regPwConfirmEdit = (TextInputLayout) findViewById(R.id.regPwConfirmEdit);

        userTypeRadio = (RadioGroup) findViewById(R.id.userTypeRadio);

        //step1이 보임.
        step1.setVisibility(View.VISIBLE);

        //stepView 설정
        regStepView.setStepsNumber(3); //3단계로 구성.
        regStepView.go(0, true);

        //step1NextBtn 비활성화
        step1NextBtn.setEnabled(false);


        //이벤트 리스너
        step1NextBtn.setOnClickListener(this);
        step2NextBtn.setOnClickListener(this);
        step3NextBtn.setOnClickListener(this);
        stepEndNextBtn.setOnClickListener(this);
        stepClose.setOnClickListener(this);
        chkBox1PopUp.setOnClickListener(this);
        chkBox2PopUp.setOnClickListener(this);

        allChkBox.setOnCheckedChangeListener(this);
        chkBox1.setOnCheckedChangeListener(this);
        chkBox2.setOnCheckedChangeListener(this);

        //라디오 버튼
        userTypeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String result;
                if(checkedId == R.id.ownerRadio){
                    ePos = "o";
                }else if(checkedId == R.id.employeeRadio){
                    ePos = "e";
                }
            }
        });



    }//onCreate() 끝

    //**************************** 만든 메소드 ****************************

    //이벤트 리스너
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.step1NextBtn: //step1(약관동의)에 있는 버튼.
                //step1NextBtn 활성화/비활성화에 따른 화면 이동.
                if(currentStep < regStepView.getStepCount() - 1){
                    currentStep++;
                    regStepView.go(currentStep, true);
                } else {
                    regStepView.done(true);
                }
                step1.setVisibility(View.GONE);
                step2.setVisibility(View.VISIBLE);
                break;


            case R.id.step2NextBtn:
                if(currentStep < regStepView.getStepCount() - 1){
                    currentStep++;
                    regStepView.go(currentStep, true);
                } else {
                    regStepView.done(true);
                }
                step2.setVisibility(View.GONE);
                step3.setVisibility(View.VISIBLE);


                break;

            case R.id.step3NextBtn:
                String usName = nameEdit.getEditText().getText().toString().trim(); //이름
                String usID = regIdEdit.getEditText().getText().toString().trim(); //아이디
                String usPw = regPwEdit.getEditText().getText().toString().trim(); //비밀번호

                if(currentStep < regStepView.getStepCount() - 1){
                    currentStep++;
                    regStepView.go(currentStep, true);
                }

                validateVal = validateRegEdit(); // 형식 및 빈칸이 없는지, 있는지 확인하고 값을 받음
                Boolean equalPw = equalPw();
                //비밀번호 일치 여부

                Log.i("회원가입 절차 - ","형식&빈칸: " + validateVal + "비밀번호 일치: " + equalPw);
                if (validateVal && equalPw) {
                    try {
                        String result  = new RegisterTask().execute(usID, usPw, usName, ePos).get();
                        Log.i("회원가입", result);
                        if(result.equals("hasId")) {
                            //Toast.makeText(RegStep.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            regIdEdit.setError("이미 존재하는 아이디입니다.");
                        } else if(result.equals("ok")) {
                            regStepView.done(true);
                            step3.setVisibility(View.GONE);
                            stepEnd.setVisibility(View.VISIBLE);
                            //Toast.makeText(RegStep.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                } //if

                break;


            case R.id.stepEndNextBtn:
                Intent loginIntent = new Intent(RegStep.this, SignInActivity.class);
                startActivity(loginIntent);
                break;


            case R.id.stepClose:
                /*Intent regStepIntent = new Intent(RegStep.this, SignInActivity.class);
                startActivity(regStepIntent);*/
                RegStep.this.finish();
                break;


            case R.id.chkBox1PopUp: //해당 약관에 대한 팝업1
                // custom dialog
                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.activity_chk_box1_dialog);
                dialog1.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                popUp1Close = (ImageView) dialog1.findViewById(R.id.popUp1Close);
                popUp1Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                //'동의'버튼을 누르면 check 상태로 바꿈.
                diaAgreeBtn1 = (Button) dialog1.findViewById(R.id.diaAgreeBtn1);
                diaAgreeBtn1.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chkBox1.setChecked(true);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();
                break;


            case R.id.chkBox2PopUp: //해당 약관에 대한 팝업2
                // custom dialog
                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.activity_chk_box2_dialog);
                dialog2.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                popUp2Close = (ImageView) dialog2.findViewById(R.id.popUp2Close);
                popUp2Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });

                //'동의'버튼을 누르면 check 상태로 바꿈.
                diaAgreeBtn2 = (Button) dialog2.findViewById(R.id.diaAgreeBtn2);
                diaAgreeBtn2.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chkBox2.setChecked(true);
                        dialog2.dismiss();
                    }
                });
                dialog2.show();
                break;

        }
    }



    //step1 - checkbox 모두 선택/해제
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.allChkBox:
                allChk = b;
                if(b){ //allChkBox가 check이면 (b = true)
                    if(!chkBox1.isChecked()) //chkBox1이 check가 안되어있다면
                        chkBox1.setChecked(b); //chkBox1을 check하라.
                    if(!chkBox2.isChecked())
                        chkBox2.setChecked(b);
                    step1NextBtn.setEnabled(true);
                    step1NextBtn.setBackgroundResource(R.drawable.neon_main_btn);

                }else{ //allChkBox가 check가 아니면 (b = false)
                    if(allChkBox.isPressed()){ //전체동의 체크박스를 직접 체크 했는지?
                        chkBox1.setChecked(b); //chkBox1을 check 해제하라.
                        chkBox2.setChecked(b);
                    }
                    step1NextBtn.setEnabled(false);
                    step1NextBtn.setBackgroundResource(R.drawable.not_active_btn);
                }
                break;


            case R.id.chkBox1:
                chk1= b;
                if(chkBox1.isChecked() && chkBox2.isChecked()){
                    allChkBox.setChecked(true);
                    step1NextBtn.setEnabled(true);
                    step1NextBtn.setBackgroundResource(R.drawable.neon_main_btn);
                }else if(chkBox1.isChecked() && chkBox2.isChecked()) {
                    step1NextBtn.setEnabled(true);
                    step1NextBtn.setBackgroundResource(R.drawable.neon_main_btn);
                }else{
                    allChkBox.setChecked(false);
                    step1NextBtn.setEnabled(false);
                    step1NextBtn.setBackgroundResource(R.drawable.not_active_btn);
                }
                break;


            case R.id.chkBox2:
                chk2= b;
                if(chkBox1.isChecked() && chkBox2.isChecked()){
                    allChkBox.setChecked(true);
                    step1NextBtn.setEnabled(true);
                    step1NextBtn.setBackgroundResource(R.drawable.neon_main_btn);
                }else if(chkBox1.isChecked() && chkBox2.isChecked()) {
                    step1NextBtn.setEnabled(true);
                    step1NextBtn.setBackgroundResource(R.drawable.neon_main_btn);
                }else{
                    allChkBox.setChecked(false);
                    step1NextBtn.setEnabled(false);
                    step1NextBtn.setBackgroundResource(R.drawable.not_active_btn);
                }
                break;
        }

    }


    //step 3 - 정규식
    private  boolean validateRegEdit() {
        String nameInput = nameEdit.getEditText().getText().toString().trim(); //이름
        String birthInput = birthEdit.getEditText().getText().toString().trim(); //생년월일
        String idInput = regIdEdit.getEditText().getText().toString().trim(); //아이디
        String pwInput = regPwEdit.getEditText().getText().toString().trim(); //비밀번호
        String pwConfirmInput = regPwConfirmEdit.getEditText().getText().toString().trim(); //비밀번호 확인

        //이름 유효성 검사
        if(nameInput.isEmpty()) {
            nameEdit.setError("이름을 입력해주세요.");
            nameEdit.requestFocus();
            return false;
        } else if(!NAME_REGEX.matcher(nameInput).matches()) {
            nameEdit.setError("한글로 입력해주세요.");
            nameEdit.requestFocus();
            return false;
        }

        //생년월일 유효성 검사
        if(birthInput.isEmpty()) {
            birthEdit.setError("생년월일을 입력해주세요.");
            birthEdit.requestFocus();
            return false;
        }

        //아이디 유효성 검사
        if(idInput.isEmpty()){
            regIdEdit.setError("아이디를 입력해주세요.");
            regIdEdit.requestFocus();
            return false;
        } else if(!ID_REGEX.matcher(idInput).matches()){
            regIdEdit.setError("영숫자를 이용한 7~13글자로 입력해주세요.");
            regIdEdit.requestFocus();
            return false;
        }

        //비밀번호 유효성 검사
        if(pwInput.isEmpty()){
            regPwEdit.setError("비밀번호를 입력해주세요.");
            regPwEdit.requestFocus();
            return false;
        } else if(!PW_REGEX.matcher(pwInput).matches()){ //정규식이 맞지 않을때
            regPwEdit.setError("영숫자, 특수문자를 이용한 8~15글자");
            regPwEdit.requestFocus();
            return false;
        }

        //비밀번호 확인 유효성 검사
        if(pwConfirmInput.isEmpty()){
            regPwConfirmEdit.setError("비밀번호 확인을 입력해주세요.");
            regPwConfirmEdit.requestFocus();
            return false;
        }
        return true;
    }


    private boolean equalPw() {
        String pwInput = regPwEdit.getEditText().getText().toString().trim(); //비밀번호
        String pwConfirmInput = regPwConfirmEdit.getEditText().getText().toString().trim(); //비밀번호 확인

        if (!pwInput.equals(pwConfirmInput)) {
            regPwConfirmEdit.setError("비밀번호가 일치하지 않습니다.");
            return false;
        }
        return true;
    }


    @SuppressLint("StaticFieldLeak")
    class RegisterTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/insertEmployee.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "usID="+strings[0]+"&usPw="+strings[1] + "&usName="+strings[2]+"&ePos="+strings[3];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }




}


