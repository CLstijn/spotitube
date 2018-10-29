package oose.dea.datasource.sql.database;

public enum Columns {
    USER_ID("userId"), USER_NAME("userName"),USER_PASSWORD("password"),
    PLAYLIST_ID("playlistId"),PLAYLIST_NAME("name"), PLAYLIST_OWNER_ID("ownerId"),
    TRACK_ID("trackId"), TRACK_NAME("titel"), TRACK_PERFORMER("performer"), TRACK_DURATION("duration"),
    TRACK_PLAYCOUNT("playcount"), TRACK_ALBUM("album"), TRACK_PUBLICATION("publicationDate"),
    TRACK_DESCRIPTION("description"), PLAYLIST_HAS_TRACK_OFFLINE_AVAILABLE("offlineAvailable"),
    PLAYLIST_HAS_TRACK_PLAYLIST_ID("Playlist_playlistId"),PLAYLIST_HAS_TRACK_TRACK_ID("Track_trackId");

    private String column;

    Columns(String column) {
        this.column = column;
    }

    public String getColumn(){
        return column;
    }
}
