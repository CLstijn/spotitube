package oose.dea.datasource.sql.dao;

import oose.dea.crosscutting.exceptions.DatabaseExceptionType;
import oose.dea.datasource.dto.User;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.DatabaseFactory;
import oose.dea.datasource.DatabaseSelection;
import oose.dea.datasource.sql.database.Columns;
import oose.dea.datasource.sql.database.SqlConnector;
import oose.dea.datasource.sql.database.Queries;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDAO {

    private SqlConnector sqlConnector;
    private Queries queries;

    @Inject
    public UserDAO(DatabaseFactory databaseFactory, Queries queries) {
        this.sqlConnector = databaseFactory.createSqlConnector(DatabaseSelection.MYSQL_8);
        this.queries = queries;
    }

    public User getUserData(String user, String password) throws DatabaseException {
        String query = queries.getUser(user, password);
        try (Connection connection = sqlConnector.createConnection();
             Statement statement = sqlConnector.createStatement(connection);
             ResultSet resultSet = sqlConnector.queryDatabase(statement, query)) {
            if (resultSet.next()) {
                 return createUserDTO(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
        throw new DatabaseException(DatabaseExceptionType.USER_NOT_FOUND);
    }

    private User createUserDTO(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setUser(resultSet.getString(Columns.USER_NAME.getColumn()));
        user.setPassWord(resultSet.getString(Columns.USER_PASSWORD.getColumn()));
        user.setUserId(resultSet.getInt(Columns.USER_ID.getColumn()));
        return user;
    }
}