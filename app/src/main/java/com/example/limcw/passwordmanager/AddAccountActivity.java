package com.example.limcw.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by limcw on 2/17/2017.
 */

public class AddAccountActivity extends SingleFragmentActivity {

    public static final String EXTRA_STATUS = "com.example.limcw.passwordmanager.status";
    public static final String EXTRA_ACCOUNT_TITLE = "com.example.limcw.passwordmanager.title";

    public static Intent newIntent(Context packageContext, String status){
        Intent intent = new Intent(packageContext, AddAccountActivity.class);
        intent.putExtra(EXTRA_STATUS, status);
        return intent;
    }

    public static Intent newIntent(Context packageContext, String status, String title){
        Intent intent = new Intent(packageContext, AddAccountActivity.class);
        intent.putExtra(EXTRA_STATUS, status);
        intent.putExtra(EXTRA_ACCOUNT_TITLE, title);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new AddAccountFragment();
    }
}
