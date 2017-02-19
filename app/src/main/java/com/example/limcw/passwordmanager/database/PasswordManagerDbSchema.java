package com.example.limcw.passwordmanager.database;

/**
 * Created by limcw on 2/14/2017.
 */

public class PasswordManagerDbSchema {
    public static final class PasswordManagerTable{
        public static final String Name = "passwordManager";

        public static final class Cols{
            public static final String TITLE = "title";
            public static final String URL = "url";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String DATE = "date";
        }
    }
}
