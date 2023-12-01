package entities;

import entities.Playlist;
import entities.Song;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpotifyPlaylist implements Playlist {

    private final ArrayList<Song> songsList;

    public SpotifyPlaylist(List<HashMap<String, String>> tracks)
    {
        // <Song name, URL>
        this.songsList = new ArrayList<>();
        for (HashMap<String, String> track : tracks) {
            // Assuming each map contains keys "name" and "url"
            String name = track.get("name");
            String url = track.get("preview_url");
            songsList.add(new SongData(name, url)); // Assuming SongData is a concrete implementation of Song
        }
    }

    public ArrayList<Song> getSongsList() {
        return songsList;
    }

    @Override
    public ArrayList<Song> songs() {
        return null;
    }

    @Override
    public Song getSong(int index) {
        if (index >= 0 && index < songsList.size()) {
            return songsList.get(index);
        }
        return null;
    }

    @Override
    public Song getByName(String name) {
        for (Song song : songsList) {
            if (song.getSongName().equals(name)) {
                return song;
            }
        }
        return null;
    }

    @Override
    public Song getByAuthor(String author) {
        return null;
    }

    @Override
    public void addSong(Song song) {

    }

    @Override
    public void addSong(Song[] songs) {

    }
}