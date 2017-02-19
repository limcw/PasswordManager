package com.example.limcw.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by limcw on 2/16/2017.
 */

public class DisplayAccountActivity extends SingleFragmentActivity {

    public static final String EXTRA_TITLE = "com.example.limcw.passwordmanager.title";
    private Account mAccount;

    public static Intent newIntent(Context packageContext, String Title){
        Intent intent = new Intent(packageContext, DisplayAccountActivity.class);
        intent.putExtra(EXTRA_TITLE, Title);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return new DisplayAccountFragment();
    }

}
