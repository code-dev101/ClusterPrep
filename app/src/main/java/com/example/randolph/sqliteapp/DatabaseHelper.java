package com.example.randolph.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Randolph on 1/22/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String STUDENT_DB = "Student.db";
    public static final String STUDENT_TBL = "student_table";
    public static final String SID = "SID";
    public static final String FNAME = "FNAME";
    public static final String LNAME = "LNAME";
    public static final String EMAIL = "EMAIL";
    public static final String PASS = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, STUDENT_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + STUDENT_TBL + " (" +
                "SID VARCHAR," +
                "FNAME VARCHAR," +
                "LNAME VARCHAR," +
                "EMAIL VARCHAR," +
                "PASSWORD VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+STUDENT_TBL);
        onCreate(db);
    }

    public boolean insertData(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.putAll(student_info(student));
        long result = db.insert(STUDENT_TBL,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+STUDENT_TBL,null);
        return res;
    }

    public ContentValues student_info(Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FNAME,student.getFirstName());
        contentValues.put(LNAME,student.getLastName());
        contentValues.put(EMAIL,student.getAddress());
        contentValues.put(PASS, student.getPassword());
        return contentValues;
    }

    public boolean updateData(Student student) {

        String sid = student.getStudentID();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SID, student.getStudentID());
        contentValues.putAll(student_info(student));
        db.update(STUDENT_TBL, contentValues, "SID = ?",new String[] { sid });
        return true;
    }

    public Integer deleteData (String sid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(STUDENT_TBL, "SID = ?",new String[] {sid});
    }
}
