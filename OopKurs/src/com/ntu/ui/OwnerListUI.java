package com.ntu.ui;

import java.sql.Date;
import java.time.LocalDate;

import com.ntu.DateUtil;

import com.ntu.dao.OwnerListDAO;
import com.ntu.dao.OwnerListDAOImpl;

import com.ntu.domain.OwnerList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;
//������� + ���� + ������ ��������, ��������

public class OwnerListUI extends BorderPane {

    private Label msgLabel = new Label();

    private TextField idField = new TextField();
    private TextField firstNameField = new TextField();
    private TextField middleNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField phoneField = new TextField();

    private TextField birthDtField = new TextField();
    private DatePicker birthDatePicker = new DatePicker();

    private TextField serialOfPassportField = new TextField();
    private TextField numOfPassportField = new TextField();
    private TextField addressField = new TextField();

    private Button createButton = new Button("New...");
    private Button updateButton = new Button("Update...");
    private Button deleteButton = new Button("Delete...");


    private TableView<OwnerList> ownerListTable = new TableView<>();

    private TableColumn<OwnerList, Long> idColumn = new TableColumn<OwnerList, Long>("ID");
    private TableColumn<OwnerList, String> firstNameColumn = new TableColumn<OwnerList, String>("First Name");
    private TableColumn<OwnerList, String> middleNameColumn = new TableColumn<OwnerList, String>("Middle Name");
    private TableColumn<OwnerList, String> lastNameColumn = new TableColumn<OwnerList, String>("Last Name");
    private TableColumn<OwnerList, String> phoneColumn = new TableColumn<OwnerList, String>("Phone Number");
    private TableColumn<OwnerList, Date> birthDtColumn = new TableColumn<OwnerList, Date>("Birth Date");
    private TableColumn<OwnerList, String> serialOfPassportColumn = new TableColumn<OwnerList, String>("Serial Of Passport");
    private TableColumn<OwnerList, Integer> numOfPassportColumn = new TableColumn<OwnerList, Integer>("Number Of Passport");
    private TableColumn<OwnerList, String> addressColumn = new TableColumn<OwnerList, String>("Adress");

    private ObservableList<OwnerList> masterData = FXCollections.observableArrayList();

    private OwnerListDAO ownerListDAO = new OwnerListDAOImpl();

    public OwnerListUI() {
        setPadding(new Insets(10, 10, 10, 10));
        setTop(msgLabel);
        setCenter(initFields());
        setBottom(initTable());
        setRight(initButtons());
        initListeners();

        setFieldData(ownerListDAO.getFirstOwnerList());

        setTabledData();

        initDatePickerEvents(birthDatePicker, birthDtField);

    }

    private void initDatePickerEvents(DatePicker datePicker, TextField textField) {
        datePicker.setOnAction(e ->
        {
            LocalDate date = datePicker.getValue();
            if (date  != null) {
                textField.setText(date.toString());
            }
            else {
                textField.setText(null);
            }
        });


    }

    private Pane initButtons() {
        HBox box = new HBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(5);
        box.getChildren().add(createButton);
        createButton.setOnAction(new ButtonHandler());
        box.getChildren().add(updateButton);
        updateButton.setOnAction(new ButtonHandler());
        box.getChildren().add(deleteButton);
        deleteButton.setOnAction(new ButtonHandler());

        return box;

    }

    private Pane initFields() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setVgap(2);

        grid.add(new Label("First name:"), 1, 0);
        grid.add(firstNameField, 2, 0);
        //  titleField.setEditable(false);
        grid.add(new Label("Middle name:"), 1, 1);
        grid.add(middleNameField, 2, 1);
        grid.add(new Label("Last name:"), 1, 2);
        grid.add(lastNameField, 2, 2);
        grid.add(new Label("Phone:"), 1, 3);
        grid.add(phoneField, 2, 3);
        grid.add(new Label("Birth date:"), 1, 4);
        grid.add(birthDatePicker, 2, 4);
        grid.add(new Label("Serial of passport:"), 1, 5);
        grid.add(serialOfPassportField, 2, 5);
        grid.add(new Label("Num of passport:"), 1, 6);
        grid.add(numOfPassportField, 2, 6);
        grid.add(new Label("Adress:"), 1, 7);
        grid.add(addressField, 2, 7);


