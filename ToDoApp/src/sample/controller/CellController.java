package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CellController extends JFXListCell<Task> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label taskLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateCreatedLabel;

    @FXML
    private ImageView deleIconImageView;

    @FXML
    private ImageView updateImgIcon;

    private FXMLLoader fxmlLoader ;
    private DatabaseHandler databaseHandler;
    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);

        if( empty || myTask == null ) {
            setText(null);
            setGraphic(null);
        }else {
                if( fxmlLoader == null ) {
                    fxmlLoader = new FXMLLoader(getClass()
                            .getResource("/sample/view/cell.fxml"));
                    fxmlLoader.setController(this);

                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                taskLabel.setText(myTask.getTask());
                descriptionLabel.setText(myTask.getDescription());
                dateCreatedLabel.setText(myTask.getDatecreated().toString());


                int taskId = myTask.getTaskId();

                deleIconImageView.setOnMouseClicked( event -> {
                    getListView().getItems().remove(getItem());
                    try {
                        databaseHandler = new DatabaseHandler();
                        databaseHandler.deleteTask(AdditemController.userId , taskId);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });

                updateImgIcon.setOnMouseClicked(event -> {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/updatetask.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));

                    UpdateTaskController updateTaskController =
                            loader.getController();
                    updateTaskController.setTaskField(myTask.getTask());
                    updateTaskController.setUpdateDescriptionField(myTask.getDescription());

                    updateTaskController.updateTaskButton.setOnAction(event1 -> {
                        Calendar calendar = Calendar.getInstance();

                        java.sql.Timestamp timestamp =
                                new java.sql.Timestamp(calendar.getTimeInMillis());

                        try {
                            System.out.println("taskid " + myTask.getTaskId());
                            databaseHandler = new DatabaseHandler();
                            databaseHandler.updateTask(timestamp,updateTaskController.getDescription(),
                                    updateTaskController.getTask(), myTask.getTaskId());

                            // update our listController
                            //updateTaskController.refreshList();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });

                    stage.show();

                });





//                System.out.println("User Id From Cell Controller " + AdditemController.userId);
//            System.out.println("Task is: " + myTask.getTask());
                setText(null);
                setGraphic(rootAnchorPane);

        }
    }
}
