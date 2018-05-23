package com.ntu.ui;

import com.ntu.dao.VanDAO;
import com.ntu.dao.VanDAOImpl;
import com.ntu.domain.Van;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
public class VanUI extends BorderPane {


    private Label msgLabel = new Label();
    private TextField idField = new TextField();
    private TextField titleField = new TextField();
    private TextField customerField = new TextField();
    private TextField madeField = new TextField();
    private TextField weightField = new TextField();

    private Button createButton = new Button("New...");
    private Button updateButton = new Button("Update...");
    private Button deleteButton = new Button("Delete...");

    private TableView<Van> vanTable = new TableView<>();
    private TableColumn<Van, Long> idColumn = new TableColumn<Van, Long>("ID");
    private TableColumn<Van, String> titleColumn = new TableColumn<Van, String>("Type");
    private TableColumn<Van, String> customerColumn = new TableColumn<Van, String>("Customer");
    private TableColumn<Van, Integer> madeColumn = new TableColumn<Van, Integer>("Made Date");
    private TableColumn<Van, Integer> weightColumn = new TableColumn<Van, Integer>("Weight");
    private ObservableList<Van> masterData = FXCollections.observableArrayList();


    private VanDAO vanDAO = new VanDAOImpl();

    public VanUI() {
        setPadding(new Insets(10, 10, 10, 10));
        setTop(msgLabel);
        setCenter(initFields());
        setBottom(initTable());
        setRight(initButtons());
        initListeners();
        setFieldData(vanDAO.getFirstVan());

        setTabledData();
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
        grid.setPadding(new Insets(10, 10, 10, 10));  //������� ������
        grid.setHgap(20); //�������������� ������ ��������
        grid.setVgap(2);  //������������ ������ ��������

        grid.add(new Label("Type:"), 1, 0);
        grid.add(titleField, 2, 0);
        grid.add(new Label("Customer:"), 1, 1);
        grid.add(customerField, 2, 1);
        grid.add(new Label("Made Date:"), 1, 2);
        grid.add(madeField, 2, 2);
        grid.add(new Label("Weight: "), 1, 3);
        grid.add(weightField, 2, 3);

        //  titleField.focusedProperty().addListener(bookTable);;

        return grid;


    }

    private Pane initTable() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        vanTable.setMinHeight(490);
        PropertyValueFactory<Van, Long> idCellValueFactory = new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);

        PropertyValueFactory<Van, String> titleCellValueFactory = new PropertyValueFactory<>("Title");
        titleColumn.setCellValueFactory(titleCellValueFactory);

        PropertyValueFactory<Van, String> customerCellValueFactory = new PropertyValueFactory<>("Customer");
        customerColumn.setCellValueFactory(customerCellValueFactory);

        PropertyValueFactory<Van, Integer> madeCellValueFactory = new PropertyValueFactory<>("Made");
        madeColumn.setCellValueFactory(madeCellValueFactory);

        PropertyValueFactory<Van, Integer> weightCellValueFactory = new PropertyValueFactory<>("Weight");
        weightColumn.setCellValueFactory(weightCellValueFactory);

        vanTable.getColumns().addAll(idColumn, titleColumn, customerColumn, madeColumn, weightColumn);
        pane.getChildren().add(vanTable);


        return pane;
    }

    private void setTabledData() {
        masterData = vanDAO.getAllVans();
        vanTable.setItems(masterData);

    }

    private void initListeners() {

        vanTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Van  van = newSelection;
                setFieldData(van);
            }
        });


    }

    private Van getFieldData() {
        Van van = new Van();
        van.setId(Integer.parseInt(idField.getText()));
        van.setTitle(titleField.getText());
        van.setCustomer(customerField.getText());
        van.setMade(Integer.parseInt(madeField.getText()));
        van.setWeight(Integer.parseInt(weightField.getText()));

        return van;
    }

    private void setFieldData(Van van) {
        idField.setText(String.valueOf(van.getId()));
        titleField.setText(van.getTitle());
        customerField.setText(van.getCustomer());
        madeField.setText(String.valueOf(van.getMade()));
        weightField.setText(String.valueOf(van.getMade()));
    }

    private boolean isEmptyFieldData() {

        if (titleField.getText().isEmpty() || customerField.getText().isEmpty() ) {

            return true;
        }
        else {
            return false;
        }
    }



    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Van van = getFieldData();
            if (e.getSource().equals(createButton)
                    && createButton.getText().equals("Save")) {
                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot create an empty record");
                    return;
                }
                if (vanDAO.insertVan(van) ) {
                    msgLabel.setText("New book created successfully.");
                }
                createButton.setText("New...");
                refreshTable();

            } else if (e.getSource().equals(createButton)
                    && createButton.getText().equals("New...")) {
                van.setTitle("");
                van.setCustomer("");
                van.setMade(2018);
                van.setWeight(0);

                setFieldData(van);
                createButton.setText("Save");


            } else if (e.getSource().equals(updateButton)) {

                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot update an empty record");
                    return;
                }

                if (vanDAO.updateVan(van))
                    msgLabel.setText("Van with ID:"
                            + String.valueOf(van.getId()
                            + " is updated successfully"));

                refreshTable();

            } else if (e.getSource().equals(deleteButton)) {

                if (isEmptyFieldData()) {
                    msgLabel.setText("Cannot delete an empty record");
                    return;
                }
                van = vanDAO.getVanById(van.getId());
                vanDAO.deleteVan(van.getId());
                msgLabel.setText("Van with ID:"
                        + String.valueOf(van.getId()
                        + " is deleted successfully"));

                refreshTable() ;
            }


        }
    }



    private void refreshTable() {

        TableViewFocusModel<Van> bookModelFocused =  vanTable.getFocusModel();
        setTabledData();
        vanTable.setFocusModel(bookModelFocused);


    }


}

