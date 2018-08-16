package com.junaid.demotestmma.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.junaid.demotestmma.R;

public class SplashActivity extends AppCompatActivity {


    private TextView textView;
    private ImageView imageView;
    public static final int TIMES = 8*10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // find view id
        initView();

        // find Animation
        initViewAnimation();

        // goto home
        gotoHome();
    }



    private void initViewAnimation() {
        Animation myAnimation = AnimationUtils.loadAnimation(this,R.anim.mytransit);
        textView.startAnimation(myAnimation);
        imageView.startAnimation(myAnimation);
    }

    private void initView() {
        textView = findViewById(R.id.tvText1);
        imageView = findViewById(R.id.image1);
    }


    private void gotoHome() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
//                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                Intent intent = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(intent);
                finish();
            }
        },TIMES);

    }

}
