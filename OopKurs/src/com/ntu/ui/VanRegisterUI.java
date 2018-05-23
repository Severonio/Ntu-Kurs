package com.ntu.ui;

import java.sql.Date;

import com.ntu.DateUtil;
import com.ntu.dao.*;
import com.ntu.domain.OwnerList;

import com.ntu.domain.Van;
import com.ntu.domain.VanRegister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;


public class VanRegisterUI extends BorderPane {

    private Label msgLabel = new Label();
    private TextField idField = new TextField();

    private ComboBox<Van> vanComboBox = new ComboBox<Van>();
    private DatePicker broughtDatePicker = new DatePicker();
    private DatePicker takenDatePicker = new DatePicker();


    private ComboBox<OwnerList> ownerComboBox = new ComboBox<OwnerList>();


    private Button createButton = new Button("New");
    private Button updateButton = new Button("Update");
    private Button deleteButton = new Button("Delete");
    private Button refreshButton = new Button("Refresh");

    private Button vanButton = new Button("Vans");
    private Button ownerButton = new Button("Owners");

    private TableView<VanRegister> vanRegisterTable = new TableView<>();
    private TableColumn<VanRegister, Long> idColumn = new TableColumn<VanRegister, Long>("ID");
    private TableColumn<VanRegister, Van> vanColumn = new TableColumn<VanRegister, Van>("Van Column");

    private TableColumn<VanRegister, Date> broughtDtColumn = new TableColumn<VanRegister, Date>("Brought Date");
    private TableColumn<VanRegister, OwnerList> ownerColumn = new TableColumn<VanRegister, OwnerList>("Owner Column");

    private TableColumn<VanRegister, Date> takenDtColumn = new TableColumn<VanRegister, Date>("Taken Date");
    private ObservableList<VanRegister> masterData = FXCollections.observableArrayList();


    private VanRegisterDAO vanRegisterDAO = new VanRegisterDAOImpl();
    private VanDAO vanDAO = new VanDAOImpl();
    private OwnerListDAO ownerListDAO = new OwnerListDAOImpl();


    public VanRegisterUI() {
        setPadding(new Insets(10, 10, 10, 10));

        setTop(msgLabel);

        setCenter(initFields());
        setBottom(initTable());
        setRight(initButtons());

        initListeners();

        setFieldData(vanRegisterDAO.getFirstVanRegister());

        setTabledData();



    }



    private Pane initButtons() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setVgap(20);

        grid.add(createButton,  0, 0);
        createButton.setOnAction(new ButtonHandler());
        grid.add(updateButton, 1, 0);
        updateButton.setOnAction(new ButtonHandler());
        grid.add(deleteButton, 2, 0);
        deleteButton.setOnAction(new ButtonHandler());
        grid.add(vanButton, 0, 1);
        vanButton.setOnAction(new ButtonHandler());
        grid.add(ownerButton, 1, 1);
        ownerButton.setOnAction(new ButtonHandler());
        grid.add(refreshButton, 2, 1);
        refreshButton.setOnAction(new ButtonHandler());

