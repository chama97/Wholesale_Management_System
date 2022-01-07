package dao.custom.impl;


import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean add(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO OrderDetails (itemCode, oid, qty, unitPrice) VALUES (?,?,?,?)", dto.getItemCode(), dto.getOrderId(),  dto.getQty(), dto.getPrice());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderDetail orderDetailDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> allOrders = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetails");
        while (rst.next()) {
            allOrders.add(new OrderDetail(rst.getString("itemCode"), rst.getString("oid"), rst.getInt("qty"), rst.getDouble("unitPrice")));
        }
        return allOrders;
    }

    @Override
    public String getMostItem() throws ClassNotFoundException, SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM OrderDetails WHERE qty = (SELECT MAX(qty) FROM OrderDetails);");
        if (rst.next()) {
            return rst.getString(1);
        }
        return "";
    }

    @Override
    public String getLeastItem() throws ClassNotFoundException, SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM OrderDetails WHERE qty = (SELECT MIN(qty) FROM OrderDetails);");
        if (rst.next()) {
            return rst.getString(1);
        }
        return "";
    }
}