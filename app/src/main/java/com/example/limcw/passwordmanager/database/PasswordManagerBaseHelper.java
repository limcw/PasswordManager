package com.example.limcw.passwordmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.limcw.passwordmanager.database.PasswordManagerDbSchema.PasswordManagerTable;

/**
 * Created by limcw on 2/14/2017.
 */

public class PasswordManagerBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "passwordManager.db";

    public PasswordManagerBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PasswordManagerTable.Name + "(" +
                "_id integer primary key autoincrement, " +
                PasswordManagerTable.Cols.TITLE + ", " +
                PasswordManagerTable.Cols.URL + ", " +
                PasswordManagerTable.Cols.USERNAME + ", " +
                PasswordManagerTable.Cols.PASSWORD + ", " +
                PasswordManagerTable.Cols.DATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
