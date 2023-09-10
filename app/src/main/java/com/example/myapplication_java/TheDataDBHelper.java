package com.example.myapplication_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TheDataDBHelper extends SQLiteOpenHelper {

    private static final String TAG="TheDataDBhelper";
    private static final String DB_NAME="data.db";
    private static final int DB_VERSION=1;
    private static TheDataDBHelper theDataDBHelper = null;
    private SQLiteDatabase theDB=null;
    private static final String TABLE_NAME="data_info";

    private TheDataDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    private TheDataDBHelper(Context context,int version){
        super(context,DB_NAME,null,version);
    }

    public static TheDataDBHelper getInstance(Context context,int version){
        if (version>0 &&theDataDBHelper==null){
            theDataDBHelper=new TheDataDBHelper(context,version);
        }
        else if(theDataDBHelper==null){
            theDataDBHelper=new TheDataDBHelper(context);
        }
        return theDataDBHelper;
    }

    public SQLiteDatabase openWriteLink(){
        if (theDB==null||theDB.isOpen()!=true){
            theDB=theDataDBHelper.getWritableDatabase();
        }
        return theDB;
    }

    public SQLiteDatabase openReadLink(){
        if (theDB==null||theDB.isOpen()!=true){
            theDB=theDataDBHelper.getReadableDatabase();
        }
        return theDB;
    }

    public void closeLink(){
        if (theDB!=null&&theDB.isOpen()==true){
            theDB.close();
            theDB=null;
        }
    }

    public String getDbName(){
        if (theDataDBHelper!=null){
            return theDataDBHelper.getDatabaseName();
        }
        else
            return DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"onCreate");
//        String drop_sql="DROP  TABLE IF EXISTS " + TABLE_NAME + ";";
//        db.execSQL(drop_sql);
//        System.out.println("------");
        String create_sql="CREATE TABLE IF NOT EXISTS "+TABLE_NAME + "("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "nowScore VARCHAR NOT NULL,"+ "topScore VARCHAR NOT NULL," +"excel VARCHAR NOT NULL,"+"putCount Int,"+
                "userId VARCHAR,"+ "userName VARCHAR"+",remainTime VARCHAR);";
        db.execSQL(create_sql);
        System.out.println("-------------------------------");
        if (db.rawQuery("select * from data_info",null).getCount()==0){
            System.out.println("11111111111111115555555555");
            ContentValues cv=new ContentValues();
            cv.put("nowScore","0");
            cv.put("topScore","0");
            cv.put("excel","2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0");
            cv.put("putCount",0);
            cv.put("userId","first");
            cv.put("userName","sssssss");
            cv.put("remainTime","5");
            db.insert(TABLE_NAME,"",cv);
            System.out.println("aaaaaaa");
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(String nowScore,String topScore,String excel,int put_count,String user_id,String user_name,String remainTime){
        ContentValues cv=new ContentValues();
        cv.put("nowScore",nowScore);
        cv.put("topScore",topScore);
        cv.put("excel",excel);
        cv.put("putCount",put_count);
        cv.put("userId",user_id);
        cv.put("userName",user_name);
        cv.put("remainTime",remainTime);
        long count = theDB.insert(TABLE_NAME,"",cv);
        return count;
    }


    public void delet(String condition){
//        theDB.delete(TABLE_NAME,"nowScore=0 and topScore=0 and excel=0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0",null);
        theDB.delete(TABLE_NAME,"topScore="+condition,null);
    }

    public String queryByString(){
        Cursor query = theDB.rawQuery("select * from "+TABLE_NAME+" limit 0,1",null);
        String message="nothing";
        if (query.moveToFirst()){
            message=query.getString(1)+"_"+query.getString(2)+"_"+query.getString(3)+"_"+query.getInt(4)+"_"+query.getString(5)+"_"+query.getString(6)+"_"+query.getString(7);
            System.out.println(message);
        }
        return message;
    }


}
