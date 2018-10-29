package oose.dea.domain;

import oose.dea.datasource.dto.Track;
import oose.dea.services.dto.TrackDTO;
import oose.dea.services.dto.TracksDTO;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.sql.dao.TrackDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class TrackHandlerTest {

    private TrackDAO mockedTrackDAO;
    private TrackDTO trackDTO;
    private ArrayList<Track> tracks;

    @Before
    public void setup(){
        mockedTrackDAO = Mockito.mock(TrackDAO.class);

        trackDTO = new TrackDTO();
        trackDTO.setId(70);
        trackDTO.setAlbum("Hell yeah");
        trackDTO.setPlaycount(22);
        trackDTO.setPerformer("Singer");
        trackDTO.setTitle("Title");
        trackDTO.setOfflineAvailable(false);

        Track track = new Track();
        track.setId(70);
        track.setAlbum("Hell yeah");
        track.setPlaycount(22);
        track.setPerformer("Singer");
        track.setTitle("Title");
        track.setOfflineAvailable(false);

        tracks = new ArrayList<>();
        tracks.add(track);
    }

    @Test
    public void getTracksOutsidePlaylistTest() throws DatabaseException {
        int playlistId = 101;
        Mockito.when(mockedTrackDAO.getTracksOutsidePlaylist(playlistId)).thenReturn(tracks);

        TrackHandler trackHandler = new TrackHandler(mockedTrackDAO);
        TracksDTO returnedTracksDTO = trackHandler.getTracksOutsidePlaylist(playlistId);
        TrackDTO returnedTrackDTO = returnedTracksDTO.getTracks().get(0);

        Mockito.verify(mockedTrackDAO, Mockito.atLeastOnce()).getTracksOutsidePlaylist(playlistId);
        testTracksDTO(returnedTrackDTO);
    }

    @Test
    public void getTracksFromPlaylist() throws DatabaseException {
        int playlistId = 101;
        Mockito.when(mockedTrackDAO.getTracksFromPlaylist(playlistId)).thenReturn(tracks);

        TrackHandler trackHandler = new TrackHandler(mockedTrackDAO);
        TracksDTO returnedTracksDTO = trackHandler.getTracksFromPlaylist(playlistId);
        TrackDTO returnedTrackDTO = returnedTracksDTO.getTracks().get(0);

        Mockito.verify(mockedTrackDAO, Mockito.atLeastOnce()).getTracksFromPlaylist(playlistId);
        testTracksDTO(returnedTrackDTO);
    }

    @Test
    public void addTrackToPlaylistTest() throws DatabaseException {
        String token = "101010";
        int playlistId = 101;
        Mockito.when(mockedTrackDAO.getTracksFromPlaylist(playlistId)).thenReturn(tracks);

        TrackHandler trackHandler = new TrackHandler(mockedTrackDAO);
        TracksDTO returnedTracksDTO = trackHandler.addTrackToPlaylist(token, playlistId, trackDTO);
        TrackDTO returnedTrackDTO = returnedTracksDTO.getTracks().get(0);

        Mockito.verify(mockedTrackDAO, Mockito.atLeastOnce()).addTrackToPlaylistFromUser(token, playlistId, trackDTO.getId(),trackDTO.isOfflineAvailable());
        Mockito.verify(mockedTrackDAO, Mockito.atLeastOnce()).getTracksFromPlaylist(playlistId);
        testTracksDTO(returnedTrackDTO);
    }

    @Test
    public void deleteTrackFromPlaylist() throws DatabaseException {
        String token = "101010";
        int trackId = 202;
        int playlistId = 101;
        Mockito.when(mockedTrackDAO.getTracksFromPlaylist(playlistId)).thenReturn(tracks);

        TrackHandler trackHandler = new TrackHandler(mockedTrackDAO);
        TracksDTO returnedTracksDTO = trackHandler.deleteTrackFromPlaylist(token, playlistId, trackId);
        TrackDTO returnedTrackDTO = returnedTracksDTO.getTracks().get(0);

        Mockito.verify(mockedTrackDAO, Mockito.atLeastOnce()).deleteTrackFromPlaylistFromUser(token, playlistId, trackId);
        Mockito.verify(mockedTrackDAO, Mockito.atLeastOnce()).getTracksFromPlaylist(playlistId);
        testTracksDTO(returnedTrackDTO);
    }

    private void testTracksDTO(TrackDTO returnedTrackDTO) {
        Assert.assertEquals(returnedTrackDTO.getId(), 70);
        Assert.assertEquals(returnedTrackDTO.getAlbum(), "Hell yeah");
        Assert.assertEquals(returnedTrackDTO.getPlaycount(), 22);
        Assert.assertEquals(returnedTrackDTO.getPerformer(), "Singer");
        Assert.assertEquals(returnedTrackDTO.getTitle(), "Title");
        Assert.assertFalse(returnedTrackDTO.isOfflineAvailable());
    }
}
