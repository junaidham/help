package com.junaid.demotestmma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.junaid.demotestmma.MainActivity;
import com.junaid.demotestmma.R;
import com.junaid.demotestmma.db.DatabaseHandler;
import com.junaid.demotestmma.model.UserModal;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtUserName,edtPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private String username,password;
    public static final String TAG = "LoginActivity";
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        // find the view's id
        initView();

        // set the clickListener
        clickListener();

        // this method call in Database class method
        dbHandler = new DatabaseHandler(this);


    }

    private void clickListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginUser();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),CameraActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void getLoginUser() {
        //username,password,email,photo, mobile,gender;
        username = edtUserName.getText().toString();
        password = edtPassword.getText().toString();

        // check validation of each field
        if(validate()) {
            // created the class Objects
            UserModal user = new UserModal();

            // set the value at the Field
            user.setUserName(username);
            user.setPassword(password);

            String dbUserName = null;
            dbUserName = dbHandler.getLoginUser(user);
            // userId --> dbUserName
            if (username.equals(dbUserName)) {
                Log.d(TAG,"username--"+username +", "+password);

//                session.setLoggedIn(true,dbUserName);
                Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getBaseContext(),MainActivity.class);
                i.putExtra("loginUserName",dbUserName);
                startActivity(i);

            }
        }
    }

    private  boolean validate() {
        boolean valid = true;

        if(edtUserName.getText().toString().trim().length() == 0){
            valid = false;
            edtUserName.requestFocus();
            edtUserName.setError("UserName cannot be empty");
        }

        if(edtPassword.getText().toString().trim().length() == 0){
            valid = false;
            edtPassword.setError("Password cannot be empty");
        }


        return valid;
    }


    private void initView() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

    }

}
