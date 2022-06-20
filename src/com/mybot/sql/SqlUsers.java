package com.mybot.sql;

import com.mybot.telegram.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SqlUsers {
    private static SqlSessionFactory session() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static List<User> getAllUsers() throws IOException {

        try (SqlSession session = session().openSession()) {

            return session.selectList("selectUsers");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User getUserSQL(long chatID) throws IOException {

        try (SqlSession session = session().openSession()) {

            return session.selectOne("selectUserByID", chatID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertUserSql(User user) throws IOException {

        try (SqlSession session = session().openSession()) {

            session.insert("insertUser", user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserSql(User user) throws IOException {

        try (SqlSession session = session().openSession()) {

            session.update("updateUser", user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
