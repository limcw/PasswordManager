package com.example.limcw.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by limcw on 2/17/2017.
 */

public class AddAccountFragment extends Fragment {

    private String status;
    private static final String EXTRA_RESULT_TITLE = "com.example.limcw.passwordmanager.result_title";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = (String) getActivity().getIntent().getSerializableExtra(AddAccountActivity.EXTRA_STATUS);
        Activity activity = (AppCompatActivity) getActivity();
        if(status.equals("Add")){
            activity.setTitle(R.string.new_account_menu);
        } else {
            activity.setTitle(R.string.edit_account_menu);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_account, container, false);

        final TextView mPassword = (TextView) v.findViewById(R.id.password);
        final ImageButton mPasswordViewButton = (ImageButton) v.findViewById(R.id.view_password_button);
        final EditText titleET = (EditText) v.findViewById(R.id.title);
        final EditText urlET = (EditText) v.findViewById(R.id.url);
        final EditText usernameET = (EditText) v.findViewById(R.id.username);
        final EditText passwordET = (EditText) v.findViewById(R.id.password);

        mPasswordViewButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

        Button add_button = (Button) v.findViewById(R.id.add_button);
        Button save_button = (Button) v.findViewById(R.id.save_button);

        if (status.equals("Add")) {
            save_button.setVisibility(View.GONE);
            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_save_account(titleET.getText().toString(), urlET.getText().toString(), usernameET.getText().toString(), passwordET.getText().toString(), null);
                }
            });
        } else {
            // Edit feature
            add_button.setVisibility(View.GONE);
            final String oldTitle = (String) getActivity().getIntent().getSerializableExtra(AddAccountActivity.EXTRA_ACCOUNT_TITLE);
            Account a = AccountList.get(getActivity()).getAccount(oldTitle);
            titleET.setText(oldTitle);
            urlET.setText(a.getURL());
            usernameET.setText(a.getUsername());
            passwordET.setText(a.getPassword());

            save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                add_save_account(titleET.getText().toString(), urlET.getText().toString(), usernameET.getText().toString(), passwordET.getText().toString(), oldTitle);
                }
            });
        }
        return v;
    }

    private void add_save_account(String title, String url, String username, String password, String oldTitle) {

        boolean accountUnique = checkUniqueTitle(title);
        if (status.equals("Edit")) {
            if (title.equals(oldTitle)) {
                accountUnique = true;
            }
        }
        if (accountUnique) {
            final Account a = new Account(title, url, username, password, new Date());
            final boolean nullAccount = checkNullField(title, url, username, password);

            if (!nullAccount) {
                try {
                    if (status.equals("Add")) {
                        AccountList.get(getActivity()).addAccount(a);
                        Toast.makeText(getContext(), R.string.success_add_account, Toast.LENGTH_LONG).show();
                    } else {
                        // update
                        AccountList.get(getActivity()).updateAccount(oldTitle, a);
                        Toast.makeText(getContext(), R.string.success_update_account, Toast.LENGTH_LONG).show();
                        Intent data = new Intent();
                        data.putExtra(EXTRA_RESULT_TITLE, title);
                        getActivity().setResult(Activity.RESULT_OK, data);
                    }
                    getActivity().finish();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Unable to add / save account. " + e.toString(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), R.string.error_add_account, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), R.string.taken_account_title, Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkUniqueTitle(String title) {
        Account checkExistAccountName = AccountList.get(getActivity()).getAccount(title);
        if (checkExistAccountName != null) {
            if (checkExistAccountName.getTitle().equals(title)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkNullField(String title, String url, String username, String password) {
        if (title.equals("") || url.equals("") || username.equals("") || password.equals(""))
            return true;
        return false;
    }

    public static String getExtraResultTitle(Intent result){
        return result.getStringExtra(EXTRA_RESULT_TITLE);
    }
}
