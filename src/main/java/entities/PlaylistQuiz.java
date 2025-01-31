package entities;

import java.util.ArrayList;
import java.util.List;

public class PlaylistQuiz implements Quiz{
    private Playlist SongList;
    private final Player player;
    private Song curr; // the song currently playing
    private int index;
    private int timeLeft;
    private int points;
    private List<String> suggestions;
    public PlaylistQuiz(Player player)
    {
        this.index = 0;
        this.curr = null;
        this.player = player;
        this.timeLeft = 0;
        this.points = 0;
        this.suggestions = null;
    }

    @Override
    public void setQuiz(Playlist songList) {
        this.SongList = songList;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    @Override
    public ArrayList<Player> players() {
        ArrayList<Player> temp = new ArrayList<>();
        temp.add(this.player);
        return temp ;
    }

    @Override
    public Song currentPlaying() {
        if(this.curr == null) {
            this.curr = this.SongList.getSong(this.index);
        }
        return this.curr;
    }

    @Override
    public void goNext() {

        if(this.curr == null) {
            this.curr = this.SongList.getSong(this.index);
        }
        else {
            this.index++;
            this.curr = this.SongList.getSong(this.index);
        }
    }

    @Override
    public double getTimeLeft() {
        return timeLeft;
    }

    @Override
    public int getRemaining() {
        return 50 - index;
    }

    @Override
    public boolean isSong(String name) {
        return false;
    }

    public void addPoints(){
        this.points = this.points + 1;
    }

    public void addPoints(int amount){
        this.points = this.points + amount;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    public void decreaseTime() {
        this.timeLeft = this.timeLeft - 1;
    }

    public void decreaseTime(int amount) {
        this.timeLeft = Math.max(this.timeLeft - amount, 0);
    }

    @Override
    public void setTime(int time) {
        this.timeLeft = time;
    }
}
