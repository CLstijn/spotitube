package oose.dea.services.dto;

import java.util.ArrayList;

public class PlaylistsDTO {

    private ArrayList<PlaylistDTO>  playlists;
    private int length;

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
