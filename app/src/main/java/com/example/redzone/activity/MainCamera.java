package com.example.redzone.activity;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.redzone.R;
import com.example.redzone.model.PostModel;
import com.example.redzone.networkAPI.CameraApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainCamera extends  AppCompatActivity {

    Button bt_take_image;
    ImageView imageView;
    private Bitmap bitmap;
    private int IMG_REQUEST = 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        take_an_image();
    }

    public void take_an_image() {
        setContentView(R.layout.camera_main);

        //Assign Variable
        imageView = findViewById(R.id.imageview);
        bt_take_image = findViewById(R.id.bt_take_image);

        //Request For Camera permission 카메라 허가 요청
        if (ContextCompat.checkSelfPermission(MainCamera.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainCamera.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

//        Uri realUri = null; //원본 이미지 저정할 변수

        bt_take_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open camera 카메라 열기
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                createImageUri(newFileName(),"image/jpg");

                startActivityForResult(intent, 100);
            }
        });
    }



//    Uri createImageUri(String filename, String minetype){ //원본 이미지를 저장할 Uri를 Mediastore에 생성하는 메서드
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
//        values.put(MediaStore.Images.Media.MIME_TYPE, minetype);
//
//        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//    }
//
//    String nameFile(){ //파일 이름을 생성하는 메서드
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        String filename = sdf.format(System.currentTimeMillis()); //흠 이게 맞나 변수....
//        return "${filename}.jpg";
//    }
//
////    @NotNull
//    public final String newFileName() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddd_HHmmss");
//        String filename = sdf.format(System.currentTimeMillis());
//        return filename + ".jpg";
//    }
//
//    Bitmap loadBitmap(Uri photo) {
//        Bitmap image = null;
//        try {
//            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1){
//                ImageDecoder.createSource(getContentResolver(), photoUri);
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        return image;
//    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);

            upload_an_image(captureImage);
        }
    }

    public void upload_an_image(Bitmap captureImage) {
        new AlertDialog.Builder(MainCamera.this)
                .setMessage("촬영된 이미지를 서버로 전송합니다. \n전송하시겠습니까?")
                .setPositiveButton("보내기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show();
                        uploadphoto(captureImage);
                        // gps 합쳐서 전송
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void uploadphoto(Bitmap captureImage) {

        /////////////////////////////////////////
        Intent splashintent = getIntent();
        Integer id = splashintent.getIntExtra("id", -1);
        float lat = splashintent.getFloatExtra("lat", (float) -1.0);
        float lng = splashintent.getFloatExtra("lng", (float) -1.0);

        System.out.print("Camera got ");
        System.out.println(id);

        System.out.print("위도: ");

        System.out.print(lat);
        System.out.print(", 경도: ");
        System.out.println(lng);
        /////////////////////////////////////////

        File imageFile = new File(saveBitmapToJpg(captureImage, "iamfromAndroidStudio!!"));
        System.out.println("이미지 경로:" + imageFile.toString());

        Retrofit retrofit = new Retrofit.Builder().baseUrl(CameraApi.DJANGO_SITE).addConverterFactory(GsonConverterFactory.create()).build();
        CameraApi api = retrofit.create(CameraApi.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("model_pic", imageFile.getName(), requestBody);

        RequestBody idBody = RequestBody.create(MediaType.parse("text/palin"), String.valueOf(id));
        RequestBody latBody = RequestBody.create(MediaType.parse("text/palin"), String.valueOf(lat));
        RequestBody lngBody = RequestBody.create(MediaType.parse("text/palin"), String.valueOf(lng));

        HashMap<String, RequestBody> requestMap = new HashMap<>();
        requestMap.put("userid", idBody);
        requestMap.put("lat", latBody);
        requestMap.put("lng", lngBody);

        Call<RequestBody> call = api.uploadImage(multipartBody, requestMap);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.d("good", "good");
                System.out.println(requestBody);
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d("fail", ""+t.getMessage());

            }
        });
    }

    public String saveBitmapToJpg(Bitmap bitmap, String name) {
        File storage = getCacheDir();
        String fileName = name + ".jpg";
        File imgFile = new File(storage, fileName);

        try {

            imgFile.createNewFile();
            FileOutputStream out = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (FileNotFoundException e) {
            Log.e("saveBitmapToJpg", "FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            Log.e("saveBitmapToJpg", "IOException: " + e.getMessage());
        }
        Log.d("imgPath", getCacheDir() + "/" + fileName);
        return getCacheDir() + "/" + fileName;

    }
}