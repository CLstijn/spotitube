package oose.dea.services;

import oose.dea.services.dto.TrackDTO;
import oose.dea.services.dto.TracksDTO;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.domain.TrackHandler;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//TODO this class should not have a path?
@Path("/")
public class TrackService {

    @Inject
    private TrackHandler trackHandler;

    @QueryParam("token") String token;

    @GET
    @Path("/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksOutsidePlaylist(@QueryParam("forPlaylist") int playlistId){
        try {
            TracksDTO tracks = trackHandler.getTracksOutsidePlaylist(playlistId);
            return Response.ok(tracks).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }

    @GET
    @Path("/playlists/{playlistId}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@PathParam("playlistId") int playlistId){
        try {
        TracksDTO tracks = trackHandler.getTracksFromPlaylist(playlistId);
        return Response.ok(tracks).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }

    @DELETE
    @Path("/playlists/{playlistId}/tracks/{trackId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId ){
        try {
            TracksDTO tracks = trackHandler.deleteTrackFromPlaylist(token, playlistId, trackId);
            return Response.ok(tracks).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }

    @POST
    @Path("/playlists/{playlistId}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTracksFromPlaylist(@PathParam("playlistId") int playlistId, TrackDTO trackDTO){
        try{
            TracksDTO tracks = trackHandler.addTrackToPlaylist(token, playlistId, trackDTO);
            return Response.ok(tracks).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }
}
