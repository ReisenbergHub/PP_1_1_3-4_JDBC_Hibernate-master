package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.connection;

/*
    Комментарий №2: закрывать соединение необходимо в файнали блоке

    Как было:
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    Что сделано:
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
            e.printStackTrace();
        }
*/
/*
    Комментарий №3: метод, который просто читает данные из бд не должен проходить в транзакции

    "Под транзакцией мы понимаем ряд действий (необязательно в БД), которые воспринимаются системой,
        как единый пакет, т.е. или все действия проходят успешно,
        или все откатываются на исходные позиции."

    Что сделано:
    Удалены строки:
            connection.commit();
            connection.rollback(); (эта строка была в catch блоке)

    Включаю вручную автофиксацию после каждого действия. Для этого в строке
            connection.setAutoCommit(false);
    установил true.
*/
/*
    Комментарий №4: в некоторых методах отсутствует коммит

    Строка
            connection.commit();
    добавлена во все методы, кроме:
            getAllUsers()
    Коммит в этот метод не добавлен для соответствия комментарию №3.
*/

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), last_name VARCHAR(45), age INT);");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate(" DROP TABLE IF EXISTS User");
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (name, last_name, age) VALUES (?, ?, ?)");
            connection.setAutoCommit(false);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            System.out.println("User " + name + " added in database");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM User WHERE id=?");
            connection.setAutoCommit(false);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(true);
            ResultSet resultSet = statement.executeQuery("SELECT * From User");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                people.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return people;
    }

    public void cleanUsersTable() {
        try{
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE User");
            System.out.println("Database clear");
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }
}
