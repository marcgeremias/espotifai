package business;

import business.entities.Song;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import persistence.SongDAOException;

import javax.sound.sampled.AudioInputStream;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongLoader extends Thread{

    private SongManager songManager;

    private ArrayList<Song> songs;
    private AudioInputStream[] songsBuffer;
    private boolean done;

    public SongLoader(SongManager songManager){
        this.songManager = songManager;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < songs.size(); i++) {
                songsBuffer[i] = songManager.getSongStream(songs.get(i));
            }
            done = true;
        } catch (SongDAOException e) {
            e.printStackTrace();
        }
    }

    public void load(ArrayList<Song> songs, AudioInputStream[] songsBuffer) {
        this.songs = songs;
        done = false;
        this.songsBuffer = songsBuffer;
    }

    public boolean awaitTask(){
        return done;
    }

    public AudioInputStream[] getSongsBuffer(){
        return songsBuffer;
    }
}
