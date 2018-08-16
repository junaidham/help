package com.junaid.demotestmma.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.junaid.demotestmma.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.ItemAction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    // variables  or fields initialization
    private TextView tvImageAdd;
    private Button btnCamera,btnGallery,btnSubmit;
    private ImageView imageView, imageView2;
    private Bitmap imageBitmap;
    public static final String TAG = "CameraActivity";
    private String imgAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // find the view's id
        initView();

        // set the clickListener
        clickListener();
    }

    private void clickListener() {
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    private void initView() {
        tvImageAdd = findViewById(R.id.tvImageAdd);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        btnSubmit = findViewById(R.id.btnSubmit);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

    }

    @Override
    public void onClick(View view) {

        if (view == btnCamera){
            selectCamera();
        }
        if (view == btnGallery){
            selectGallery();
        }
        if (view == btnSubmit){
           submitData();
        }
    }

    private void submitData() {

        Intent intent = new Intent(CameraActivity.this, RegistrationActivity.class);
//        intent.putExtra("imageBitmap",imageBitmap);

        Drawable drawable=imageView.getDrawable();
        Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] byteArray = baos.toByteArray();
        intent.putExtra("picture", byteArray);
        intent.putExtra("picture2", tvImageAdd.getText().toString() );

        Log.d(TAG,"Image put--"+byteArray);
        startActivity(intent);
        finish();
    }

    private void selectCamera() {
        Album.camera(this) // Camera function.
                .image() // Take Picture.
                //.filePath(String.valueOf(new File(Environment.getExternalStorageDirectory() + "/Junaid/Camera/"))) // File save path, not required.
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {

                        tvImageAdd.setText(result);

                        Album.getAlbumConfig()
                                .getAlbumLoader()
                                .load(imageView, result);

                        imgAddress =result;
                        Log.d(TAG,"Image--"+result);
                        File file = new File(result);
                        imageBitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                        // compress
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        // set bitmap
                        imageView.setImageBitmap(imageBitmap);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(getApplicationContext(), R.string.canceled, Toast.LENGTH_LONG).show();
                    }
                })
                .start();

    }



    private void selectGallery() {

        // Preview path:
        Album.gallery(this)
                .itemClick(new ItemAction<String>() {
                    @Override
                    public void onAction(Context context, String item) {
                    }
                })
                .itemLongClick(new ItemAction<String>() {
                    @Override
                    public void onAction(Context context, String item) {
                    }
                })
                .start();

    }
}
