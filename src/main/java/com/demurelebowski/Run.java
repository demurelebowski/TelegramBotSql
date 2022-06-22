package com.demurelebowski;

import com.demurelebowski.dao.SqlUsers;
import com.demurelebowski.dto.User;

import java.io.IOException;

public class Run {

    public static void main(String[] args) throws IOException {
        /*
        User foundUser = SqlUsers.getUserSQL(1L);
        if (foundUser != null){
            foundUser.setAuthorized(false);

            SqlUsers.updateUserSql(foundUser);
            System.out.println(foundUser);
        }
        else {
            System.out.println("User not found.");
        }
*/
        // User foundUser = new User("Npm", 123124);

        SqlUsers.insertUserSql(new User("Frank",10));

        User fu = SqlUsers.getUserSQL(10);
        if (fu != null) {
            fu.setAuthorized(true);
        }
        SqlUsers.updateUserSql(fu);

        System.out.println(SqlUsers.getUserSQL(10));
    }
}
