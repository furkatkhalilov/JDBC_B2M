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
            System.out.println(resultSet.getString("task_id"));
        }
    }

    @AfterClass
    public  void cleanUp() throws SQLException {
        connection.close();
    }
}
