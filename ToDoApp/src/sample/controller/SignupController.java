package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signupFirstName;

    @FXML
    private TextField signupLastName;

    @FXML
    private TextField signupUsername;

    @FXML
    private JFXRadioButton signupMaleRadioButton;

    @FXML
    private ToggleGroup Group1;

    @FXML
    private JFXRadioButton signupFemaleRadioButton;

    @FXML
    private PasswordField signupPassword;

    @FXML
    private JFXButton signupButton;

    @FXML
    void initialize() {

        signupButton.setOnAction(event ->{
            createUser();

            signupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/additem.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();





        });

    }

   private void createUser() {

       DatabaseHandler databaseHandler = new DatabaseHandler();

        String firstname = signupFirstName.getText();
        String lastname = signupLastName.getText();
        String username = signupUsername.getText();
        String password = signupPassword.getText();

        String gender;
        if( signupMaleRadioButton.isSelected()) {
            gender = "Male";
        }else gender = "Female";


        User user = new User( firstname, lastname, username, password, gender);
           databaseHandler.signUpUser(user);



    }





}
