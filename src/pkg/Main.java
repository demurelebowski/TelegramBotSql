package pkg;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {

        DBAction action =new DBAction();
/*
        try {
            action.GetUser(844555);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*
 */
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
        String resource = "pkg/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

/*
        try(SqlSession session = sqlSessionFactory.openSession();)  {

            Users newuser = new Users(111222,"Tom");
            session.insert("insertUser", newuser);
            session.commit();
            System.out.println(newuser);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
*/

        try(SqlSession session = sqlSessionFactory.openSession();)  {

            List<Users> list = session.selectList("selectUserByID",111222L);

            for (Users a : list) {
                System.out.println(a);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
