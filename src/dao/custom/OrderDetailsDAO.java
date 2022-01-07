package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail,String> {
    String getMostItem()throws ClassNotFoundException, SQLException;
    String getLeastItem()throws ClassNotFoundException, SQLException;
}
