package dao.custom;

import dao.CrudDAO;
import entity.Customer;
import entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User, String> {
    boolean ifUserExist(String id) throws SQLException, ClassNotFoundException;
}
