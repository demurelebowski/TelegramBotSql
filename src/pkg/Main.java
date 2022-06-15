package pkg;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) {

        DBAction action =new DBAction();
        try {
            action.PrintAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            action.updateRecord("Donda3",855445,"2022-06-01");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
