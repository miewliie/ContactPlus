package com.augmentis.ayp.contactplus.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.augmentis.ayp.contactplus.Model.ContactDbSchema.ContactTable;

/**
 * Created by Apinya on 8/9/2016.
 */
public class ContactsBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ContactBase.db";
    private static final int VERSION = 1;
    private static final String TAG = "ContactsBaseHelper";

    public ContactsBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Create Database");

        sqLiteDatabase.execSQL("create table " + ContactTable.NAME
                + "("
                + "_id integer primary key autoincrement, "
                + ContactTable.Cols.UUID + ","
                + ContactTable.Cols.NAME + ","
                + ContactTable.Cols.TEL + ","
                + ContactTable.Cols.EMAIL + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
