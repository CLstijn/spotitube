package oose.dea.domain;

import oose.dea.datasource.dto.Track;
import oose.dea.services.dto.TrackDTO;
import oose.dea.services.dto.TracksDTO;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.sql.dao.TrackDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TrackHandler {

    private TrackDAO trackDAO;

    @Inject
    public TrackHandler(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }

    public TracksDTO getTracksOutsidePlaylist(int playlistId) throws DatabaseException {
        return createTracksDTO(trackDAO.getTracksOutsidePlaylist(playlistId));
    }

    public TracksDTO getTracksFromPlaylist(int playlistId) throws DatabaseException {
        return createTracksDTO(trackDAO.getTracksFromPlaylist(playlistId));
    }

    public TracksDTO deleteTrackFromPlaylist(String token, int playlistId, int trackId) throws DatabaseException {
        trackDAO.deleteTrackFromPlaylistFromUser(token, playlistId, trackId);
        return getTracksFromPlaylist(playlistId);
    }

    public TracksDTO addTrackToPlaylist(String token, int playlistId, TrackDTO trackDTO) throws DatabaseException {
        Track track = createTrack(trackDTO);
        setTrackPublicationDate(track);
        trackDAO.addTrackToPlaylistFromUser(token, playlistId, trackDTO.getId(), trackDTO.isOfflineAvailable());
        return getTracksFromPlaylist(playlistId);
    }

    private Track createTrack(TrackDTO trackDTO) {
        Track track = new Track();
        track.setDuration(trackDTO.getDuration());
        track.setAlbum(trackDTO.getAlbum());
        track.setId(trackDTO.getId());
        track.setOfflineAvailable(trackDTO.isOfflineAvailable());
        track.setPerformer(trackDTO.getPerformer());
        track.setPlaycount(trackDTO.getPlaycount());
        track.setPublicationDate(trackDTO.getPublicationDate());
        track.setDescription(trackDTO.getDescription());
        track.setTitle(trackDTO.getTitle());
        return track;
    }

    private TracksDTO createTracksDTO(ArrayList<Track> tracks) {
        ArrayList<TrackDTO> trackDTOs = new ArrayList<>();
        for(Track track : tracks){
            trackDTOs.add(createTrackDTO(track));
        }
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.setTracks(trackDTOs);
        return tracksDTO;
    }

    private TrackDTO createTrackDTO(Track track) {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setDuration(track.getDuration());
        trackDTO.setAlbum(track.getAlbum());
        trackDTO.setId(track.getId());
        trackDTO.setOfflineAvailable(track.isOfflineAvailable());
        trackDTO.setPerformer(track.getPerformer());
        trackDTO.setPlaycount(track.getPlaycount());
        trackDTO.setPublicationDate(track.getPublicationDate());
        trackDTO.setDescription(track.getDescription());
        trackDTO.setTitle(track.getTitle());
        return trackDTO;
    }

    private void setTrackPublicationDate(Track track) {
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        track.setPublicationDate(date.toString());
    }
}
