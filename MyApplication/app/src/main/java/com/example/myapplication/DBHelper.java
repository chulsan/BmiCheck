package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import static android.provider.BaseColumns._ID;
import static com.example.myapplication.Constants.TABLE_NAME;
import static com.example.myapplication.Constants.date;
import static com.example.myapplication.Constants.weight;
import static com.example.myapplication.Constants.BMI;
import static com.example.myapplication.Constants.Status;
public class DBHelper extends SQLiteOpenHelper {



    public DBHelper(@Nullable Context context) {
        super(context, "bmi.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                +_ID+"INTERGER PRIMARY KEY AUTOINCREMENT, "
                +date+"INTEGER, "
                +weight+"INTERGER, "
                +BMI+"INTERGER, "
                +Status+"TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
