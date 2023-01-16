package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

//        String create = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(20) NULL, lastName VARCHAR(20) NULL, age INT NULL, PRIMARY KEY (id))";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(20) NULL, lastName VARCHAR(20) NULL, age INT NULL, PRIMARY KEY (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
//        String drop = "DROP TABLE IF EXISTS users";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
//        String save = "INSERT INTO users (name, lastName, age) values (?,?,?)";

        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement("INSERT INTO users (name, lastName, age) values (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
//        String remove = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
//        String all = "SELECT id, name, lastName, age FROM users";

        try (Statement statement = Util.getConnection().createStatement();
             ResultSet res = statement.executeQuery("SELECT id, name, lastName, age FROM users")) {

            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastName"));
                user.setAge((byte) res.getFloat("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
//        String clean = "DELETE FROM users";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

