package com.example.limcw.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by limcw on 2/14/2017.
 */

public class Home extends Fragment {

    private RecyclerView mAccountRecyclerView;
    private AccountAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, container, false);

        mAccountRecyclerView = (RecyclerView) v.findViewById(R.id.account_recycler_view);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        Button add_new_account_button = (Button) v.findViewById(R.id.add_new_account_button);
        add_new_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddAccountActivity.newIntent(getActivity(),"Add");
                startActivity(intent);
            }
        });

        return v;
    }

    private void updateUI(){
        AccountList accountList = AccountList.get(getActivity());
        List<Account> accounts = accountList.getAccountList();

        if(mAdapter == null){
            mAdapter = new AccountAdapter(accounts);
            mAccountRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAccounts(accounts);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class AccountHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTitleTextView;
        private Account mAccount;

        public AccountHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView;
        }

        @Override
        public void onClick(View v){
            Intent intent = DisplayAccountActivity.newIntent(getActivity(), mAccount.getTitle());
            startActivity(intent);
        }

        public void bindAccount(Account account){
            mAccount = account;
            mTitleTextView.setText((mAccount.getTitle()));
            // set other text view text
        }
    }

    private class AccountAdapter extends RecyclerView.Adapter<AccountHolder>{
        private List<Account> mAccounts;

        public AccountAdapter(List<Account> accounts){
            mAccounts = accounts;
        }

        @Override
        public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new AccountHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountHolder holder, int position){
            Account account = mAccounts.get(position);
            holder.bindAccount(account);
        }

        @Override
        public int getItemCount(){
            return mAccounts.size();
        }

        public void setAccounts(List<Account> accounts){
            mAccounts = accounts;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
