package com.junaid.demotestmma;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.junaid.demotestmma.activity.LoginActivity;
import com.junaid.demotestmma.db.DatabaseHandler;
import com.junaid.demotestmma.model.UserModal;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private TextView userNameView,fullNameView,emailIdView,passwordView,address1View,address2View,mobileNoView,tvStateCode;
    private String loginUserName;
    private ImageView imageView;
    private Button logOutButton;
//    private Session session;
    private UserModal uModel;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the view's id
        initView();

        // set the clickListener
        clickListener();

        // this method call in Database class method
        dbHandler = new DatabaseHandler(this);

        // for user Logout
//        session = new Session(this);
//        if(!session.loggedIn()){
//            logout();
//        }
        displayUserDetails();


    }

    private void logout() {
//        session.setLoggedIn(false,null);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    private void clickListener() {

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    private void displayUserDetails() {
        // get Uername
        Bundle bundle = getIntent().getExtras();
        loginUserName = bundle.getString("loginUserName");

        // set the information
        uModel = new UserModal();
//        userModal = handler.getLoginUserDetails(loginUserName,this);
        uModel = dbHandler.getLoginUserDetails(loginUserName);

        userNameView.setText(uModel.getUserName());
        fullNameView.setText(uModel.getFullName());
        emailIdView.setText(uModel.getEmailId());
        passwordView.setText(uModel.getPassword());
        mobileNoView.setText(uModel.getMobileNo());
        imageView.setImageDrawable(Drawable.createFromPath(uModel.getPhotoPath()));
    }



    private void initView() {
        userNameView =  findViewById(R.id.userNameView);
       // fullNameView =  findViewById(R.id.fullNameView);
        emailIdView =  findViewById(R.id.emailIdView);
        passwordView =  findViewById(R.id.passwordView);
        mobileNoView =  findViewById(R.id.mobileNoView);
        imageView =  findViewById(R.id.imageView);
        logOutButton =  findViewById(R.id.logOutButton);

    }


}
