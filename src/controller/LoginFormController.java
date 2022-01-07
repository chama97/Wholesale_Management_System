package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.UserBO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entity.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginFormController {
    public AnchorPane adminLogin;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Label lblWrong;
    public static User user;

    private final UserBO userBO = (UserBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.USER);

    public void dashboardOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        user = new User(txtUserName.getText(), txtPassword.getText(), false);
        boolean userIsAdmin = isUserAdmin(user);
        user.setIs_admin(userIsAdmin);

        try {
            if (isUserValid(user)) {
                    URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
                    Parent load = FXMLLoader.load(resource);
                    Stage window = (Stage) adminLogin.getScene().getWindow();
                    window.setScene(new Scene(load));
                }else {
                lblWrong.setText("Wrong Username or Password");
                }

        } catch (Exception e) {
            System.out.println("isUserValid Exception");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("Error while trying to connect to database ..");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/asset/img/Error01.png"));
            alert.show();
            return;
        }
    }

    public void UserAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public  boolean isUserValid(User user) throws SQLException, ClassNotFoundException {
        List<UserDTO> users = userBO.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                if (users.get(i).getPassword().equals(user.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public  boolean isUserAdmin(User user) throws SQLException, ClassNotFoundException {
        List<UserDTO> users = userBO.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                if (users.get(i).getPassword().equals(user.getPassword())) {
                    if (users.get(i).isIs_admin() == true) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
