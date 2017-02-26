package com.example.limcw.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by limcw on 2/21/2017.
 */

public class DisplayAccountPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Account> mAccountList;
    private static final String EXTRA_ACCOUNT_TITLE = "com.example.limcw.passwordmanager.title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_account_pager);

        String title = (String) getIntent().getSerializableExtra((EXTRA_ACCOUNT_TITLE));

        mViewPager = (ViewPager) findViewById(R.id.display_account_pager_view_pager);
        mAccountList = AccountList.get(this).getAccountList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Account account = mAccountList.get(position);
                return DisplayAccountFragment.newInstance(account.getTitle());
            }
            @Override
            public int getCount() {
                return mAccountList.size();
            }
        });
        for (int i = 0; i < mAccountList.size(); i++) {
            if (mAccountList.get(i).getTitle().equals(title)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, String title){
        Intent intent = new Intent(packageContext, DisplayAccountPagerActivity.class);
        intent.putExtra(EXTRA_ACCOUNT_TITLE, title);
        return intent;
    }
}
