package oose.dea.datasource.sql.dao;

import oose.dea.datasource.dto.Track;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.DatabaseFactory;
import oose.dea.datasource.DatabaseSelection;
import oose.dea.datasource.sql.database.Columns;
import oose.dea.datasource.sql.database.SqlConnector;
import oose.dea.datasource.sql.database.Queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;


public class TrackDAO {

    private SqlConnector sqlConnector;
    private Queries queries;

    public TrackDAO()  {
        this.sqlConnector = new DatabaseFactory().createSqlConnector(DatabaseSelection.MYSQL_8_LOGIN);
        this.queries = new Queries();
    }

    public ArrayList<Track> getTracksOutsidePlaylist(int playlistId) throws DatabaseException {
        try {
            ArrayList<Track> tracksFromPlaylist = getTracksFromPlaylist(playlistId);
            HashSet<Integer> tracksFromPlaylistIds = getIdsFromTracks(tracksFromPlaylist);

            ArrayList<Track> tracks = new ArrayList<>();
            try (Connection connection = sqlConnector.createConnection();
                 Statement statement = sqlConnector.createStatement(connection);
                 ResultSet resultSet = sqlConnector.queryDatabase(statement, queries.getAllTracks())) {
                while (resultSet.next()) {
                    if (!tracksFromPlaylistIds.contains(resultSet.getInt(Columns.TRACK_ID.getColumn()))) {
                        tracks.add(createTrack(resultSet));
                    }
                }
                return tracks;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public ArrayList<Track> getTracksFromPlaylists(ArrayList<Integer> playlistIds) throws DatabaseException {
        try {
            ArrayList<Track> tracks = new ArrayList<>();
            try (Connection connection = sqlConnector.createConnection();
                 Statement statement = sqlConnector.createStatement(connection);
                 ResultSet resultSet = sqlConnector.queryDatabase(statement, queries.getTracksFromPlaylists(playlistIds))) {
                while (resultSet.next()) {
                    Track track = createTrack(resultSet);
                    tracks.add(setPlaylistTrackSettings(resultSet, track));
                }
            }
            return tracks;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public ArrayList<Track> getTracksFromPlaylist(int playlistId) throws DatabaseException {
        try {
            ArrayList<Track> tracks = new ArrayList<>();
            try (Connection connection = sqlConnector.createConnection();
                 Statement statement = sqlConnector.createStatement(connection);
                 ResultSet resultSet = sqlConnector.queryDatabase(statement, queries.getTrackFromPlaylist(playlistId))) {
                while (resultSet.next()) {
                    Track track = createTrack(resultSet);
                    tracks.add(setPlaylistTrackSettings(resultSet, track));
                }
            }
            return tracks;
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void addTrackToPlaylistFromUser(String userId, int playlistId, int trackId, boolean offlineAvailable) throws DatabaseException {
        try {
            if (playlistBelongsToUser(userId, playlistId)) {
                try(Connection connection = sqlConnector.createConnection();
                    Statement statement = sqlConnector.createStatement(connection)) {
                    sqlConnector.updateDatabase(statement, queries.addTrackToPlaylist(trackId, playlistId, offlineAvailable));
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void deleteTrackFromPlaylistFromUser(String userId, int playlistId, int trackId) throws DatabaseException {
        try{
            if(playlistBelongsToUser(userId,playlistId)) {
                try(Connection connection = sqlConnector.createConnection();
                    Statement statement = sqlConnector.createStatement(connection)) {
                    sqlConnector.updateDatabase(statement, queries.deleteTrackFromPlaylist(trackId, playlistId));
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    private HashSet<Integer> getIdsFromTracks(ArrayList<Track> tracksFromPlaylist) {
        HashSet<Integer> tracksFromPlaylistIds = new HashSet<>();
        for (Track track : tracksFromPlaylist) {
            tracksFromPlaylistIds.add(track.getId());
        }
        return tracksFromPlaylistIds;
    }

    private boolean playlistBelongsToUser(String userId, int playlistId) throws SQLException, ClassNotFoundException {
        try(Connection connection = sqlConnector.createConnection();
            Statement statement = sqlConnector.createStatement(connection);
            ResultSet resultSet = sqlConnector.queryDatabase(statement, queries.getPlaylistFromUser(userId,playlistId))){
            return resultSet.isBeforeFirst();
        }
    }

    private Track createTrack(ResultSet resultSet) throws SQLException {
        Track track = new Track();
        track.setId(resultSet.getInt(Columns.TRACK_ID.getColumn()));
        track.setTitle(resultSet.getString(Columns.TRACK_NAME.getColumn()));
        track.setPerformer(resultSet.getString(Columns.TRACK_PERFORMER.getColumn()));
        track.setPlaycount(resultSet.getInt(Columns.TRACK_PLAYCOUNT.getColumn()));
        track.setDuration(resultSet.getInt(Columns.TRACK_DURATION.getColumn()));
        track.setDescription(resultSet.getString(Columns.TRACK_DESCRIPTION.getColumn()));
        track.setPublicationDate(resultSet.getString(Columns.TRACK_PUBLICATION.getColumn()));
        track.setAlbum(resultSet.getString(Columns.TRACK_ALBUM.getColumn()));
        return track;
    }

    private Track setPlaylistTrackSettings(ResultSet resultSet, Track track) throws SQLException {
        track.setOfflineAvailable(resultSet.getBoolean(Columns.PLAYLIST_HAS_TRACK_OFFLINE_AVAILABLE.getColumn()));
        return track;
    }
}
