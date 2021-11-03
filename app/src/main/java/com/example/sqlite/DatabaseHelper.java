package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="records.db";
    public static final String TABLE_1="NEWBIE";
    public static final String TABLE_2="PUPIL";
    public static final String TABLE_3="SPECIALIST";
    public static final String TABLE_4="EXPERT";
    public static final String COL_1="DATE";
    public static final String COL_2="NAME";
    public static final String COL_3="WON";
    public static final String COL_4="LOSE";
    public static final String COL_5="TIE";

    // table for two players

//    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_1+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,WON INTEGER, LOSE INTEGER,TIE INTEGER,DATE TEXT);");
        sqLiteDatabase.execSQL("create table "+TABLE_2+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,WON INTEGER, LOSE INTEGER,TIE INTEGER,DATE TEXT);");
        sqLiteDatabase.execSQL("create table "+TABLE_3+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,WON INTEGER, LOSE INTEGER,TIE INTEGER,DATE TEXT);");
        sqLiteDatabase.execSQL("create table "+TABLE_4+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,WON INTEGER, LOSE INTEGER,TIE INTEGER,DATE TEXT);");
        sqLiteDatabase.execSQL("create table DUO (ID INTEGER PRIMARY KEY AUTOINCREMENT,A TEXT,B TEXT,SCOREA INTEGER,SCOREB INTEGER,TIE INTEGER,DATE TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_1+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_2+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_3+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_4+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DUO;");
        onCreate(sqLiteDatabase);
    }

    public int insertData(String table_name,String name,int won,int lose,int tie,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,date);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,won);
        contentValues.put(COL_4,lose);
        contentValues.put(COL_5,tie);
        long result=db.insert(table_name,null,contentValues);
        return (int)result;
    }

    public int insertDuoData(String playerA,String playerB,int scoreA,int scoreB,int tie,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("A",playerA);
        contentValues.put("B",playerB);
        contentValues.put("SCOREA",scoreA);
        contentValues.put("SCOREB",scoreB);
        contentValues.put("TIE",tie);
        contentValues.put(COL_1,date);
        long result=db.insert("DUO",null,contentValues);
        return (int)result;
    }

    public Cursor getAllData(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name+" ORDER BY ID DESC;",null);
        return cursor;
    }

    public Cursor getSortedData(String table_name,int sort){
        // sort=1 -> sort by date
        // sort=2 -> sort by won
        // sort=3 -> sort by lose
        // sort=4 -> sort by tie
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        if(sort==1)
            cursor = db.rawQuery("SELECT * FROM "+table_name+" ORDER BY ID DESC;",null);
        else if(sort==2)
            cursor = db.rawQuery("SELECT * FROM "+table_name+" ORDER BY WON DESC;",null);
        else if(sort==3)
            cursor = db.rawQuery("SELECT * FROM "+table_name+" ORDER BY LOSE DESC;",null);
        else
            cursor = db.rawQuery("SELECT * FROM "+table_name+" ORDER BY TIE DESC;",null);
        return cursor;
    }

    public boolean updateData(String table_name,String id,int won,int lose,int tie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,won);
        contentValues.put(COL_4,lose);
        contentValues.put(COL_5,tie);
        int updated=db.update(table_name,contentValues,"ID = ?",new String[] {id});
        if(updated==-1)
            return false;
        else
            return true;
    }
    public boolean updateDuoData(String id,int scoreA,int scoreB,int tie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SCOREA",scoreA);
        contentValues.put("SCOREB",scoreB);
        contentValues.put("TIE",tie);
        int updated=db.update("DUO",contentValues,"ID = ?",new String[] {id});
        if(updated==-1)
            return false;
        else
            return true;
    }

    public Integer deleteData(String table_name,String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name,"ID = ?",new String[] {id});
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM NEWBIE");
        db.execSQL("DELETE FROM PUPIL");
        db.execSQL("DELETE FROM SPECIALIST");
        db.execSQL("DELETE FROM EXPERT");
        db.execSQL("DELETE FROM DUO");
    }

}
