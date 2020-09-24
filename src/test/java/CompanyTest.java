import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;


public class CompanyTest {
    Connection connection;

    @BeforeClass
    public void init() throws SQLException {
        String url = "jdbc:mysql://test.medis.mersys.io:33306/company";
        String username = "technostudy";
        String password = "zhTPis0l9#$&";
        connection = DriverManager.getConnection(url, username, password);
    }

    @Test
    public void printAllCountries() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from countries;");
        while (resultSet.next()) {
            System.out.print(resultSet.getString("COUNTRY_ID") + "\t");
            System.out.print(resultSet.getString("COUNTRY_NAME") + "\t\t");
            System.out.print(resultSet.getString("REGION_ID") + "\t");
            System.out.println();
        }
    }
    // Task2: create a test that tests that Egypt is in region 4+
    // solution 1
    @Test
    public void egyptInRegion4() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from countries;"); // selecting all the countries, not optimized
        while (resultSet.next()) {
            // if there's no Egypt this test will never fail, but it should
            if(resultSet.getString("COUNTRY_NAME") =="Egypt"){
                Assert.assertEquals(resultSet.getString("REGION_ID"),4);
            }
        }
    }
    // solution 2
    @Test
    public void simpleSelectGettingNullValuesAsWrappers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM company.countries where COUNTRY_NAME = 'Egypt';" );
        resultSet.absolute( 1 );
        int region_id = resultSet.getInt( "REGION_ID" );
        Assert.assertEquals( region_id,4 );
    }

    @AfterClass
    public void cleanUp() throws SQLException {
        connection.close();
    }
}
