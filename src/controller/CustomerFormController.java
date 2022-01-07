package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomDTO;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entity.Customer;
import util.Validation;
import view.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane customerDetail;
    public TableView<CustomerTM> tblCstDetail;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colProvince;
    public TableColumn colCode;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnAdd;
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public TextField txtSearch;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        initUI();
        tblCstDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    btnDelete.setDisable(newValue == null);
                    btnUpdate.setDisable(newValue == null);
        });
        btnAdd.setDisable(true);
        storeValidations();
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        tblCstDetail.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();
            for (CustomerDTO customer : allCustomers) {
                tblCstDetail.getItems().add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress(), customer.getProvince(), customer.getPostalCode()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)customerDetail.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[C][0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern provincePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern codePattern = Pattern.compile("^[0-9]{4,6}$");

    private void storeValidations() {
        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtProvince, provincePattern);
        map.put(txtPostalCode, codePattern);
    }

    public void addCustomerOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String province = txtProvince.getText();
        String code = txtPostalCode.getText();

        try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }
            CustomerDTO customerDTO = new CustomerDTO(id, name, address, province, code);
            customerBO.addCustomer(customerDTO);
            tblCstDetail.getItems().add(new CustomerTM(id, name, address, province, code));
            new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully " + id).show();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = Validation.validate(map,btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) {
        String id = tblCstDetail.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            customerBO.deleteCustomer(id);
            tblCstDetail.getItems().remove(tblCstDetail.getSelectionModel().getSelectedItem());
            tblCstDetail.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully " + id).show();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void UpdateCustomerOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String province = txtProvince.getText();
        String code = txtPostalCode.getText();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }

            CustomerDTO customerDTO = new CustomerDTO(id, name, address, province, code);
            customerBO.updateCustomer(customerDTO);
            new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully " + id).show();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        CustomerTM selectedCustomer = tblCstDetail.getSelectionModel().getSelectedItem();
        selectedCustomer.setName(name);
        selectedCustomer.setAddress(address);
        selectedCustomer.setProvince(province);
        selectedCustomer.setPostalCode(code);
        tblCstDetail.refresh();
    }
}
