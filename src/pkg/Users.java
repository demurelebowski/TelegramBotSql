package pkg;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Users {
    private final Long timestamp;
    private String name;
    private final Long ChatID;
    private String Date;
    private String password;
    private boolean Authorized;

    public Users(String name, long chatID) throws IOException {
        ChatID = chatID;
        this.timestamp = getTimeInLong();
        this.name = name;
        this.password = generateSimplePassword();
        Date = getCurrentLocalDateTimeStamp();

        //insertUserSql(this);

    }

    private static SqlSessionFactory session() throws IOException {
        String resource = "pkg/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static Users getUserSQL(long chatID) throws IOException {

        try (SqlSession session = session().openSession()) {

            return session.selectOne("selectUserByID", chatID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertUserSql(Users user) throws IOException {

        try (SqlSession session = session().openSession()) {

            session.insert("insertUser", user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserSql(Users user) throws IOException {

        try (SqlSession session = session().openSession()) {

            session.update("updateUser", user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAuthorized() {
        return Authorized;
    }

    public void setAuthorized(boolean authorized) throws IOException {
        Authorized = authorized;
        updateUserSql(this);
    }

    @Override
    public String toString() {
        return "Users{" +
                "timestamp=" + timestamp +
                ", name='" + name + '\'' +
                ", ChatID=" + ChatID +
                ", Date='" + Date + '\'' +
                ", password='" + password + '\'' +
                ", Authorized=" + Authorized +
                '}';
    }

    public long getChatID() {
        return ChatID;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) throws IOException {
        Date = date;
        updateUserSql(this);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getPassword() {
        return password;

    }

    public void setPassword(String password) throws IOException {
        this.password = password;
        updateUserSql(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IOException {
        this.name = name;
        updateUserSql(this);
    }


    private String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    private String generateSimplePassword() {
        int len = 4;
        String chars = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    private Long getTimeInLong() {
        return new Date().getTime() / 1000;
    }
}
