package pkg;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        DBAction action =new DBAction();

        try {
            action.GetUser(844555);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
/*
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            action.insertRecord("Rom",665446554);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            action.PrintAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

 */

        try  {

            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new
                    SqlSessionFactoryBuilder().build(inputStream);

            SqlSession session = sqlSessionFactory.openSession();
            List<LargeCities> list = session.selectList("selectCities");

            for (LargeCities a : list) {
                System.out.println("Rank: " + a.getRank() + " Name: " + a.getName());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
