package com.example.limcw.passwordmanager;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class PasswordManagerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }
}