        return grid;

    }



    private Pane initFields() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setVgap(2);

        vanComboBox.getItems().addAll((vanDAO.getAllVans()));
        vanComboBox.setMaxWidth(350);
        ownerComboBox.getItems().addAll((ownerListDAO.getAllOwnerLists()));
        ownerComboBox.setMaxWidth(350);


        grid.add(new Label("Van:"), 1, 0);
        grid.add(vanComboBox, 2, 0);


        grid.add(new Label("Brought: "), 1, 2);
        grid.add(broughtDatePicker, 2, 2);

        grid.add(new Label("Owner"), 1, 1);
        grid.add(ownerComboBox, 2, 1);

        grid.add(new Label("Taken: "), 1, 5);
        grid.add(takenDatePicker, 2, 5);



        return grid;
    }


    private Pane initTable() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        vanRegisterTable.setMinHeight(560);
        PropertyValueFactory<VanRegister, Long> idCellValueFactory = new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);

        PropertyValueFactory<VanRegister, Van> vanCellValueFactory = new PropertyValueFactory<>("van");
        vanColumn.setCellValueFactory(vanCellValueFactory);

        PropertyValueFactory<VanRegister, OwnerList> ownerCellValueFactory = new PropertyValueFactory<>("ownerList");
        ownerColumn.setCellValueFactory(ownerCellValueFactory);

        PropertyValueFactory<VanRegister, Date> broughtDtCellValueFactory = new PropertyValueFactory<>("broughtDt");
        broughtDtColumn.setCellValueFactory(broughtDtCellValueFactory);

        PropertyValueFactory<VanRegister, Date> takenDtCellValueFactory = new PropertyValueFactory<>("takenDt");
        takenDtColumn.setCellValueFactory(takenDtCellValueFactory);


        vanRegisterTable.getColumns().addAll(idColumn, broughtDtColumn, takenDtColumn,  vanColumn, ownerColumn);
        pane.getChildren().add(vanRegisterTable);


        return pane;
    }

    private void setTabledData() {
        masterData = vanRegisterDAO.getAllVanRegisters();
        vanRegisterTable.setItems(masterData);

    }

    private void initListeners() {

        vanRegisterTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                VanRegister  vanRegister = newSelection;
                setFieldData(vanRegister);
            }
        });


    }

    private VanRegister getFieldData() {

        VanRegister vanRegister = new VanRegister();

        Van van = (Van)vanComboBox.getValue();
        OwnerList ownerList = (OwnerList)ownerComboBox.getValue();

        vanRegister.setId(Integer.parseInt(idField.getText()));

        vanRegister.setVan(van);

        vanRegister.setOwnerList(ownerList);


        vanRegister.setBroughtDt(DateUtil.convertStringIntoSqlDate(broughtDatePicker.getValue().toString()));


        if (takenDatePicker.getValue() != null) {
            vanRegister.setTakenDt(DateUtil.convertStringIntoSqlDate( takenDatePicker.getValue().toString()));
        }


        return vanRegister;
    }


    private void setFieldData(VanRegister vanRegister) {
        idField.setText(String.valueOf(vanRegister.getId()));
        vanComboBox.setValue(vanRegister.getVan());
        ownerComboBox.setValue(vanRegister.getOwnerList());

        if (vanRegister.getBroughtDt() != null) {
            broughtDatePicker.setValue(vanRegister.getBroughtDt().toLocalDate());
        }
        else {
            broughtDatePicker.setValue(null);
        }
        if (vanRegister.getTakenDt() != null) {
            takenDatePicker.setValue(vanRegister.getTakenDt().toLocalDate());
        }
        else {
            takenDatePicker.setValue(null);
        }
    }

    private boolean isEmptyFieldData() {

        if (vanComboBox.getValue() == null || ownerComboBox.getValue() == null || broughtDatePicker.getValue() == null ) {
            return true;
        }
        else {
            return false;
        }
    }


    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            VanRegister vanRegister = getFieldData();
            if (e.getSource().equals(createButton)
                    && createButton.getText().equals("Save")) {
                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot create an empty record");
                    return;
                }
                if (vanRegisterDAO.insertVanRegister(vanRegister) ) {
                    msgLabel.setText("New record created successfully.");
                }
                createButton.setText("New...");
                refreshTable();

            } else if (e.getSource().equals(createButton)
                    && createButton.getText().equals("New...")) {
                vanRegister.setVan(null);
                vanRegister.setOwnerList(null);
                vanRegister.setBroughtDt(null);
                vanRegister.setTakenDt(null);

                setFieldData(vanRegister);
                createButton.setText("Save");


            } else if (e.getSource().equals(updateButton)) {

                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot update an empty record");
                    return;
                }

                if (vanRegisterDAO.updateVanRegister(vanRegister))
                    msgLabel.setText("VanRegister with ID:"
                            + String.valueOf(vanRegister.getId()
                            + " is updated successfully"));

                refreshTable();

            } else if (e.getSource().equals(deleteButton)) {

                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot delete an empty record");
                    return;
                }
                vanRegister = vanRegisterDAO.getVanRegisterById(vanRegister.getId());
                vanRegisterDAO.deleteVanRegister(vanRegister.getId());
                msgLabel.setText("VanRegister with ID:"
                        + String.valueOf(vanRegister.getId()
                        + " is deleted successfully"));

                refreshTable() ;
            }
            else if (e.getSource().equals(refreshButton)) {
                vanComboBox.getItems().clear();
                vanComboBox.getItems().addAll((vanDAO.getAllVans()));
                ownerComboBox.getItems().clear();
                ownerComboBox.getItems().addAll((ownerListDAO.getAllOwnerLists()));
                refreshTable() ;

            }
            else if (e.getSource().equals(vanButton)) {

                Modal.display("Vans", 600, 700, new VanUI());



            }
            else if (e.getSource().equals(ownerButton)) {

                    Modal.display("Owner List", 930, 700, new OwnerListUI());
            }

        }
    }


    private void refreshTable() {

        TableViewFocusModel<VanRegister> vanModelFocused =  vanRegisterTable.getFocusModel();
        setTabledData();
        vanRegisterTable.setFocusModel(vanModelFocused);

    }




}

