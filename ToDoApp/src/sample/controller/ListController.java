package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
public class ListController {

    @FXML
    private ImageView listRefreshImgView;
    @FXML
    private JFXListView<Task> listTask;
    @FXML
    private TextField listTaskField;
    @FXML
    private TextField listDescriptionField;
    @FXML
    private JFXButton listSaveTaskButton;

    private ObservableList<Task> tasks ;
    private ObservableList<Task> refreshedTasks ;

    private DatabaseHandler databaseHandler;
    @FXML
    void initialize() throws SQLException {

        tasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTaskByUser(AdditemController.userId);

        while( resultSet.next() ){
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskId"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);
//            System.out.println("User tasks: " + resultSet.getString("task") );
        }






        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());

        listSaveTaskButton.setOnAction(event -> {
            addNewTask();
        });

        listRefreshImgView.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }

    public void refreshList() throws SQLException {

        System.out.println("Refresh list in ListCont called");

        refreshedTasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTaskByUser(AdditemController.userId);

        while(resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskId"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);

        }

        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());


    }
    public void addNewTask() {

            if( !listTaskField.getText().equals("") ||
            !listDescriptionField.getText().equals("") ) {

                databaseHandler = new DatabaseHandler();
                Task myNewTask = new Task() ;

                Calendar calendar = Calendar.getInstance();

                java.sql.Timestamp timestamp =
                        new java.sql.Timestamp(calendar.getTimeInMillis());

                myNewTask.setUserId(AdditemController.userId);
                myNewTask.setTask(listTaskField.getText().trim());
                myNewTask.setDescription(listDescriptionField.getText().trim());
                myNewTask.setDatecreated(timestamp);

                databaseHandler.insertTask(myNewTask);

                listTaskField.setText("");
                listDescriptionField.setText("");

                try {
                    initialize();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
    }

}