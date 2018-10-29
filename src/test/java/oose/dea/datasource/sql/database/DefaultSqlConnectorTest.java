package oose.dea.datasource.sql.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Properties;

public class DefaultSqlConnectorTest {

    private SqlConnector defaultSqlConnector;

    @Before
    public void setup() {
        Properties properties = Mockito.mock(Properties.class);
        Mockito.when(properties.getProperty(Mockito.anyString())).thenReturn("");
        defaultSqlConnector = new DefaultSqlConnector(properties);
    }

    @Test
    public void queryDatabaseTest() throws SQLException {
        Statement mockedStatement = Mockito.mock(Statement.class);
        ResultSet mockedResultSet = Mockito.mock(ResultSet.class);

        Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
        ResultSet resultSet = defaultSqlConnector.queryDatabase(mockedStatement,Mockito.anyString());
        Assert.assertNotNull(resultSet);
    }

    @Test
    public void updateDatabaseTest() throws SQLException {
        Statement mockedStatement = Mockito.mock(Statement.class);
        int expectedStatus = 0;

        Mockito.when(mockedStatement.executeUpdate(Mockito.anyString())).thenReturn(expectedStatus);
        int status = defaultSqlConnector.updateDatabase(mockedStatement,Mockito.anyString());
        Assert.assertEquals(expectedStatus, status);
    }

}

