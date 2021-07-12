package sample.database;


import sample.model.Task;
import sample.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName ;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    // Write
    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USERS_TABLE +"(" + Const.USERS_FIRSTNAME +
                "," + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD
                + "," + Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString( 1 , user.getFirstName());
            preparedStatement.setString( 2 , user.getLastName());
            preparedStatement.setString( 3 , user.getUserName());
            preparedStatement.setString( 4 , user.getPassword() );
            preparedStatement.setString( 5 , user.getGender());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getTaskByUser(int userId ) {
        ResultSet resultTasks = null ;

        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " +
                Const.USERS_ID + " =?" ;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userId);

            resultTasks = preparedStatement.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        return resultTasks;
    }
    public ResultSet getUser(User user ) {

        ResultSet resultSet = null ;
       if( !user.getUserName().equals("") || !user.getPassword().equals("") ) {
           String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                   Const.USERS_USERNAME + " =?" + " AND " + Const.USERS_PASSWORD + " =?" ;


           try {
               PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
               preparedStatement.setString( 1, user.getUserName());
               preparedStatement.setString(2, user.getPassword());

               resultSet = preparedStatement.executeQuery();

           } catch (SQLException throwables) {
               throwables.printStackTrace();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }


       }

       return resultSet ;

    }
    public int getAllTasks(int userId) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " +
                Const.USERS_ID + " =?" ;


            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                return resultSet.getInt(1);
            }

            return resultSet.getInt(1);


    }

    public void insertTask(Task task) {

        String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" + Const.USERS_ID + "," + Const.TASKS_TASK + ","
                + Const.TASKS_DATE + "," + Const.TASKS_DESCRIPTION + ") " + " VALUES(?,?,?,?)" ;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1 , task.getUserId() );
            preparedStatement.setString( 2 , task.getTask());
            preparedStatement.setTimestamp( 3, task.getDatecreated());
            preparedStatement.setString(4, task.getDescription());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void deleteTask( int userId , int taskId) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " +
                    Const.USERS_ID + " =? " + " AND " + Const.TASKS_ID + " =?" ;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1,userId );
        preparedStatement.setInt(2,taskId );
        preparedStatement.execute();
        preparedStatement.close();
    }
    // Read

    // Update
    public void updateTask(Timestamp datecreated, String description,
                           String task, int taskId) throws SQLException, ClassNotFoundException {
//        String query = "UPDATE Tasks SET datecreated=?, description=?, task=? WHERE taskid=?" ;
        String query = "UPDATE " + Const.TASKS_TABLE + " SET " + Const.TASKS_DATE + "=?, " +
                        Const.TASKS_DESCRIPTION + "=?, " + Const.TASKS_TASK + " =? " + "WHERE " +
                        Const.TASKS_ID + "=?" ;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setTimestamp(1,datecreated);
        preparedStatement.setString(2,description);
        preparedStatement.setString(3, task);
        preparedStatement.setInt(4, taskId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


}
