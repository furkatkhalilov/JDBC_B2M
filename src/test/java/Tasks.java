import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

public class Tasks {
    Connection connection;

    @BeforeClass
    public void init() throws SQLException {
        String url = "jdbc:mysql://test.medis.mersys.io:3306/ts_dake";
        String username = "technostudy";
        String password = "zhTPis0l9#$&";
        connection = DriverManager.getConnection(url, username, password);
    }

    @AfterClass
    public void cleanUp() throws SQLException {
        connection.close();
    }

    @Test
    public void task1() throws SQLException {
        // print out task id, title and priority from ts_tasks table using executeQuery
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select task_id , title, priority from ts_tasks;");
        while (resultSet.next()) {
            System.out.print(resultSet.getString(1)+" , ");
            System.out.print(resultSet.getString(2)+" , ");
            System.out.print(resultSet.getString(3)+" , ");
            System.out.println();
        }
    }
}
