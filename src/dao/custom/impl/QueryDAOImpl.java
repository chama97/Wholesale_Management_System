package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import dto.CustomDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO> getOrderDetailsFromOrderID(String oid) throws SQLException, ClassNotFoundException {
        ArrayList<CustomDTO> allData= new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("select o.oid,o.date,o.customerID,od.oid,od.itemCode,od.qty,od.unitPrice,o.cost from Orders o inner join OrderDetails od on o.oid=od.oid where o.oid=?;", oid);
        while (rst.next()) {
            allData.add(new CustomDTO(rst.getString("oid"), LocalDate.parse(rst.getString("date")), rst.getString("customerID"), rst.getString("itemCode"), rst.getInt("qty"), rst.getDouble("unitPrice"), rst.getDouble("cost")));
        }
        return allData;
    }

}
