package oose.dea.datasource;

import oose.dea.datasource.sql.database.DefaultSqlConnector;
import oose.dea.datasource.sql.database.LoginSqlConnector;
import oose.dea.datasource.sql.database.SqlConnector;
import org.junit.Assert;
import org.junit.Test;

public class DatabaseFactoryTest {

    @Test
    public void createSqlConnectorTest(){
        DatabaseFactory databaseFactory = new DatabaseFactory();

        SqlConnector sqlConnector = databaseFactory.createSqlConnector(DatabaseSelection.MYSQL_8);
        Assert.assertTrue(sqlConnector instanceof DefaultSqlConnector);

        sqlConnector = databaseFactory.createSqlConnector(DatabaseSelection.MYSQL_8_LOGIN);
        Assert.assertTrue(sqlConnector instanceof LoginSqlConnector);

    }

}
