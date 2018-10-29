package oose.dea.datasource.sql.dao;

import oose.dea.crosscutting.exceptions.DatabaseExceptionType;
import oose.dea.datasource.dto.Playlist;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.DatabaseFactory;
import oose.dea.datasource.DatabaseSelection;
import oose.dea.datasource.sql.database.Columns;
import oose.dea.datasource.sql.database.SqlConnector;
import oose.dea.datasource.sql.database.Queries;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class PlaylistDAO {

    private SqlConnector sqlConnector;
    private Queries queries;

    @Inject
    public PlaylistDAO(Queries queries, DatabaseFactory databaseFactory)  {
        this.sqlConnector = databaseFactory.createSqlConnector(DatabaseSelection.MYSQL_8_LOGIN);
        this.queries = queries;
    }

    public ArrayList<Playlist> getAllPlaylists() throws DatabaseException {
        try(Connection connection = sqlConnector.createConnection();
            Statement statement = sqlConnector.createStatement(connection)){

            HashMap<String, Playlist> playlists = new HashMap<>();
            try (ResultSet resultSet = sqlConnector.queryDatabase(statement, queries.getEmptyPlaylists())) {
                while (resultSet.next()) {
                    String playlistId = resultSet.getString(Columns.PLAYLIST_ID.getColumn());
                    Playlist playlist = createPlaylist(resultSet);
                    playlists.put(playlistId, playlist);
                }
            }
            try (ResultSet resultSet = sqlConnector.queryDatabase(statement, queries.getPlaylistsWithTracks())) {
                while (resultSet.next()) {
                    String playlistId = resultSet.getString(Columns.PLAYLIST_ID.getColumn());
                    if (!playlists.keySet().contains(playlistId)) {
                        Playlist playlist = createPlaylist(resultSet);
                        playlists.put(playlistId, playlist);
                    }
                }
            }
            return new ArrayList<>(playlists.values());

        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void deletePlaylistFromUser(String userId, int playlistId) throws DatabaseException {
        try(Connection connection = sqlConnector.createConnection();
            Statement statement = sqlConnector.createStatement(connection)){

            int status = sqlConnector.updateDatabase(statement, queries.deletePlaylistWithTracksFromUser(userId, playlistId));
            if (status == 0) {
                status = sqlConnector.updateDatabase(statement, queries.deleteEmptyPlaylistFromUser(userId, playlistId));
            }
            if (status == 0) {
                throw new DatabaseException(DatabaseExceptionType.PLAYLIST_NOT_FROM_USER);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void addPlaylistFromUser(String userId, String playlistName) throws DatabaseException {
        try (Connection connection = sqlConnector.createConnection();
             Statement statement = sqlConnector.createStatement(connection)){

            int status = sqlConnector.updateDatabase(statement, queries.addPlaylistFromUser(userId, playlistName));
            if (status == 0) {
                throw new DatabaseException(DatabaseExceptionType.PLAYLIST_NOT_FROM_USER);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void editPlaylistFromUser(String userId, int playlistId, String playlistName) throws DatabaseException {
        try (Connection connection = sqlConnector.createConnection();
             Statement statement = sqlConnector.createStatement(connection)){

            int status = sqlConnector.updateDatabase(statement, queries.editPlaylistFromUser(userId, playlistId, playlistName));
            if(status == 0) {
                throw new DatabaseException(DatabaseExceptionType.PLAYLIST_NOT_FROM_USER);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    private Playlist createPlaylist(ResultSet resultSet) throws SQLException {
        Playlist playlist = new Playlist();
        playlist.setId(resultSet.getInt(Columns.PLAYLIST_ID.getColumn()));
        playlist.setName(resultSet.getString(Columns.PLAYLIST_NAME.getColumn()));
        playlist.setOwnerId(resultSet.getString(Columns.PLAYLIST_OWNER_ID.getColumn()));
        return playlist;
    }
}
