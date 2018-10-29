package oose.dea.crosscutting.exceptions;

public enum DatabaseExceptionType {
    DEFAULT("Error occurred during database interaction!", 500),
    USER_NOT_FOUND("User not found: UserId or password is incorrect!", 401),
    PLAYLIST_NOT_FROM_USER("PlaylistId and userId don't match any results!", 400);

    private String exceptionMessage;
    private int httpStatus;

    DatabaseExceptionType(String exceptionMessage, int httpStatus){
        this.exceptionMessage = exceptionMessage;
        this.httpStatus = httpStatus;
    }

    public String getMessage(){
        return exceptionMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
