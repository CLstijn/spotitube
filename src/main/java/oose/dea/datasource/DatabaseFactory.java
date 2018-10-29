package oose.dea.datasource;

import oose.dea.datasource.sql.database.DefaultSqlConnector;
import oose.dea.datasource.sql.database.SqlConnector;
import oose.dea.datasource.sql.database.LoginSqlConnector;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class DatabaseFactory {


    public SqlConnector createSqlConnector(DatabaseSelection databaseSelection){

        switch (databaseSelection) {
            case MYSQL_8:
                return new DefaultSqlConnector(getDatabaseProperties(databaseSelection.getProperties()));
            case MYSQL_8_LOGIN:
                return new LoginSqlConnector(getDatabaseProperties(databaseSelection.getProperties()));
        }
        return null;
    }

    private Properties getDatabaseProperties(String properties)  {

        Properties databaseProperties = new Properties();
        try (BufferedReader propertiesFile = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(properties)))) {
            databaseProperties.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return databaseProperties;
    }
}
