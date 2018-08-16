package com.junaid.demotestmma.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.junaid.demotestmma.model.UserModal;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Junaid Ahmad.
 * email id junaidhamdard11@gmail.com
 */

public class DatabaseHandler extends SQLiteOpenHelper {
     // Database Info - Database Name && Database Version
     public static final String TAG = "DatabaseHandler";
    public static final String DATABASE_NAME = "Mma.db";
    public static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/com.infysystem.aro.puchsu/databases/";

    // Table Names
    private static final String TABLE_USER ="User";


    //Column Names for User Table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_userName = "userName";
    public static final String COLUMN_password = "password";
    public static final String COLUMN_fullName = "fullName";
    public static final String COLUMN_emailId = "emailId";
    public static final String COLUMN_mobileNo = "mobileNo";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_Photo = "photo";
    public static final String COLUMN_Address_FULL = "address";
    public static final String COLUMN_Address1 = "address1";
    public static final String COLUMN_CentreName = "centreName";
    public static final String COLUMN_city = "city";
    public static final String COLUMN_state = "state";
    public static final String COLUMN_StateCode = "sCode";
    public static final String COLUMN_country = "country";
    public static final String COLUMN_pinCode = "pinCode";


    //################---  TABLES CREATION - QUERIES -- ####################

    //User Table
    private static final String  QUERY_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_userName + " TEXT," +
            COLUMN_fullName + " TEXT," +
            COLUMN_emailId + " TEXT," +
            COLUMN_password + " TEXT," +
            COLUMN_mobileNo + " TEXT," +
            COLUMN_GENDER + " TEXT," +
            COLUMN_Photo + " TEXT"
            + ")";


    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG,"--constructor");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_TABLE_USER);
        Log.d(TAG,"--onCreate");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
        Log.d(TAG,"--onUpgrade");

    }


    // Add User
    public long addUser(UserModal user){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        //Inserting values
        values.put(COLUMN_userName,user.getUserName());
        values.put(COLUMN_password,user.getPassword());
        values.put(COLUMN_emailId,user.getEmailId());
        values.put(COLUMN_mobileNo,user.getMobileNo());
        values.put(COLUMN_GENDER,user.getGender());
        values.put(COLUMN_Photo, user.getPhotoPath());
        Log.d(TAG, "COLUMN_Photo-"+user.getPhotoPath());

        // 3. insert row into table
        long id = db.insert(TABLE_USER,null,values);
        Log.d(TAG,"--addUser");
         // 4. close
        db.close();

        return id;
    }

    // update the Table rows-
    public long updateUser(long id, UserModal ptModel){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(COLUMN_Photo,ptModel.getPhotoPath());


        // 3. Update Table row into Table
        long tmp = db.update(TABLE_USER, values, "_id="+id, null);
        Log.d(TAG,"--updateUser");
        // 4. close
        db.close();

        return tmp;
    }



    // get the Data for Login from User Table
    public String getLoginUser(UserModal user){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_userName + " FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery,null);
        String userName = null;
        if(cursor.moveToFirst()){
            do{
                userName = cursor.getString(cursor.getColumnIndex(COLUMN_userName));
                if(user.getUserName().equals(userName))
                    break;

            } while(cursor.moveToNext());
        }
        Log.d(TAG,"--getLoginUser Name :"+userName);
        return userName;
    }



    // get the Data for Display from User Table
    public UserModal getLoginUserDetails(String loginUserName){
        // read data from DB
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

        Cursor cursor = db.rawQuery(selectQuery,null);
        String userName = null;
        // create User Object
        UserModal uModel = new UserModal();

        if(cursor.moveToFirst()){
            do{
                userName = cursor.getString(cursor.getColumnIndex(COLUMN_userName));
                if(loginUserName.equals(userName)){
                    uModel.setUserName(loginUserName);
                    uModel.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_password)));
                    uModel.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_emailId)));
                    uModel.setMobileNo(cursor.getString(cursor.getColumnIndex(COLUMN_mobileNo)));
                    uModel.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                    uModel.setPhotoPath(cursor.getString(cursor.getColumnIndex(COLUMN_Photo)));

                    Log.d(TAG,""+cursor.getString(cursor.getColumnIndex(COLUMN_Photo)));
                    Log.d(TAG,"--getLoginUserDetails Name :"+userName);
                }
            } while(cursor.moveToNext());
        }

        return uModel;
    }


    // get the Data in List for Displaying
    public List<UserModal> getAllUser() {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();

        String query ="SELECT * FROM "+TABLE_USER   + " ORDER BY " + COLUMN_ID + " DESC";
        // 3. query perform
        Cursor cursor = db.rawQuery(query,null);

        // looping through all rows and adding to list
        List<UserModal> petrolTestModelList = new ArrayList<>();

        if(cursor!=null && cursor.moveToFirst()) {
            do{
                UserModal uModel = new UserModal();

                uModel.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                uModel.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_password)));
                uModel.setEmailId(cursor.getString(cursor.getColumnIndex(COLUMN_emailId)));
                uModel.setMobileNo(cursor.getString(cursor.getColumnIndex(COLUMN_mobileNo)));
                uModel.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                uModel.setPhotoPath(cursor.getString(cursor.getColumnIndex(COLUMN_Photo)));
                uModel.setPhotoPath(cursor.getString(cursor.getColumnIndex(COLUMN_Photo)));
                Log.d(TAG,""+cursor.getString(cursor.getColumnIndex(COLUMN_Photo)));

                // adding to TABLE_PETROL_TEST list
                petrolTestModelList.add(uModel);
                Log.d(TAG,"--getAllUser ");
            }while(cursor.moveToNext());
        }
           return petrolTestModelList;

    }



    // Delete the Data - Petrol Vehicle test
    public void deleteUser(long delId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_USER, COLUMN_ID +"="+delId,null);
    }






}
