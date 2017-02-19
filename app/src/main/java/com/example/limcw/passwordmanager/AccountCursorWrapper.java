package com.example.limcw.passwordmanager;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.limcw.passwordmanager.database.PasswordManagerDbSchema;

import java.util.Date;

/**
 * Created by limcw on 2/16/2017.
 */

public class AccountCursorWrapper extends CursorWrapper {
    public AccountCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Account getAccount(){
        String title = getString(getColumnIndex(PasswordManagerDbSchema.PasswordManagerTable.Cols.TITLE));
        String url = getString(getColumnIndex(PasswordManagerDbSchema.PasswordManagerTable.Cols.URL));
        String username = getString(getColumnIndex(PasswordManagerDbSchema.PasswordManagerTable.Cols.USERNAME));
        String password = getString(getColumnIndex(PasswordManagerDbSchema.PasswordManagerTable.Cols.PASSWORD));

        String date = getString(getColumnIndex(PasswordManagerDbSchema.PasswordManagerTable.Cols.DATE));

        Account account = new Account(title);
        account.setTitle(title);
        account.setURL(url);
        account.setUsername(username);
        account.setPassword(password);
        account.setDate(new Date(date));

        return account;
    }
}
