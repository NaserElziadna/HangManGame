package com.nmmsoft.hangman.logic;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="hangman.db";
    private static final String DB_TABLE="Player";

   //Columns
    private static final String ID="Id";
    private static final String NAME="Name";
    private static final String SCORE="Score";
//    CREATE TABLE "Player" (
//            "Id"	INTEGER,
//            "Name"	TEXT NOT NULL,
//            "Score"	INTEGER NOT NULL DEFAULT 0,
//    PRIMARY KEY("Id" AUTOINCREMENT)
//);

    private static final String CREATE_TABLE="CREATE TABLE "+DB_TABLE+" ("+
            ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME+" TEXT NOT NULL ,"+
            SCORE+" INTEGER NOT NULL DEFAULT 0 "+");";

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        long result;
        if (checkNameExits(name)==true){
            return false;
        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, name);
            result = db.insert(DB_TABLE, null, contentValues);
        }
        return result!=-1; //if result = -1 data doesn't inserted
    }

    public String getPlayerScore(String pName){
        String num=null;
        SQLiteDatabase db = this.getReadableDatabase();
//        SELECT Score FROM Player WHERE Name='naser'
//        String query = "Select Score from "+DB_TABLE+" where Name='"+pName+"'";
        String query = "SELECT * FROM Player WHERE Name='"+pName+"'";
        Cursor cursor =db.rawQuery(query,null);
        if( cursor != null && cursor.moveToFirst() ){
            num = cursor.getString(cursor.getColumnIndex("Score"));
            cursor.close();
        }

        return num;
    }

    public void updatePlayerScoreData(String pName,int score){
        SQLiteDatabase db = this.getWritableDatabase();
//        long result;
//
//        ContentValues cv = new ContentValues();
//        cv.put(SCORE, score);
//        result=db.update(DB_TABLE, cv, NAME + " = "+pName, null);
//
//        return result!=-1; //if result = -1 data doesn't inserted
        String strSQL = "UPDATE "+DB_TABLE+" SET Score = "+score+" WHERE Name = '"+pName+"'";

        db.execSQL(strSQL);
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE;
        Cursor cursor =db.rawQuery(query,null);

        return cursor;
    }
//    public Integer getPlayerIdByName(String pName){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "Select Id from "+DB_TABLE+" where Name = '"+pName+"'";
//        Cursor cursor =db.rawQuery(query,null);
//
//        if(cursor.getCount() <= 0)
//        {
//            return null;
//        }
//        else
//        {
//            cursor.close();
//            return cursor.getInt(0);
//        }
//
//    }

    public boolean checkNameExits(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + DB_TABLE + " where Name = " + "'"+name+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        else
        {
            cursor.close();
            return true;
        }
    }
//    public Cursor searchUsers(String text){
//        SQLiteDatabase db =this.getReadableDatabase();
//        String query = "Select * from "+DB_TABLE+" WHERE "+NAME+" Like '%"+text+"%'";
//        Cursor cursor =db.rawQuery(query,null);
//
//        return cursor;
//    }
}
