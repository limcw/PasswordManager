package com.example.limcw.passwordmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by limcw on 2/16/2017.
 */

public class DisplayAccountActivity extends SingleFragmentActivity {

    private static final String EXTRA_TITLE = "com.example.limcw.passwordmanager.title";

    public static Intent newIntent(Context packageContext, String Title){
        Intent intent = new Intent(packageContext, DisplayAccountActivity.class);
        intent.putExtra(EXTRA_TITLE, Title);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        String accountTitle = (String) getIntent().getSerializableExtra(EXTRA_TITLE);
        return DisplayAccountFragment.newInstance(accountTitle);
    }
}
