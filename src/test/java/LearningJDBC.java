import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

public class LearningJDBC {
    Connection connection;

    @BeforeClass
    public void init() throws SQLException {
        String url = "jdbc:mysql://test.medis.mersys.io:3306/ts_dake";
        String username = "technostudy";
        String password = "zhTPis0l9#$&";
        connection = DriverManager.getConnection(url, username, password);
    }

    @Test
    public void simpleSelectTest() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select task_id from ts_tasks;");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    @Test
    public void simpleSelectTestAbsolute() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select task_id from ts_tasks;");
        resultSet.absolute(4);
        System.out.println(resultSet.getInt("task_id"));
    }

    // handling null values in sql
    // 1) avoid using primitive data types, because they cannot represent null
    @Test
    public void simpleSelectGettingNullValueAsPrimitive() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select start_date from ts_tasks;");
        resultSet.absolute(4);
        int integer = resultSet.getInt("start_date");
        System.out.println(integer); // this should print null, but it is printing zero
        // so instead use Object or any other type other than primitive
        System.out.println(resultSet.getObject("start_date"));
    }

    // 2) using wrapper classes, because they can represent null
    @Test
    public void simpleSelectGettingNullValueAsWrappers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select start_date from ts_tasks;");
        resultSet.absolute(4);
        Integer integer = resultSet.getInt("start_date");
        if(resultSet.wasNull()) { // if the value was null
            integer = null; // set the wrapper to null
        }
        System.out.println(integer);
    }

    @AfterClass
    public  void cleanUp() throws SQLException {
        connection.close();
    }
}
