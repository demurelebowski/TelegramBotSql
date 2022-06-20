package com.demurelebowski.dao;

import com.demurelebowski.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class SqlUsers {

	public SqlSessionFactory session() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	public List<User> getAllUsers() throws IOException {

		try (SqlSession session = session().openSession()) {

			return session.selectList("selectUsers");

		} catch (Exception e) {
			log.error("Error during getting all users", e);
		}

		return null;
	}

	public User getUserSQL(long chatID) throws IOException {

		try (SqlSession session = session().openSession()) {

			return session.selectOne("selectUserByID", chatID);

		} catch (Exception e) {
			log.error("Error getting user", e);
		}
		return null;
	}

	public void insertUserSql(User user) throws IOException {

		try (SqlSession session = session().openSession()) {

			session.insert("insertUser", user);
			session.commit();
		} catch (Exception e) {
			log.error("Error inserting user", e);
		}
	}

	public void updateUserSql(User user) throws IOException {

		try (SqlSession session = session().openSession()) {

			session.update("updateUser", user);
			session.commit();
		} catch (Exception e) {
			log.error("Error updating user", e);
		}
	}

}
