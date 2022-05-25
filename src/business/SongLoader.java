package business;

import business.entities.Song;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import persistence.SongDAOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SongLoader extends Thread{

    private static final String SONGS_DIRECTORY = "./res/songs";

    private SongManager songManager;

    private ArrayList<Song> songs;
    private ArrayList<Integer> songsSavedIndex;
    private AudioInputStream[] songsBuffer;
    private boolean done;

    public SongLoader(SongManager songManager){
        this.songManager = songManager;
        songsSavedIndex = new ArrayList<>();
        File directory = new File(SONGS_DIRECTORY);
        for (String file : Objects.requireNonNull(directory.list())){
            songsSavedIndex.add(Integer.parseInt(file.split("\\.")[0]));
        }
    }

    @Override
    public void run() {
        try {
            File directory = new File(SONGS_DIRECTORY);
            if (!directory.exists()){
                directory.mkdir();
            }
            for (Song song : songs) {
                if (!songsSavedIndex.contains(song.getId())) {
                    AudioInputStream ais = songManager.getSongStream(song);
                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(SONGS_DIRECTORY + "/" + song.getId() + ".wav"));
                    songsSavedIndex.add(song.getId());
                }
            }
            done = true;
        } catch (SongDAOException | IOException e) {
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

    public ArrayList<Integer> getSongsSaved() {
        return songsSavedIndex;
    }
}
