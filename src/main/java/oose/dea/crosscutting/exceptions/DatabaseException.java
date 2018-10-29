package oose.dea.crosscutting.exceptions;

public class DatabaseException extends Exception {

    private DatabaseExceptionType databaseExceptionType;

    public DatabaseException(){
        super(DatabaseExceptionType.DEFAULT.getMessage());
        this.databaseExceptionType = DatabaseExceptionType.DEFAULT;
    }

    public DatabaseException(DatabaseExceptionType databaseExceptionType){
        super(databaseExceptionType.getMessage());
        this.databaseExceptionType = databaseExceptionType;}

    public int getStatusCode(){
        return databaseExceptionType.getHttpStatus();
    }

}

