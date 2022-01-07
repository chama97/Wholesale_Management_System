package controller;

import dao.DAOFactory;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetail;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.CartTM;
import view.tm.IncomeReportTM;
import view.tm.ItemReportTM;
import view.tm.OrderDetailTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemReportsFormController {
    public AnchorPane itemReports;
    public TableView tblItemReports;
    public TableColumn colItemCode;
    public TableColumn colUnitPrice;
    public TableColumn colQtySell;
    public Label mostItem;
    public Label leastItem;
    private final OrderDetailsDAO orderDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    public void initialize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQtySell.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadAllOrderDetails();
    }

    private void loadAllOrderDetails() {
        tblItemReports.getItems().clear();
        try {
            ArrayList<OrderDetail> allOrders = orderDAO.getAll();
            for (OrderDetail order : allOrders) {
                tblItemReports.getItems().add(new ItemReportTM(order.getItemCode(), order.getQty(), order.getPrice()));
            }
            mostItem.setText(orderDAO.getMostItem());
            leastItem.setText(orderDAO.getLeastItem());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)itemReports.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void printItemOnAction(MouseEvent mouseEvent) {
    }
}
