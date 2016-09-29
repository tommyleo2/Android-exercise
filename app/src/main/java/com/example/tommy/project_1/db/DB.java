package com.example.tommy.project_1.db;

import java.util.HashMap;

/**
 * Created by tommy on 9/29/16.
 */

public class DB {
    HashMap<String, String> userList = new HashMap<>();


    public class NoSuchUser extends Exception {
        public static final long serialVersionUID = 1L;
        public NoSuchUser() {
            super("No such user");
        }
        public NoSuchUser(String username, HashMap<String, String> userList) {
            super(
            "Username: '" + username + "' is not found\n" +
            "userList:\n" + userList.toString());
//            for (Map.Entry<String, String> entry: userList.entrySet()) {
//                message += entry.getValue() + "\n";
//            }
//
        }
    }

    public class IncorrectPassword extends Exception {
        public static final long serialVersionUID = 2L;
        public IncorrectPassword() {
            super("Incorrect password");
        }
        public IncorrectPassword(String username, String password, String correctPassword) {
            super(
                    username + "'s password is incorrect\n" +
                            "Input password: " + password + "\n" +
            "Correct password: " + correctPassword);
        }
    }

    public DB() {
        userList.put("Android", "123456");
    }

    public boolean login(String username, String password) throws NoSuchUser, IncorrectPassword{
        if (!userList.containsKey(username)) {
            throw new NoSuchUser(username, userList);
        }
        if (!userList.get(username).equals(password)) {
            throw new IncorrectPassword(username, password, userList.get(username));
        }
        return true;
    }
}