        return grid;
    }

    private Pane initTable() {
        //StackPane pane = new StackPane();
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));

        //��������� ����� � ��������� �������
        PropertyValueFactory<OwnerList, Long> idCellValueFactory = new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);

        PropertyValueFactory<OwnerList, String> firstNameCellValueFactory = new PropertyValueFactory<>("firstName");
        firstNameColumn.setCellValueFactory(firstNameCellValueFactory);

        PropertyValueFactory<OwnerList, String> middleNameCellValueFactory = new PropertyValueFactory<>("middleName");
        middleNameColumn.setCellValueFactory(middleNameCellValueFactory);

        PropertyValueFactory<OwnerList, String> lastNameCellValueFactory = new PropertyValueFactory<>("lastName");
        lastNameColumn.setCellValueFactory(lastNameCellValueFactory);

        PropertyValueFactory<OwnerList, String> phoneCellValueFactory = new PropertyValueFactory<>("phone");
        phoneColumn.setCellValueFactory(phoneCellValueFactory);

        PropertyValueFactory<OwnerList, Date> birthDtCellValueFactory = new PropertyValueFactory<>("birthDt");
        birthDtColumn.setCellValueFactory(birthDtCellValueFactory);

        PropertyValueFactory<OwnerList, String> serialOfPassportCellValueFactory = new PropertyValueFactory<>("serialOfPassport");
        serialOfPassportColumn.setCellValueFactory(serialOfPassportCellValueFactory);

        PropertyValueFactory<OwnerList, Integer> numOfPassportCellValueFactory = new PropertyValueFactory<>("numOfPassport");
        numOfPassportColumn.setCellValueFactory(numOfPassportCellValueFactory);

        PropertyValueFactory<OwnerList, String> addressCellValueFactory = new PropertyValueFactory<>("address");
        addressColumn.setCellValueFactory(addressCellValueFactory);

        ownerListTable.getColumns().addAll(idColumn, firstNameColumn, middleNameColumn, lastNameColumn, phoneColumn, birthDtColumn, serialOfPassportColumn, numOfPassportColumn, addressColumn );
        pane.getChildren().add(ownerListTable);


        return pane;
    }

    private void setTabledData() {
        masterData = ownerListDAO.getAllOwnerLists();
        ownerListTable.setItems(masterData);

    }

    private void initListeners() {

        ownerListTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                OwnerList  ownerList = newSelection;
                setFieldData(ownerList);
            }
        });

    }

    private OwnerList getFieldData() {
        OwnerList ownerList = new OwnerList();
        ownerList.setId(Integer.parseInt(idField.getText()));
        ownerList.setFirstName(firstNameField.getText());
        ownerList.setMiddleName(middleNameField.getText());
        ownerList.setLastName(lastNameField.getText());
        ownerList.setPhone(phoneField.getText());

        ownerList.setBirthDt(DateUtil.convertStringIntoSqlDate(birthDtField.getText()));

        ownerList.setSerialOfPassport(serialOfPassportField.getText());
        ownerList.setNumOfPassport(Integer.parseInt(numOfPassportField.getText()));
        ownerList.setAddress(addressField.getText());

        return ownerList;
    }


    private void setFieldData(OwnerList ownerList) {
        idField.setText(String.valueOf(ownerList.getId()));
        firstNameField.setText(ownerList.getFirstName());
        middleNameField.setText(ownerList.getMiddleName());
        lastNameField.setText(ownerList.getLastName());
        phoneField.setText(ownerList.getPhone());

        if (ownerList.getBirthDt() != null) {
            birthDatePicker.setValue(ownerList.getBirthDt().toLocalDate());
        }
        else {
            birthDatePicker.setValue(null);
        }

        serialOfPassportField.setText(ownerList.getSerialOfPassport());
        numOfPassportField.setText(String.valueOf(ownerList.getNumOfPassport()));
        addressField.setText(ownerList.getAddress());

    }

    private boolean isEmptyFieldData() {

        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || birthDtField.getText().isEmpty() ) {

            return true;
        }
        else {
            return false;
        }
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            OwnerList ownerList = getFieldData();
            if (e.getSource().equals(createButton)
                    && createButton.getText().equals("Save")) {
                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot create an empty record");
                    return;
                }
                if (ownerListDAO.insertOwnerList(ownerList) ) {
                    msgLabel.setText("New record created successfully.");
                }
                createButton.setText("New...");
                refreshTable();

            } else if (e.getSource().equals(createButton)
                    && createButton.getText().equals("New...")) {
                ownerList.setFirstName(null);
                ownerList.setMiddleName(null);
                ownerList.setLastName(null);
                ownerList.setPhone(null);
                ownerList.setBirthDt(null);
                ownerList.setSerialOfPassport(null);
                ownerList.setNumOfPassport(0);
                ownerList.setAddress(null);

                setFieldData(ownerList);
                createButton.setText("Save");




            } else if (e.getSource().equals(updateButton)) {

                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot update an empty record");
                    return;
                }

                if (ownerListDAO.updateOwnerList(ownerList))
                    msgLabel.setText("PersonReader with ID:"
                            + String.valueOf(ownerList.getId()
                            + " is updated successfully"));

                refreshTable();

            } else if (e.getSource().equals(deleteButton)) {

                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot delete an empty record");
                    return;
                }
                ownerList = ownerListDAO.getOwnerListById(ownerList.getId());
                ownerListDAO.deleteOwnerList(ownerList.getId());
                msgLabel.setText("PersonReader with ID:"
                        + String.valueOf(ownerList.getId()
                        + " is deleted successfully"));

                refreshTable() ;
            }




        }
    }



    private void refreshTable() {

        TableViewFocusModel<OwnerList> personModelFocused =  ownerListTable.getFocusModel();
        setTabledData();
        ownerListTable.setFocusModel(personModelFocused);


    }


}
