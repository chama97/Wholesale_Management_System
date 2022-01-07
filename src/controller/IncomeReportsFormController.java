package controller;

import dao.DAOFactory;
import dao.custom.OrderDAO;
import dao.custom.QueryDAO;
import entity.Order;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CartTM;
import view.tm.IncomeReportTM;
import view.tm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeReportsFormController {
    public AnchorPane incomeReport;
    public TableView tblIncomeReport;
    public TableColumn colCstId;
    public TableColumn colOdId;
    public TableColumn colOdDate;
    public TableColumn colCstIncome;
    public Label lblTotalIncome;
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    public void initialize() {

        colCstId.setCellValueFactory(new PropertyValueFactory<>("CId"));
        colOdId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOdDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCstIncome.setCellValueFactory(new PropertyValueFactory<>("cost"));

        loadAllOrders();
    }

    private void loadAllOrders() {
        tblIncomeReport.getItems().clear();
        try {
            ArrayList<Order> allOrders = orderDAO.getAll();
            for (Order order : allOrders) {
                tblIncomeReport.getItems().add(new IncomeReportTM(order.getOrderId(), order.getCId(), order.getOrderDate(), order.getCost()));
            }
            lblTotalIncome.setText(String.valueOf(orderDAO.getOrderTotalCost()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)incomeReport.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void printItemOnAction(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/IncomeReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
