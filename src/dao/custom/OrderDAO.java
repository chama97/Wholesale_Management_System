package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order, String> {
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
    boolean ifOrderExist(String id) throws SQLException, ClassNotFoundException;
    double getOrderTotalCost()throws Exception;
    int getOrderCount()throws ClassNotFoundException, SQLException;
}
