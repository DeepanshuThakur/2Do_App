package sample.controller;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;


public class AdditemFormController {


    private int userId ;

    @FXML
    private Label successLabel;

    @FXML
    private JFXButton toDoButton;

    @FXML
    private TextField taskField;

    @FXML
    private TextField descriptionField;

    @FXML
    private JFXButton saveTaskButton;

    private DatabaseHandler databaseHandler;
    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        saveTaskButton.setOnAction(event -> {

            Task task = new Task();

            Calendar calendar = Calendar.getInstance();

            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(calendar.getTimeInMillis());

            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            if( !taskText.equals("") || !taskDescription.equals("")) {

                System.out.println("User id : " + AdditemController.userId);
                task.setUserId(AdditemController.userId) ;
                task.setDatecreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskText);

                databaseHandler.insertTask(task);

                successLabel.setVisible(true);

                toDoButton.setVisible(true);
                int tasknumber = 0;
                try {
                    tasknumber = databaseHandler.getAllTasks(AdditemController.userId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                toDoButton.setText("My 2Dos: (" + tasknumber + ")");

                taskField.setText("");
                descriptionField.setText("");

                toDoButton.setOnAction(event1 -> {
                // send users to list screen
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/list.fxml"));

                    try {
                        loader.load();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();



                });



            }else {
                System.out.println("Nothing added!");
            }

        });
    }


    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User ID is : " + this.userId );
    }

}
