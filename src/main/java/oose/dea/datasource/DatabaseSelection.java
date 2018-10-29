package oose.dea.datasource;

public enum DatabaseSelection {
    MYSQL_8("/database.properties", DatabaseType.SQL),
    MYSQL_8_LOGIN("/database.properties", DatabaseType.SQL);

    private String properties;
    private DatabaseType databaseType;

    DatabaseSelection(String properties, DatabaseType databaseType){
        this.properties = properties;
        this.databaseType = databaseType;
    }

    public String getProperties(){
        return properties;
    }

    public DatabaseType getDatabaseType(){
        return databaseType;
    }

}
