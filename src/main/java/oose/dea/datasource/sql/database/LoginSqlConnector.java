package oose.dea.datasource.sql.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LoginSqlConnector implements SqlConnector {

    private DefaultSqlConnector defaultSqlConnector;

    public LoginSqlConnector(Properties properties) {
        defaultSqlConnector = new DefaultSqlConnector(properties);
    }

    public Connection createConnection() throws SQLException, ClassNotFoundException {
        return defaultSqlConnector.createConnection();
    }

    public Statement createStatement(Connection databaseConnection) throws SQLException{
        return defaultSqlConnector.createStatement(databaseConnection);
    }

    public ResultSet queryDatabase(Statement statement, String query) throws SQLException {
        return defaultSqlConnector.queryDatabase(statement,query);
    }

    public int updateDatabase(Statement statement, String query) throws SQLException {
        return defaultSqlConnector.updateDatabase(statement,query);
    }
}
