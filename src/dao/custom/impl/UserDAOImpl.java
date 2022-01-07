package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import dao.custom.UserDAO;
import entity.Customer;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean ifUserExist(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean add(User user) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public User search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> allUsers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Users");
        while (rst.next()) {
            allUsers.add(new User(rst.getString("name"), rst.getString("password"), rst.getBoolean("isAdmin")));
        }
        return allUsers;
    }
}
