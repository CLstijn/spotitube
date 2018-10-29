package oose.dea.datasource.sql.database;

public enum Tables {
    USER("user"), PLAYLIST("playlist"),TRACK("track"),PLAYLIST_HAS_TRACKS("playlist_has_track");

    private String table;

    Tables(String table) {
        this.table = table;
    }

    public String table(){
        return table;
    }
}
