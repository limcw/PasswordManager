package com.example.limcw.passwordmanager;

import java.util.Date;

/**
 * Created by limcw on 2/14/2017.
 */

public class Account {
    private String mTitle;
    private String mURL;
    private String mUsername;
    private String mPassword;
    private Date mDate;

    public Account(String title, String url, String username, String password, Date date){
        setTitle(title);
        setURL(url);
        setUsername(username);
        setPassword(password);
        setDate(date);
    }

    public Account(String title){
        mTitle = title;
        mDate = new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
