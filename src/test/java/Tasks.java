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

    @Test
    public void task2() throws SQLException {
        // print out task id, title and status from ts_tasks where status is 1 using prepared statement
        PreparedStatement statement = connection.prepareStatement("select task_id , title, status from ts_tasks WHERE status = ?;");
        statement.setInt(1, 1);
        ResultSet resultSet = statement.executeQuery();
        System.out.println("task_id , title , status ");
        while (resultSet.next()) {
            System.out.print(resultSet.getString(1)+"   ,   ");
            System.out.print(resultSet.getString(2)+"   ,   ");
            System.out.print(resultSet.getString(3));
            System.out.println();
        }
    }

    @Test
    public void task3() throws SQLException {
        // get me the number of tasks for each status using executeQuery
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(task_id) , status from ts_tasks group by status;");
        System.out.println("Count of Task , Status");
        while (resultSet.next()) {
            System.out.print(resultSet.getString(1)+" , ");
            System.out.print(resultSet.getString(2));
            System.out.println();
        }
    }

    @Test
    public void task4() throws SQLException {
        // update titles to "tasks with startdate" and start_date to current date, where status is 0 using prepared statement
        PreparedStatement statement = connection.prepareStatement("UPDATE ts_tasks SET title = ?, start_date = ? WHERE status = ?;");
        Date date = new Date(System.currentTimeMillis()); // java Date and sql Date are two different classes

        statement.setString(1, "tasks with startdate");
        statement.setDate(2, date);
        statement.setInt(3, 0);

        statement.execute();
    }


}
