package com.khairul.gudrasto.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.khairul.gudrasto.Model.UserModels;

public class DatabaseHelperUser extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "id";
    public static final String COL_2 = "email";
    public static final String COL_3 = "name";
    public static final String COL_4 = "password";
    public static final String COL_5 = "Best_score_Story";
    public static final String COL_6 = "Best_score_Guess";
    public static final String COL_7 = "week_score_Story"; //ini score akhir dari soal dan cerita
    public static final String COL_8 = "week_score_Guess"; //ini score terakhir dari tebak suara

    public DatabaseHelperUser(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "("+
                COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2+" TEXT, "+ COL_3+" TEXT, "+COL_4+" TEXT, " +
                COL_5+" TEXT, "+ COL_6+" TEXT, "+COL_7+" TEXT, " +
                COL_8+" TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String email, String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, password);
        contentValues.put(COL_5, "0");
        contentValues.put(COL_6, "0");
        contentValues.put(COL_7, "0");
        contentValues.put(COL_8, "0");
        long res = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return 1;
    }

    public UserModels checkUser(String email, String password){
        String[] columns = { COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.moveToFirst();
//        Log.d("BLALBA", "ini id = " + cursor.getString(0) + " Nama : " + cursor.getString(1) + " Pwd : " + cursor.getString(2));

        UserModels user = null;
        if(count>0) {
            user = new UserModels(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6),
                    cursor.getString(7));
        }
        cursor.close();
        db.close();

        return user;

    }

    public int updateBestPointStory(String value, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_5, value);

        // updating row
        return db.update(TABLE_NAME, values, COL_1 + " = ?",
                new String[] { String.valueOf(id) });
    }

    public  int updateBestPointGuess(String value, String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_6, value);

        // updating row
        return db.update(TABLE_NAME, values, COL_1 + " = ?",
                new String[] { String.valueOf(id) });
    }

    public  int updateLastPointStory(String value, String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_7, value);

        // updating row
        return db.update(TABLE_NAME, values, COL_1 + " = ?",
                new String[] { String.valueOf(id) });
    }

    public  int updateLastPointGuess(String value, String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_8, value);

        // updating row
        return db.update(TABLE_NAME, values, COL_1 + " = ?",
                new String[] { String.valueOf(id) });
    }


}
