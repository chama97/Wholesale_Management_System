package controller;

import dao.DAOFactory;
import dao.custom.OrderDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane DashFID;
    public Label lblRole;
    public Label lblUserName;
    public AnchorPane Dash2FID;
    public AnchorPane p3;
    public AnchorPane p3shadow;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane p2shadow;
    public AnchorPane p2;
    public Label lblSale;
    public AnchorPane p1;
    public Label lblOrder;
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    public void initialize(){
        lblUserName.setText(LoginFormController.user.getUsername());
        if (LoginFormController.user.isIs_admin() == true) {
            lblRole.setText("Administrator");
        } else {
            lblRole.setText("Receptionist");
        }
        loadDateAndTime();
        try {
            lblOrder.setText("00"+orderDAO.getOrderCount());
            lblSale.setText("Rs: "+orderDAO.getOrderTotalCost());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadDateAndTime() {
        Date dates = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(dates));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void logOutAction(ActionEvent actionEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/LoginForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) DashFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        Dash2FID.getChildren().clear();
        Dash2FID.getChildren().add(load);
    }

    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("CustomerForm");
    }

    public void incomeReportOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("IncomeReportsForm");
    }

    public void placeOderOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("PlaceCustomerOrderForm");
    }
    
    public void orderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("OrderDetailForm");
    }

    public void itemReportOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ItemReportsForm");
    }

    public void manageOrdersOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ManageOrdersForm");
    }

    public void itemsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ItemListForm");
    }
}
