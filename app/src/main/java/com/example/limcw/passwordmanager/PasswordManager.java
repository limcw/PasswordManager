package com.example.limcw.passwordmanager;

import android.support.v4.app.Fragment;

public class PasswordManager extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new Home();
    }
}
