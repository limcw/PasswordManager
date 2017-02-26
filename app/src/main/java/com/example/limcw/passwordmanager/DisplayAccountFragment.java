package com.example.limcw.passwordmanager;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
    private static final String ARG_ACCOUNT_TITLE = "account_title";
    private static final int REQUEST_TITLE = 0;
    TextView mPassword;
    TextView mTitle;
    TextView mUrl;
    TextView mUsername;
    TextView mLastUpdated;
    Button mEditButton;
    Button mDeleteButton;
    String result_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = (String) getArguments().getSerializable(ARG_ACCOUNT_TITLE);
        mAccount = AccountList.get(getActivity()).getAccount(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.display_account, container, false);

        mPassword = (TextView) v.findViewById(R.id.password);
        mTitle = (TextView) v.findViewById(R.id.title);
        mUrl = (TextView) v.findViewById(R.id.url);
        mUsername = (TextView) v.findViewById(R.id.username);
        mLastUpdated = (TextView) v.findViewById(R.id.lastupdated);
        mEditButton = (Button) v.findViewById(R.id.edit_button);
        mDeleteButton = (Button) v.findViewById(R.id.delete_button);
        ImageButton mPasswordViewButton = (ImageButton) v.findViewById(R.id.view_password_button);
        ImageButton mCopyUsername = (ImageButton) v.findViewById(R.id.copy_username_button);

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
                            if (delete) {
                                Toast.makeText(getContext(), R.string.success_delete_account, Toast.LENGTH_LONG).show();
                                getActivity().finish();
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
                startActivityForResult(intent, REQUEST_TITLE);
            }
        });

        mCopyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", mUsername.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), R.string.copy_clipboard, Toast.LENGTH_SHORT).show();
            }
        });

        updateUI();
        return v;
    }

    private void updateUI() {
        mTitle.setText(mAccount.getTitle());
        mUrl.setText(mAccount.getURL());
        mUsername.setText(mAccount.getUsername());
        mPassword.setText(mAccount.getPassword());
        mLastUpdated.setText(mAccount.getLastUpdated().toString());
    }

    public static DisplayAccountFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACCOUNT_TITLE, title);

        DisplayAccountFragment fragment = new DisplayAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (result_title != null) {
            mAccount = AccountList.get(getActivity()).getAccount(result_title);
        }
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_TITLE) {
            if (data == null) {
                return;
            }
            result_title = AddAccountFragment.getExtraResultTitle(data);
        }
    }
}
