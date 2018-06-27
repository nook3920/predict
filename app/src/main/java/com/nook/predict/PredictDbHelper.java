package com.nook.predict;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
public class PredictDbHelper extends SQLiteAssetHelper {

    private static final String DBNAME = "predic_sys.db";
    private static final int DBVersion = 1;
    Context context;

    public PredictDbHelper(Context context){
        super(context,DBNAME,null,DBVersion);
        this.context = context;
    }
}
