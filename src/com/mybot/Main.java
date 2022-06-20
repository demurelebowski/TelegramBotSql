package com.mybot;

import com.mybot.sql.SqlUsers;
import com.mybot.telegram.User;

import java.io.IOException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        /*
        int size = 10;

        for (int i = 0; i < size; i++) {
            SqlUsers.insertUserSql(new User("User" + i, i));
        }

        List<User> allUsers = SqlUsers.getAllUsers();
        //System.out.println(fUser);
        int co = 0;
        if (allUsers != null) {

            for (User us : allUsers) {
                System.out.println(us);
            }

        } else {
            System.out.println("User not found.");
        }
*/
        User foundUser = SqlUsers.getUserSQL(1);

        if (foundUser != null){
            foundUser.setAuthorized(true);
            SqlUsers.updateUserSql(foundUser);
        }
        else {
            System.out.println("User not found.");
        }

    }
}
