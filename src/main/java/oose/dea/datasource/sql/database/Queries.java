package oose.dea.datasource.sql.database;

import java.util.ArrayList;


public class Queries {

    public String getUser(String user, String password) {
        return String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'",
                Tables.USER.table(),
                Columns.USER_NAME.getColumn(), user,
                Columns.USER_PASSWORD.getColumn(), password);
    }

    public String getEmptyPlaylists(){
        return String.format("SELECT * " +
                        "FROM %s ;",
                Tables.PLAYLIST.table());
    }

    public String getPlaylistsWithTracks() {
        return String.format("SELECT * " +
                        "FROM %s , %s , %s " +
                        "WHERE %s = %s " +
                        "AND %s = %s ;",
                Tables.PLAYLIST.table(),
                Tables.PLAYLIST_HAS_TRACKS.table(),
                Tables.TRACK.table(),
                Columns.PLAYLIST_HAS_TRACK_PLAYLIST_ID.getColumn(), Columns.PLAYLIST_ID.getColumn(),
                Columns.PLAYLIST_HAS_TRACK_TRACK_ID.getColumn(), Columns.TRACK_ID.getColumn());

    }

    public String getPlaylistFromUser(String userId, int playlistId){
        return String.format("SELECT * " +
                        "FROM %s " +
                        "WHERE %s = %s " +
                        "AND %s = %s ;",
                Tables.PLAYLIST.table(),
                Columns.PLAYLIST_OWNER_ID.getColumn(), userId,
                Columns.PLAYLIST_ID.getColumn(), playlistId);
    }

    public String deletePlaylistWithTracksFromUser(String userId, int playlistId) {
        return String.format("DELETE %s , %s FROM %s " +
                        "JOIN %s ON %s = %s " +
                        "WHERE %s = %s " +
                        "AND %s = %s ;",
                Tables.PLAYLIST.table(), Tables.PLAYLIST_HAS_TRACKS.table(), Tables.PLAYLIST.table(),
                Tables.PLAYLIST_HAS_TRACKS.table(), Columns.PLAYLIST_HAS_TRACK_PLAYLIST_ID.getColumn(), Columns.PLAYLIST_ID.getColumn(),
                Columns.PLAYLIST_OWNER_ID.getColumn(), userId,
                Columns.PLAYLIST_ID.getColumn(), playlistId);
    }

    public String deleteEmptyPlaylistFromUser(String userId, int playlistId){
        return String.format("DELETE FROM %s " +
                        "WHERE %s = %s " +
                        "AND %s = %s ;",
                Tables.PLAYLIST.table(),
                Columns.PLAYLIST_OWNER_ID.getColumn(), userId,
                Columns.PLAYLIST_ID.getColumn(), playlistId);
    }

    public String addPlaylistFromUser(String ownerId, String playlistName) {
        return String.format("INSERT INTO %s (%s , %s) " +
                        "VALUES ('%s' , %s );",
                Tables.PLAYLIST.table(),
                Columns.PLAYLIST_NAME.getColumn(), Columns.PLAYLIST_OWNER_ID.getColumn(),
                playlistName, ownerId);
    }

    public String editPlaylistFromUser(String ownerId, int playlistId, String playlistName){
        return String.format("UPDATE %s " +
                        "SET %s = '%s' " +
                        "WHERE %s = %s " +
                        "AND %s = %s",
                Tables.PLAYLIST.table(),
                Columns.PLAYLIST_NAME.getColumn(), playlistName,
                Columns.PLAYLIST_ID.getColumn(), playlistId,
                Columns.PLAYLIST_OWNER_ID.getColumn(), ownerId);
    }

    public String getAllTracks(){
        return String.format("SELECT * " +
                        "FROM %s ",
                Tables.TRACK.table());
    }

    public String getTrackFromPlaylist(int playlistId){
        return String.format("SELECT * " +
                        "FROM %s , %s " +
                        "WHERE %s = %s " +
                        "AND %s = %s ;",
                Tables.TRACK.table(),
                Tables.PLAYLIST_HAS_TRACKS.table(),
                Columns.PLAYLIST_HAS_TRACK_TRACK_ID.getColumn(), Columns.TRACK_ID.getColumn(),
                Columns.PLAYLIST_HAS_TRACK_PLAYLIST_ID.getColumn(), playlistId);
    }

    public String getTracksFromPlaylists(ArrayList<Integer> playlistIds) {
        return String.format("SELECT * " +
                        "FROM %s , %s " +
                        "WHERE %s IN %s " +
                        "AND %s = %s ;",
                Tables.TRACK.table(),
                Tables.PLAYLIST_HAS_TRACKS.table(),
                Columns.PLAYLIST_HAS_TRACK_PLAYLIST_ID.getColumn(), playlistIds,
                Columns.PLAYLIST_HAS_TRACK_TRACK_ID.getColumn(), Columns.TRACK_ID.getColumn())
                .replace("[","(").replace("]",")");
    }

    public String deleteTrackFromPlaylist(int trackId, int playlistId){
        return String.format("DELETE %s " +
                        "FROM %s , %s " +
                        "WHERE %s = %s " +
                        "AND %s = %s " +
                        "AND %s = %s ;",
                Tables.PLAYLIST_HAS_TRACKS.table(),
                Tables.PLAYLIST_HAS_TRACKS.table(), Tables.TRACK.table(),
                Columns.PLAYLIST_HAS_TRACK_TRACK_ID.getColumn(), Columns.TRACK_ID.getColumn(),
                Columns.PLAYLIST_HAS_TRACK_PLAYLIST_ID.getColumn(), playlistId,
                Columns.TRACK_ID.getColumn() , trackId);
    }

    public String addTrackToPlaylist(int trackId, int playlistId, boolean offlineAvailable){
        return String.format("INSERT INTO %s (%s , %s, %s) " +
                        "VALUES (%s , %s, %s);",
                Tables.PLAYLIST_HAS_TRACKS.table(),
                Columns.PLAYLIST_HAS_TRACK_PLAYLIST_ID.getColumn(), Columns.PLAYLIST_HAS_TRACK_TRACK_ID.getColumn(),
                Columns.PLAYLIST_HAS_TRACK_OFFLINE_AVAILABLE.getColumn(),
                playlistId, trackId, offlineAvailable);
    }


}