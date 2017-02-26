package com.example.limcw.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.limcw.passwordmanager.database.PasswordManagerBaseHelper;
import com.example.limcw.passwordmanager.database.PasswordManagerDbSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limcw on 2/14/2017.
 */

public class AccountList {
    private static AccountList sAccountList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private AccountList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new PasswordManagerBaseHelper(mContext).getWritableDatabase();
    }

    public static AccountList get(Context context) {
        if (sAccountList == null) {
            sAccountList = new AccountList(context);
        }
        return sAccountList;
    }

    public List<Account> getAccountList() {
        List<Account> accounts = new ArrayList<>();

        AccountCursorWrapper cursor = queryAccounts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                accounts.add(cursor.getAccount());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return accounts;
    }

    public Account getAccount(String title) {
        AccountCursorWrapper cursor = queryAccounts(PasswordManagerDbSchema.PasswordManagerTable.Cols.TITLE + " = '" + title + "'", new String[]{title});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getAccount();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getcontentValues(Account account) {
        ContentValues values = new ContentValues();
        values.put(PasswordManagerDbSchema.PasswordManagerTable.Cols.TITLE, account.getTitle().toString());
        values.put(PasswordManagerDbSchema.PasswordManagerTable.Cols.URL, account.getURL().toString());
        values.put(PasswordManagerDbSchema.PasswordManagerTable.Cols.USERNAME, account.getUsername().toString());
        values.put(PasswordManagerDbSchema.PasswordManagerTable.Cols.PASSWORD, account.getPassword().toString());
        values.put(PasswordManagerDbSchema.PasswordManagerTable.Cols.DATE, account.getLastUpdated().toString());

        return values;
    }

    public void addAccount(Account a) {
        ContentValues values = getcontentValues(a);

        mDatabase.insert(PasswordManagerDbSchema.PasswordManagerTable.Name, null, values);
    }

    public boolean deleteAccount(String title) {
        return mDatabase.delete(PasswordManagerDbSchema.PasswordManagerTable.Name, PasswordManagerDbSchema.PasswordManagerTable.Cols.TITLE + " = '" + title + "'", null) > 0;
    }

    public void updateAccount(String title, Account a) {
        ContentValues values = getcontentValues(a);

        mDatabase.update(PasswordManagerDbSchema.PasswordManagerTable.Name, values, PasswordManagerDbSchema.PasswordManagerTable.Cols.TITLE + " = ?", new String[]{title});
    }

    private AccountCursorWrapper queryAccounts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PasswordManagerDbSchema.PasswordManagerTable.Name,
                null, // Columns - null selects all columns
                whereClause,
                null,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new AccountCursorWrapper(cursor);
    }
}
