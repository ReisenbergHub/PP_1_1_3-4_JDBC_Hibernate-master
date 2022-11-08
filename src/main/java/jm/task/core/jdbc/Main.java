package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserServiceImpl userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Рас", "Путин", (byte) 20);
        userService.saveUser("Два", "Путин", (byte) 40);
        userService.saveUser("Три", "Путин", (byte) 60);
        userService.saveUser("Путин", "Путин", (byte) 80);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
