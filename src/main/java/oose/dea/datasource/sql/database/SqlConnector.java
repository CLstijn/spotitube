package oose.dea.datasource.sql.database;

import java.sql.*;

public interface SqlConnector {
    Connection createConnection() throws SQLException, ClassNotFoundException;
    Statement createStatement(Connection databaseConnection) throws SQLException;
    ResultSet queryDatabase(Statement statement, String query) throws SQLException;
    int updateDatabase(Statement statement, String query) throws SQLException;
}
