package oose.dea.domain;


import oose.dea.datasource.dto.Playlist;
import oose.dea.services.dto.PlaylistDTO;
import oose.dea.datasource.dto.Track;
import oose.dea.services.dto.TrackDTO;
import oose.dea.datasource.sql.dao.TrackDAO;
import oose.dea.services.dto.PlaylistsDTO;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.sql.dao.PlaylistDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class PlaylistHandlerTest {

    private PlaylistDAO mockedPlaylistDAO;
    private TrackDAO mockedTrackDAO;
    private PlaylistDTO playlistDTO;
    private ArrayList<Playlist> playlists;
    private ArrayList<Track> tracks;

    @Before
    public void setup(){
        mockedPlaylistDAO = Mockito.mock(PlaylistDAO.class);
        mockedTrackDAO = Mockito.mock(TrackDAO.class);

        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setDuration(200);
        ArrayList<TrackDTO> trackDTOs = new ArrayList<>();
        trackDTOs.add(trackDTO);

        Track track = new Track();
        track.setDuration(200);
        tracks = new ArrayList<>();
        tracks.add(track);

        playlistDTO = new PlaylistDTO();
        playlistDTO.setId(10);
        playlistDTO.setName("Stijn");
        playlistDTO.setOwner(false);
        playlistDTO.setTracks(trackDTOs);

        Playlist playlist = new Playlist();
        playlist.setId(10);
        playlist.setName("Stijn");
        playlist.setOwnerId("101010");
        playlists = new ArrayList<>();
        playlists.add(playlist);
    }

    @Test
    public void getPlaylistFromUserTest() throws DatabaseException {
        String token = "101010";
        PlaylistHandler playlistHandler = new PlaylistHandler(mockedPlaylistDAO, mockedTrackDAO);
        Mockito.when(mockedPlaylistDAO.getAllPlaylists()).thenReturn(playlists);

        ArrayList<Integer> playlistIds = new ArrayList<>();
        playlistIds.add(playlists.get(0).getId());
        Mockito.when(mockedTrackDAO.getTracksFromPlaylists(playlistIds)).thenReturn(tracks);

        PlaylistsDTO playlistsDTO = playlistHandler.getPlaylists(token);
        PlaylistDTO playlistDTO = playlistsDTO.getPlaylists().get(0);

        Mockito.verify(mockedPlaylistDAO, Mockito.atLeastOnce()).getAllPlaylists();
        Assert.assertEquals(playlistsDTO.getLength(), 200);
        Assert.assertEquals(playlistDTO.getName(), "Stijn");
        Assert.assertEquals(playlistDTO.getId(), 10);
        Assert.assertTrue(playlistDTO.isOwner());
    }

    @Test
    public void deletePlaylistFromUserTest() throws DatabaseException {
        String token = "101010";
        int playlistId = 101;

        PlaylistHandler playlistHandler = new PlaylistHandler(mockedPlaylistDAO, mockedTrackDAO);
        playlistHandler.deletePlaylistFromUser(token,playlistId);
        Mockito.verify(mockedPlaylistDAO).deletePlaylistFromUser(token,playlistId);
    }

    @Test
    public void addPlaylistFromUserTest() throws DatabaseException {
        String token = "101010";

        PlaylistHandler playlistHandler = new PlaylistHandler(mockedPlaylistDAO, mockedTrackDAO);
        playlistHandler.addPlaylistFromUser(token,playlistDTO);
        Mockito.verify( mockedPlaylistDAO ).addPlaylistFromUser(token,playlistDTO.getName());
    }

    @Test
    public void editPlaylistFromUserTest() throws DatabaseException {
        String token = "101010";
        int playlistId = 66;

        PlaylistHandler playlistHandler = new PlaylistHandler(mockedPlaylistDAO, mockedTrackDAO);
        playlistHandler.editPlaylistFromUser(token,playlistId,playlistDTO);
        Mockito.verify( mockedPlaylistDAO ).editPlaylistFromUser(token,playlistId,playlistDTO.getName());
    }
}
