package controller;

import dao.CrudDAO;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.impl.OrderDetailsDAOImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entity.OrderDetail;
import view.tm.OrderDetailTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailFormController {
    public AnchorPane orderDetail;
    public TableView<OrderDetailTM> tblOrderDetail;
    public TableColumn colItemCod;
    public TableColumn colOrderId;
    public TableColumn colQty;
    public TableColumn colPrice;
    private final OrderDetailsDAO orderDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    public void initialize() {
        colItemCod.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadAllOrderDetails();
    }

    private void loadAllOrderDetails() {
        tblOrderDetail.getItems().clear();
        try {
            ArrayList<OrderDetail> allOrders = orderDAO.getAll();
            for (OrderDetail order : allOrders) {
                tblOrderDetail.getItems().add(new OrderDetailTM(order.getItemCode(),order.getOrderId(),  order.getQty(), order.getPrice()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)orderDetail.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
