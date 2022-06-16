package pkg;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DBAction {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/DBTEST";
    static final String USER = "postgres";
    static final String PASS = "debet2013";

    public static void printSQLException(SQLException ex) {
        System.out.println(ex.getMessage());

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public void PrintAllUsers() throws SQLException {

        String INSERT_USERS_SQL = "SELECT name, \"ChatID\", \"Date\"" + "FROM public.users";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(INSERT_USERS_SQL);

            while (rs.next()) {

                int albumid = rs.getInt("ChatID");

                String title = rs.getString("name");

                System.out.printf("AlbumId = %s , Title = %s ", albumid, title);

                System.out.println();

            }


        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }

    }
    public void GetUser(Integer ChatID) throws SQLException {

        String INSERT_USERS_SQL = "SELECT name, \"ChatID\", \"Date\"" + " FROM public.users WHERE \"ChatID\" = "+ChatID.toString();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(INSERT_USERS_SQL);

            while (rs.next()) {

                int albumid = rs.getInt("ChatID");

                String title = rs.getString("name");

                System.out.printf("AlbumId = %s , Title = %s ", albumid, title);

                System.out.println();

            }


        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }


    }
    public void insertRecord(String name, int ChatId) throws SQLException {
        String INSERT_USERS_SQL = "INSERT INTO public.users " +
                "(name, \"ChatID\", \"Date\", \"password\") " +
                "VALUES (?, ?, ?, ?);";

        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, ChatId);
            preparedStatement.setString(3, getCurrentLocalDateTimeStamp());
            preparedStatement.setString(4, generateSimplePassword());
            System.out.println(System.currentTimeMillis() / 1000L);


            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }
    public String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    private String generatePassword(){

        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return password;
    }
    private String generateSimplePassword(){
        int len = 4;
        String chars = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
    public void updateRecord(String name, int ChatId, String date) throws SQLException {

        String INSERT_USERS_SQL = "UPDATE public.users " +
                "SET name=?, \"ChatID\"=?, \"Date\"=?" +
                "WHERE \"ChatID\"= ?;";

        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, ChatId);
            preparedStatement.setString(3, date);
            preparedStatement.setInt(4, ChatId);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }


}
