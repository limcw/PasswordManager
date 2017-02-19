package com.example.limcw.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by limcw on 2/16/2017.
 */

public class DisplayAccountFragment extends Fragment {
    private Account mAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = (String) getActivity().getIntent().getSerializableExtra(DisplayAccountActivity.EXTRA_TITLE);
        mAccount = AccountList.get(getActivity()).getAccount(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.display_account, container, false);

        final TextView mPassword = (TextView) v.findViewById(R.id.password);
        final TextView mTitle = (TextView) v.findViewById(R.id.title);
        final TextView mUrl = (TextView) v.findViewById(R.id.url);
        final TextView mUsername = (TextView) v.findViewById(R.id.username);
        final TextView mLastupdated = (TextView) v.findViewById(R.id.lastupdated);
        final Button mEditButton = (Button) v.findViewById(R.id.edit_button);
        final Button mDeleteButton = (Button) v.findViewById(R.id.delete_button);
        final ImageButton mPasswordViewButton = (ImageButton) v.findViewById(R.id.view_password_button);

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

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String title = mAccount.getTitle();
                            boolean delete = AccountList.get(getActivity()).deleteAccount(title);
                            if(delete){
                                Toast.makeText(getContext(), R.string.success_delete_account, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(), PasswordManager.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), R.string.error_delete_account, Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Unable to delete account. " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddAccountActivity.newIntent(getActivity(), "Edit", mAccount.getTitle());
                startActivity(intent);
            }
        });

        mTitle.setText((mAccount.getTitle()));
        mUrl.setText(mAccount.getURL());
        mUsername.setText(mAccount.getUsername());
        mPassword.setText(mAccount.getPassword());
        mLastupdated.setText(mAccount.getDate().toString());

        return v;
    }
}
