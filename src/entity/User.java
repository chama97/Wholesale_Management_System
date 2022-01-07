package entity;

import controller.LoginFormController;

import java.sql.SQLException;
import java.util.List;

public class User {

    private String username;
    private String password;
    private boolean is_admin;

    public User() {
    }

    public User(String username, String password, boolean is_admin) {
        this.setUsername(username);
        this.setPassword(password);
        this.setIs_admin(is_admin);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", is_admin=" + is_admin +
                '}';
    }

}
