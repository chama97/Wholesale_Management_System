package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dao.custom.impl.OrderDAOImpl;
import dto.OrderDTO;
import entity.Order;
import javafx.event.ActionEvent;
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
import view.tm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrdersFormController {
    public AnchorPane manageOrder;
    public TableView<OrderTM> tblOrders;
    public TableColumn colOrderId;
    public TableColumn colCstId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCost;

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCstId.setCellValueFactory(new PropertyValueFactory<>("CId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        initUI();
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnUpdate.setDisable(newValue == null);
        });
        loadAllOrders();
    }

    private void loadAllOrders() {
        tblOrders.getItems().clear();
        try {
            ArrayList<Order> allOrders = orderDAO.getAll();
            for (Order order : allOrders) {
                tblOrders.getItems().add(new OrderTM(order.getOrderId(), order.getCId(), order.getOrderDate(), order.getOrderTime(), order.getCost()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)manageOrder.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void UpdateOrderOnAction(ActionEvent actionEvent) {
        initUI();
    }

    public void deleteOrderOnAction(ActionEvent actionEvent) {
        String id = tblOrders.getSelectionModel().getSelectedItem().getOrderId();
        try {
            if (!existOrder(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            orderDAO.delete(id);

            tblOrders.getItems().remove(tblOrders.getSelectionModel().getSelectedItem());
            tblOrders.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully " + id).show();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean existOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.ifOrderExist(id);
    }

}
