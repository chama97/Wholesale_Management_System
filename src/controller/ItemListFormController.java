package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;
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
import entity.Item;
import util.Validation;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemListFormController {
    public AnchorPane itemsList;
    public TableView<ItemTM> tblItemList;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TextField txtItemCode;
    public TextField txtQty;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public JFXButton btnAdd;
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public JFXButton btnUpdate;
    public JFXButton btnDelete;


    public void initialize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        controlUI();
        tblItemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnUpdate.setDisable(newValue == null);
        });
        btnAdd.setDisable(true);
        storeValidations();
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        tblItemList.getItems().clear();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItemList.getItems().add(new ItemTM(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void controlUI() {
        txtItemCode.clear();
        txtDescription.clear();
        txtQty.clear();
        txtUnitPrice.clear();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[I][0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern qtyPattern = Pattern.compile("^[0-9 ]{1,100}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    private void storeValidations() {
        map.put(txtItemCode, idPattern);
        map.put(txtDescription, namePattern);
        map.put(txtQty, qtyPattern);
        map.put(txtUnitPrice, pricePattern);
    }

    public void addItemOnAction(ActionEvent actionEvent) {
        String id = txtItemCode.getText();
        String name = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());

        try {
            if (exitItem(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }
            ItemDTO dto = new ItemDTO(id, name, qty, price );
            itemBO.addItem(dto);
            tblItemList.getItems().add(new ItemTM(id, name, qty, price));
            new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully " + id).show();
            controlUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteItemsOnAction(ActionEvent actionEvent) {
        String id = tblItemList.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!exitItem(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            itemBO.deleteItem(id);

            tblItemList.getItems().remove(tblItemList.getSelectionModel().getSelectedItem());
            tblItemList.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully " + id).show();
            controlUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ItemsUpdateOnAction(ActionEvent actionEvent) {
        String id = txtItemCode.getText();
        String name = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());
        try {
            if (!exitItem(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            ItemDTO dto = new ItemDTO(id, name, qty, price );
            itemBO.addItem(dto);
            new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully " + id).show();
            controlUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ItemTM selectedCustomer = tblItemList.getSelectionModel().getSelectedItem();
        selectedCustomer.setDescription(name);
        selectedCustomer.setQtyOnHand(qty);
        selectedCustomer.setUnitPrice(price);
        tblItemList.refresh();
    }

    boolean exitItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(id);
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

    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)itemsList.getScene().getWindow();
        window.setScene(new Scene(load));
    }

}
