package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

/*
    Комментарий №5: использовать полиморфизм, обращаться к классу через интерфейс

    Как было:
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
    Должно быть:
        UserDao userDao = new UserDaoJDBCImpl();
    Чтобы это заработало, необходимо было добавить импорт:
        import jm.task.core.jdbc.dao.UserDao;
    shame!!!
    Также сделал это поле private.
*/

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoJDBCImpl();

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> users =  userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
