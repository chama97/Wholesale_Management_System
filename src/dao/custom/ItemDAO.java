package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item, String> {
        boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

}
