package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Customer;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Orders` (oid, customerID, date, time, cost) VALUES (?,?,?,?,?)", dto.getOrderId(), dto.getCId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCost());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Orders WHERE oid=?", s);
    }

    @Override
    public boolean update(Order orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Order search(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT oid FROM `Orders` WHERE oid=?", oid);
        rst.next();
        return new Order(rst.getString("oid"), rst.getString("customerID"), LocalDate.parse(rst.getString("date")), rst.getString("time"), Double.parseDouble(rst.getString("cost")));
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Orders");
        while (rst.next()) {
            allOrders.add(new Order(rst.getString("oid"), rst.getString("customerID"), LocalDate.parse(rst.getString("date")), rst.getString("time"), rst.getDouble("cost")));
        }
        return allOrders;
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
        return rst.next() ? String.format("OD%03d", (Integer.parseInt(rst.getString("oid").replace("OD", "")) + 1)) : "OD-001";
    }

    @Override
    public boolean ifOrderExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT oid FROM `Orders` WHERE oid=?", id).next();
    }

    @Override
    public double getOrderTotalCost() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(cost) FROM Orders");
        if (rst.next()) {
            return rst.getDouble(1);
        }
        return -1;
    }

    @Override
    public int getOrderCount() throws ClassNotFoundException, SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(oid) FROM Orders");
        if (rst.next()){
            return rst.getInt(1);
        }
        return -1;
    }

}
