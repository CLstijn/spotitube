package oose.dea.services;

import oose.dea.services.dto.PlaylistsDTO;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.domain.PlaylistHandler;
import oose.dea.services.dto.PlaylistDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class PlaylistService {

    @Inject
    private PlaylistHandler playlistHandler;

    @QueryParam("token") String token;

    @GET
    @Path("/playlists")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlaylistsFromUser(){
        try {
            PlaylistsDTO playlists = playlistHandler.getPlaylists(token);
            return Response.ok(playlists).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }

    @DELETE
    @Path("/playlists/{id-playlist}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePlaylistFromUser(@PathParam("id-playlist") int playlistId){
        try {
            PlaylistsDTO playlists = playlistHandler.deletePlaylistFromUser(token, playlistId);
            return Response.ok(playlists).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }

    @POST
    @Path("/playlists")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPlaylistFromUser(PlaylistDTO playlistDTO){
        try {
            PlaylistsDTO playlists = playlistHandler.addPlaylistFromUser(token, playlistDTO);
            return Response.status(201).entity(playlists).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }

    @PUT
    @Path("/playlists/{id-playlist}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response editPlaylistFromUser(@PathParam("id-playlist") int playlistId, PlaylistDTO playlistDTO){
        try {
            PlaylistsDTO playlists = playlistHandler.editPlaylistFromUser(token, playlistId, playlistDTO);
            return Response.ok(playlists).build();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return Response.status(e.getStatusCode()).build();
        }
    }
}
