package com.example.cafeinadmin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.shuhart.stepview.StepView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuRegister extends AppCompatActivity implements View.OnClickListener{

    private int currentStep = 0; //step
    StepView regStepView;
    private ImageView croppedImg;
    private String radioCategory, temperature, imgUri, imgName;

    ImageView stepClose;
    Uri uri;
    RelativeLayout step1, step2, step3, step4, step5;
    Button step1NextBtn, step2NextBtn, step3NextBtn, step4NextBtn, stepCompleteBtn;
    Button hashtagPlusBtn;
    Button addRowBtn;
    RadioGroup categoryRadio;
    EditText menuNameEdit, menuPriceEdit, menuExplainEdit;
    EditText kcal, fat, protein, natrium, saccharide , caffeine;
    CheckBox chkHot, chkIce;
    Context context;
    LinearLayout temperatureLinear, sizeLinear;
    TableLayout addTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_register);

        context = this;

        stepClose = (ImageView) findViewById(R.id.stepClose);
        croppedImg = (ImageView) findViewById(R.id.croppedImg);

        regStepView = (StepView) findViewById(R.id.regStepView);

        step1 = (RelativeLayout) findViewById(R.id.step1);
        step2 = (RelativeLayout) findViewById(R.id.step2);
        step3 = (RelativeLayout) findViewById(R.id.step3);
        step4 = (RelativeLayout) findViewById(R.id.step4);
        step5 = (RelativeLayout) findViewById(R.id.step5);

        step1NextBtn = (Button) findViewById(R.id.step1NextBtn);
        step2NextBtn = (Button) findViewById(R.id.step2NextBtn);
        step3NextBtn = (Button) findViewById(R.id.step3NextBtn);
        step4NextBtn = (Button) findViewById(R.id.step4NextBtn);
        stepCompleteBtn = (Button) findViewById(R.id.stepCompleteBtn);

        addRowBtn = (Button) findViewById(R.id.addRowBtn);

        categoryRadio = (RadioGroup) findViewById(R.id.categoryRadio);

        menuNameEdit = (EditText) findViewById(R.id.menuNameEdit);
        menuPriceEdit = (EditText) findViewById(R.id.menuPriceEdit);
        menuExplainEdit = (EditText) findViewById(R.id.menuExplainEdit);

        kcal = (EditText) findViewById(R.id.kcal);
        fat = (EditText) findViewById(R.id.fat);
        protein = (EditText) findViewById(R.id.protein);
        natrium = (EditText) findViewById(R.id.natrium);
        saccharide = (EditText) findViewById(R.id.saccharide);
        caffeine = (EditText) findViewById(R.id.caffeine);

        chkHot = (CheckBox) findViewById(R.id.chkHot);
        chkIce = (CheckBox) findViewById(R.id.chkIce);

        temperatureLinear = (LinearLayout) findViewById(R.id.temperatureLinear);
        sizeLinear = (LinearLayout) findViewById(R.id.sizeLinear);

        addTable = (TableLayout) findViewById(R.id.addTable);

        //step1이 보임.
        step1.setVisibility(View.VISIBLE);

        //stepView 설정
        regStepView.setStepsNumber(5); //6단계로 구성.
        regStepView.go(0, true);

        radioCategory = "c";

        categoryRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.coffeeRadio) {
                    //radioCategory = "coffee";
                    radioCategory = "c";

                } else if(checkedId == R.id.nonCoffeeRadio) {
                    //radioCategory = "nonCoffee";
                    radioCategory = "n";

                } else if(checkedId == R.id.bakeryRadio) {
                    //radioCategory = "bakery";
                    radioCategory = "b";
                    temperatureLinear.setVisibility(View.INVISIBLE);
                    sizeLinear.setVisibility(View.INVISIBLE);
                }
            }
        });


        if (chkHot.isChecked()) {
            temperature = "hot";
        } else if (chkIce.isChecked()) {
            temperature = "ice";
        } else if (chkHot.isChecked() || chkIce.isChecked()) {
            temperature = "both";
        }



        //이벤트 리스너
        stepClose.setOnClickListener(this);
        step1NextBtn.setOnClickListener(this);
        step2NextBtn.setOnClickListener(this);
        step3NextBtn.setOnClickListener(this);
        step4NextBtn.setOnClickListener(this);
        stepCompleteBtn.setOnClickListener(this);
        croppedImg.setOnClickListener(this);
        addRowBtn.setOnClickListener(this);



    } //onCreate() 끝



    //이벤트 리스너
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stepClose:
                MenuRegister.this.finish();

                break;
            case R.id.step1NextBtn:
                stepCount();
                step1.setVisibility(View.GONE);
                step2.setVisibility(View.VISIBLE);
                break;

            case R.id.step2NextBtn:
                if(checkMenuInfo1()) {
                    stepCount();
                    step2.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.step3NextBtn:
                if(checkMenuInfo2()) {
                    stepCount();
                    step3.setVisibility(View.GONE);
                    step4.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.step4NextBtn:
                stepCount();
                step4.setVisibility(View.GONE);
                step5.setVisibility(View.VISIBLE);


                break;

            case R.id.stepCompleteBtn:
                //Bitmap bitmap = ((BitmapDrawable) croppedImg.getDrawable()).getBitmap();
                //String convertImg = getStringFromBitmap(bitmap);
                String name = menuNameEdit.getText().toString();
                String price = menuPriceEdit.getText().toString();
                String explain = menuExplainEdit.getText().toString();
                //DoFileUpload("http://14.33.171.115:8080/CafeinProject/saveMenuImg.jsp", imgUri); //사진 전송
                try {
                    String json = sendMenuInfo();
                    Log.i("jsonMenu", json);
                    String returns = new MenuInfoTask().execute(name, price, explain, radioCategory, json).get();
                    Log.i("카테고리: ", ""+radioCategory);
                    if (returns.equals("ok")) {
                        Toast.makeText(this, "메뉴가 성공적으로 등록되었습니다!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;



            case R.id.croppedImg:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MenuRegister.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        openGallery();
                    } else {
                        ActivityCompat.requestPermissions(MenuRegister.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 401);
                    }
                } else {
                    openGallery();
                }

                break;


            case R.id.addRowBtn:
                final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                TableRow addTr = new TableRow(this); //row 추가
                EditText td1 = new EditText(this); //edit 추가
                EditText td2 = new EditText(this); //edit 추가
                EditText td3 = new EditText(this); //edit 추가

                td1.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                td2.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                td3.setBackgroundResource(R.drawable.table);

                td1.setGravity(Gravity.CENTER);
                td2.setGravity(Gravity.CENTER);
                td3.setGravity(Gravity.CENTER);

                TableRow.LayoutParams params = new TableRow.LayoutParams(width, TableRow.LayoutParams.MATCH_PARENT, 1f);

                td1.setLayoutParams(params);
                td2.setLayoutParams(params); //edit 추가
                td3.setLayoutParams(params); //edit 추가

                addTr.addView(td1); //tableRow에 edit 넣기.
                addTr.addView(td2); //tableRow에 edit 넣기.
                addTr.addView(td3); //tableRow에 edit 넣기.

                addTable.addView(addTr);

                break;
        }
    }

    // 사진과 관련된 퍼미션
    private boolean checkAndRequestPermissions() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 402);
            return false;
        }
        return true;
    }

    //갤러리
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 501);

    }

    //사진 crop 및 사진 등록
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 501 && resultCode == RESULT_OK && data != null) {
            Uri selectedURI = data.getData();
            CropImage.activity(selectedURI)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                croppedImg.setImageURI(resultUri);
                /*Log.i("사진", resultUri.toString());
                imgUri = getRealPathFromURI(resultUri);*/

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(MenuRegister.this, "Failed to get profile picture, Try Again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    //stepView와 관련된 메소드
    private void stepCount() {
        if(currentStep < regStepView.getStepCount() - 1){
            currentStep++;
            regStepView.go(currentStep, true);
        } else {
            regStepView.done(true);
        }
    }

    //step 2 - 공백 체크
    private  boolean checkMenuInfo1() {
        String nameInput = menuNameEdit.getText().toString(); //메뉴 이름
        String priceInput = menuPriceEdit.getText().toString(); //메뉴 가격
        String explainInput = menuExplainEdit.getText().toString(); //메뉴 설명

        if (nameInput.replace(" ", "").equals("")) {
            menuNameEdit.setError("메뉴 이름을 입력해주세요.");
            menuNameEdit.requestFocus();
            return false;
        }

        if (priceInput.replace(" ", "").equals("")) {
            menuPriceEdit.setError("메뉴 가격을 입력해주세요.");
            menuPriceEdit.requestFocus();
            return false;
        }

        if (explainInput.replace(" ", "").equals("")) {
            menuExplainEdit.setError("메뉴 설명을 입력해주세요.");
            menuExplainEdit.requestFocus();
            return false;
        }
        return true;
    }

    private  boolean checkMenuInfo2() {
        //step 3
        String kcalInput = kcal.getText().toString();
        String fatInput = fat.getText().toString();
        String proteinInput = protein.getText().toString();
        String natriumInput = natrium.getText().toString();
        String saccharideInput = saccharide.getText().toString();
        String caffeineInput = caffeine.getText().toString();

        if (!(chkHot.isChecked() || chkIce.isChecked())) {
            Toast.makeText(this, "온도를 체크해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (kcalInput.isEmpty()) {
            kcal.setError("칼로리를 입력해주세요.");
            kcal.requestFocus();
            return false;
        }

        if (fatInput.isEmpty()) {
            fat.setError("포화 지방을 입력해주세요.");
            fat.requestFocus();
            return false;
        }

        if (proteinInput.isEmpty()) {
            protein.setError("단백질을 입력해주세요.");
            protein.requestFocus();
            return false;
        }

        if (natriumInput.isEmpty()) {
            natrium.setError("나트륨을 입력해주세요.");
            natrium.requestFocus();
            return false;
        }

        if (saccharideInput.isEmpty()) {
            saccharide.setError("당류를 입력해주세요.");
            saccharide.requestFocus();
            return false;
        }

        if (caffeineInput.isEmpty()) {
            caffeine.setError("카페인을 입력해주세요.");
            caffeine.requestFocus();
            return false;
        }

        return true;
    }

    /* private String getStringFromBitmap(Bitmap bitmapPicture) {
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }*/

    //json 파일을 서버로 보내는 메소드
    private String sendMenuInfo() {
        Bitmap bitmap = ((BitmapDrawable) croppedImg.getDrawable()).getBitmap();
        //String convertImg = getStringFromBitmap(bitmap);

        String kcalInput = kcal.getText().toString();
        String fatInput = fat.getText().toString();
        String proteinInput = protein.getText().toString();
        String natriumInput = natrium.getText().toString();
        String saccharideInput = saccharide.getText().toString();
        String caffeineInput = caffeine.getText().toString();

        JSONObject jsonObject = new JSONObject();
        JSONArray menuArray = new JSONArray();
        JSONObject infoObject = new JSONObject();

        try {
            infoObject.put("name", menuNameEdit.getText().toString());
            infoObject.put("price", menuPriceEdit.getText().toString());
            infoObject.put("explain", menuExplainEdit.getText().toString());
            //infoObject.put("img", convertImg);
            infoObject.put("type", radioCategory);
            infoObject.put("temperature", temperature);
            infoObject.put("kcal", kcalInput);
            infoObject.put("fat", fatInput);
            infoObject.put("protein", proteinInput);
            infoObject.put("natrium", natriumInput);
            infoObject.put("saccharide", saccharideInput);
            infoObject.put("caffeine", caffeineInput);
            menuArray.put(infoObject);
            jsonObject.put("menu", menuArray);

            Log.i("Test:",infoObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return menuArray.toString();
    }

    //이미지를 서버에 보내기
    public void DoFileUpload(String apiUrl, String absolutePath) {

        HttpFileUpload(apiUrl, "", absolutePath);

    }


    public void HttpFileUpload(String urlString, String params, String fileName) {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            File sourceFile = new File(fileName);
            DataOutputStream dos;

            if (!sourceFile.isFile()) {
                Log.e("uploadFile", "Source File not exist :" + fileName);
            } else {
                FileInputStream mFileInputStream = new FileInputStream(sourceFile);
                URL connectUrl = new URL(urlString);
                // open connection
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                // write data
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);



                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);



                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                mFileInputStream.close();
                dos.flush(); // finish upload...

                if (conn.getResponseCode() == 200) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                }

                mFileInputStream.close();
                dos.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};

        //절대 경로 획득.
        Cursor cursor = this.getContentResolver().query(contentUri, proj, null, null, null);
        /*if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }*/

        cursor.moveToNext();
        String absolutePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

        imgName = res.substring(res.lastIndexOf("/") + 1);

        cursor.close();
        return res;

    }

    static class MenuInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/insertMenu.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                sendMsg = "name="+strings[0]+"&price="+strings[1] + "&explain="+strings[2] + "&type="+strings[3] +
                        "&json="+strings[4];
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
