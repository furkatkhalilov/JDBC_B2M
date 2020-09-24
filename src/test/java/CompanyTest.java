import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class CompanyTest {
    Connection connection;
    private Statement statement;

    @BeforeClass
    public void init() throws SQLException {
        String url = "jdbc:mysql://test.medis.mersys.io:33306/company";
        String username = "technostudy";
        String password = "zhTPis0l9#$&";
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

    @Test
    public void printAllCountries() throws SQLException {
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
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM company.countries where COUNTRY_NAME = 'Egypt';" );
        resultSet.absolute( 1 );
        int region_id = resultSet.getInt( "REGION_ID" );
        Assert.assertEquals( region_id,4 );
    }

    //Task3: create a test that tests that Singapore is in Asia
    @Test
    public void Task3() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM countries join regions ON countries.REGION_ID=regions.REGION_ID where COUNTRY_NAME like 'Singapore';");
        rs.first();
        Assert.assertEquals(rs.getString("REGION_NAME").trim(), "Asia");
    }

    /*  Task4: create a test that checks that : (hint: use dataprovider)
	- region 1 has 8 countries
	- region 2 has 5 countries
	- region 3 has 6 countries
	- region 4 has 6 countries
    */
    @Test(dataProvider = "testCountries")
    public void Task4(int regionId, int numberOfCountries) throws SQLException {
        ResultSet rs = statement.executeQuery("select count(country_name) as total from company.countries where region_id = "+ regionId +";");
        rs.first();
        int totalNumberOfCountries = rs.getInt("total");
        Assert.assertEquals(totalNumberOfCountries, numberOfCountries);
    }

    @DataProvider(name = "testCountries")
    public Object[][] testCountriesData() {
        Object[][] data = {{1,8}, {2,5}, {3,6}, {4,6}}; // {regionId, expectedCountriesCount }
        return data;
    }

    @Test(dataProvider = "countryDataProvider")
    public void Task4Alternative(int regionId, int numberOfCountries) throws SQLException {
        Map<Integer, Integer> countryMap = new HashMap<>();
        countryMap.put(1,8);
        countryMap.put(2,5);
        countryMap.put(3,6);
        countryMap.put(4,6);
        Assert.assertEquals(countryMap.get(regionId).intValue(), numberOfCountries);
    }

    @DataProvider(name = "countryDataProvider")
    public Object[][] data() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT REGION_ID, count(*) AS count FROM countries group by REGION_ID;");
        resultSet.last(); // point the cursor to the last row
        int numberOfRows = resultSet.getRow(); // get the number of rows
        resultSet.beforeFirst(); // point the cursor back to the beginning
        Object[][] object = new Object[numberOfRows][2];
        while (resultSet.next()) {
            for (int i = 0; i < numberOfRows; i++) {
                object[i][0]= resultSet.getString("REGION_ID");
                object[i][1]= resultSet.getString("COUNT");
            }
        }
        return object;
    }

    // Task5: create a test that checks that every employee gets salary within range of their jobs min and max salary

    @AfterClass
    public void cleanUp() throws SQLException {
        connection.close();
    }
}
