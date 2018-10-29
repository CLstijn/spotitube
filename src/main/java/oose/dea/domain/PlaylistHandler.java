package oose.dea.domain;

import oose.dea.datasource.dto.Playlist;
import oose.dea.services.dto.PlaylistDTO;
import oose.dea.datasource.dto.Track;
import oose.dea.datasource.sql.dao.TrackDAO;
import oose.dea.services.dto.PlaylistsDTO;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.sql.dao.PlaylistDAO;

import javax.inject.Inject;
import java.util.ArrayList;


public class PlaylistHandler {

    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;


    @Inject
    public PlaylistHandler(PlaylistDAO playlistDAO, TrackDAO trackDAO){
        this.playlistDAO = playlistDAO;
        this.trackDAO = trackDAO;
    }

    public PlaylistsDTO getPlaylists(String token) throws DatabaseException {
        ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists();
        ArrayList<Track> tracks = trackDAO.getTracksFromPlaylists(getIdsFromPlaylists(playlists));
        return createPlaylistsDTO(token, playlists, tracks);
    }

    public PlaylistsDTO deletePlaylistFromUser(String token, int playlistId) throws DatabaseException {
        playlistDAO.deletePlaylistFromUser(token,playlistId);
        return getPlaylists(token);
    }

    public PlaylistsDTO addPlaylistFromUser(String token, PlaylistDTO playlist) throws DatabaseException {
        playlistDAO.addPlaylistFromUser(token,playlist.getName());
        return getPlaylists(token);
    }

    public PlaylistsDTO editPlaylistFromUser(String token, int playlistId, PlaylistDTO playlist) throws DatabaseException {
        playlistDAO.editPlaylistFromUser(token, playlistId, playlist.getName());
        return getPlaylists(token);
    }

    private PlaylistsDTO createPlaylistsDTO(String token, ArrayList<Playlist> playlists, ArrayList<Track> tracks) {
        ArrayList<PlaylistDTO> playlistDTOs = new ArrayList<>();
        for(Playlist playlist : playlists) {
            playlistDTOs.add(createPlaylistDTO(token, playlist));
        }
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.setPlaylists(playlistDTOs);
        playlistsDTO.setLength(determinePlaylistsTotalLength(tracks));
        return playlistsDTO;
    }

    private PlaylistDTO createPlaylistDTO(String token, Playlist playlist){
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(playlist.getId());
        playlistDTO.setName(playlist.getName());
        setPlaylistOwner(playlist.getOwnerId(), playlistDTO, token);
        return playlistDTO;
    }

    private ArrayList<Integer> getIdsFromPlaylists(ArrayList<Playlist> playlists) {
        ArrayList<Integer> playlistIds = new ArrayList<>();
        for(Playlist playlist: playlists) {
            playlistIds.add(playlist.getId());
        }
        return playlistIds;
    }

    private int determinePlaylistsTotalLength(ArrayList<Track> tracks) {
        int playlistLength = 0;
        for (Track track : tracks){
            playlistLength += track.getDuration();
        }
        return playlistLength;
    }

    private void setPlaylistOwner(String ownerId, PlaylistDTO playlist, String token) {
        if(ownerId.equals(token)){
            playlist.setOwner(true);
        }else {
            playlist.setOwner(false);
        }
    }
}
