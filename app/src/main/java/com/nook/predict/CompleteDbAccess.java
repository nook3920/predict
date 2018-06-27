package com.nook.predict;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class CompleteDbAccess {

    SQLiteDatabase db;
    private CompleteDbHelper openHelper;
    Cursor c = null;

    public CompleteDbAccess(Context context){
        openHelper = new CompleteDbHelper(context);
    }

    public void open(){
        db = openHelper.getWritableDatabase();
    }
    public void close(){
        openHelper.close();
        c.close();
    }

    public ArrayList<String> getComplete(String s){
        ArrayList<String> listWord = new ArrayList<>();
        String sql = "SELECT DISTINCT SENSEGROUP FROM BEST_COMPLETE_"+ LangCheck.getDBByKeyTH(s) + " WHERE SENSEGROUP LIKE '" + s + "%' ORDER BY FREQ DESC LIMIT 50";
        c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            listWord.add(c.getString(0));
            c.moveToNext();
        }

        return  listWord;
    }

}
