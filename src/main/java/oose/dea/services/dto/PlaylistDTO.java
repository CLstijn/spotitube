package oose.dea.services.dto;

import java.util.ArrayList;

public class PlaylistDTO {

    private int id;
    private String name;
    private boolean owner;
    private ArrayList<TrackDTO> tracks;

    public PlaylistDTO(){tracks = new ArrayList<>();}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
