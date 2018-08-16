package com.junaid.demotestmma.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.junaid.demotestmma.R;
import com.junaid.demotestmma.db.DatabaseHandler;
import com.junaid.demotestmma.model.UserModal;

public class RegistrationActivity extends AppCompatActivity {

    public static final String TAG = "RegistrationActivity";
    private ImageView imageView;
    private Button btnRegister;
    private TextView tvLogin;
    private TextInputEditText edtUserName,edtPassword, edtEmail,edtMobile;
    private RadioGroup genderRadioGroup;
    private RadioButton rbGender;
    private String username,password,email,photo, mobile,gender;
    private DatabaseHandler dbHandler;
    private  UserModal userModal;
    private long rowId;
    boolean checkId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // find the view's id
        initView();

        // set the clickListener
        clickListener();

        // this method call in Database class method
        dbHandler = new DatabaseHandler(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // get and set picture
        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            byte[] arrByte = extras.getByteArray("picture");
            photo = extras.getString("picture2");

            Bitmap bmp = BitmapFactory.decodeByteArray(arrByte, 0, arrByte.length);
//        imageView.setImageBitmap(bmp);
            Log.d(TAG,"bmp arrByte--"+arrByte);

            imageView.setImageDrawable(Drawable.createFromPath(photo)); // set img
            Log.d(TAG,"bmp getPhotoPath--"+photo);
        }

    }

    private void clickListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectid = genderRadioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                rbGender = (RadioButton) findViewById(selectid);
                gender = rbGender.getText().toString();
                Log.d(TAG,"gender--"+gender);

                dataSubmit();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void dataSubmit() {

        //username,password,email,photo, mobile,gender;
        username = edtUserName.getText().toString();
        password = edtPassword.getText().toString();
        email = edtEmail.getText().toString();
        mobile = edtMobile.getText().toString();


        // check validation of each field
        if(validate()) {

            // created the class Objects
             userModal = new UserModal();
             userModal.setUserName(username);
             userModal.setPassword(password);
             userModal.setEmailId(email);
             userModal.setMobileNo(mobile);
             userModal.setGender(gender);
             userModal.setPhotoPath(photo);

            // set the value at the Field

            // this method call in Database class method
            rowId = dbHandler.addUser(userModal);
            if (rowId>0) {
                Log.d(TAG,"rowId--"+rowId);
                Log.d(TAG,"checkId--"+checkId);

                Toast.makeText(this, "Register Successfully" +"-"+ rowId, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                intent.putExtra("dbId",rowId);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Register Fail" , Toast.LENGTH_SHORT).show();
            }

        } else{
            Toast.makeText(this,"Some Error occurs",Toast.LENGTH_SHORT).show();
        }

    }

    private  boolean validate() {
        boolean valid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(edtUserName.getText().toString().trim().length() == 0){
            valid = false;
            edtUserName.requestFocus();
            edtUserName.setError("UserName cannot be empty");
        }

        if(edtPassword.getText().toString().trim().length() == 0){
            valid = false;
            edtPassword.setError("Password cannot be empty");
        }


        if(edtEmail.getText().toString().trim().length() == 0){
            valid = false;
            edtEmail.setError("EmailId cannot be empty");
        }
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
            valid = true;
        } else{
            edtEmail.requestFocus();
            edtEmail.setError("Invalid Email Address");
            valid = false;
        }


        // editMobile
        if(edtMobile.getText().toString().length() == 12
                && edtMobile.getText().toString().startsWith("4")
                ||edtMobile.getText().toString().startsWith("5")
                ||edtMobile.getText().toString().startsWith("6")
                ||edtMobile.getText().toString().startsWith("7")
                || edtMobile.getText().toString().startsWith("8")
                || edtMobile.getText().toString().startsWith("9")
                ){
            valid = true;

        } else{
            valid = false;
            edtMobile.requestFocus();
            edtMobile.setError("Not Valid Mobile Number");
        }
        if(edtMobile.getText().toString().trim().length() == 0){
            valid = false;
            edtMobile.setError("Mobile No cannot be empty");
        }


            return valid;
    }


    private void initView() {
        imageView = findViewById(R.id.profile_image);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        genderRadioGroup = findViewById(R.id.sexRadioGroup);
//        maleRadioButton = findViewById(R.id.maleRadioButton);
//        femaleRadioButton = findViewById(R.id.femaleRadioButton);

    }


}

