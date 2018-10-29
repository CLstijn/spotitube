package oose.dea.datasource.sql.database;

import java.sql.*;
import java.util.Properties;

public class DefaultSqlConnector implements SqlConnector {

    private Properties databaseProperties;

    public DefaultSqlConnector(Properties databaseProperties){
        this.databaseProperties = databaseProperties;
    }

    public Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(databaseProperties.getProperty("driver"));
        return DriverManager.getConnection(
                databaseProperties.getProperty("connection"),
                databaseProperties.getProperty("username"),
                databaseProperties.getProperty("password"));
    }

    public Statement createStatement(Connection databaseConnection) throws SQLException {
        return databaseConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    }

    public ResultSet queryDatabase(Statement statement, String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public int updateDatabase(Statement statement, String query) throws SQLException {
        return statement.executeUpdate(query);
    }
}
