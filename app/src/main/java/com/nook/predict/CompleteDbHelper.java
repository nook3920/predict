package com.nook.predict;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
public class CompleteDbHelper extends SQLiteAssetHelper {

    private static final String DBNAME = "complete_sys.db";
    private static final int DBVersion = 1;
    Context context;

    public CompleteDbHelper(Context context){
        super(context,DBNAME,null,DBVersion);
        this.context = context;
    }
}
