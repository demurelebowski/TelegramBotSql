package com.demurelebowski;

import com.demurelebowski.dto.User;
import com.demurelebowski.dao.SqlUsers;

import java.io.IOException;

public class Main {

    private static final SqlUsers sqlUsers = new SqlUsers();

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

        sqlUsers.insertUserSql(new User("User100" , 999999999999L));

        User foundUser = sqlUsers.getUserSQL(1);

        if (foundUser != null){
            foundUser.setAuthorized(true);
            sqlUsers.updateUserSql(foundUser);
        }
        else {
            System.out.println("User not found.");
        }

    }
}
