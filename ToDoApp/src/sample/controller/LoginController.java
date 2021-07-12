package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animations.Shaker;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {


    private int userId ;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton loginSignupButton;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();


        loginButton.setOnAction( event -> {


            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);



            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0 ;
            try {
                while (userRow != null && userRow.next()) {
                    counter++;
                    String name = userRow.getString("firstname");
                    userId = userRow.getInt("userid");

                    System.out.println("Welcome! " + name );
                }

                if (counter == 1) {

                    showAddItemScreen();

                }else {
                    Shaker usernameshaker = new Shaker(loginUsername);
                    Shaker passwordshaker = new Shaker(loginPassword);
                    usernameshaker.shake();
                    passwordshaker.shake();
                }
            }
                catch( SQLException e) {
                    e.printStackTrace();
                }

        }) ;



        loginSignupButton.setOnAction( event -> {

            // Take the users to sign up screen
            loginSignupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader() ;
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));

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



    }

    private void showAddItemScreen(){
        // Take users to AddItem screen
        loginSignupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader() ;
        loader.setLocation(getClass().getResource("/sample/view/additem.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        AdditemController additemController = loader.getController();
        additemController.setUserId( userId);


        stage.showAndWait();

    }

}
