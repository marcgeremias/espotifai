package business;

import business.entities.Song;
import persistence.SongDAOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Public class responsible for loading all the songs in the local system in a concurrent way without blocking main
 * execution
 */
public class SongLoader extends Thread{

    private static final String SONGS_DIRECTORY = "./res/songs";

    private SongManager songManager;

    private ArrayList<Song> songs;
    private ArrayList<Integer> songsSavedIndex;

    /**
     * Constructor for the SongLoader class. This constructor will load all the existing songs in the array
     * @param songManager instance of {@link SongManager} to download the songs
     */
    public SongLoader(SongManager songManager){
        this.songManager = songManager;
        songsSavedIndex = new ArrayList<>();
        File directory = new File(SONGS_DIRECTORY);
        if (!directory.exists()){
            directory.mkdir();
        }

        for (String file : Objects.requireNonNull(directory.list())){
            songsSavedIndex.add(Integer.parseInt(file.split("\\.")[0]));
        }
    }

    /**
     * Runnable method for the thread that will download all the songs given in the songs attribute
     */
    @Override
    public void run() {
        try {
            File directory = new File(SONGS_DIRECTORY);
            // If directory doesn't exist then we create it
            if (!directory.exists()){
                directory.mkdir();
            }
            for (Song song : songs) {
                //If song is already downloaded we don't want to download it again
                if (!songsSavedIndex.contains(song.getId())) {
                    AudioInputStream ais = songManager.getSongStream(song);
                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(SONGS_DIRECTORY + "/" + song.getId() + ".wav"));
                    songsSavedIndex.add(song.getId());
                }
            }
        } catch (SongDAOException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will load the list of songs to be downloaded if they are not in the system
     * @param songs list of {@link Song}
     */
    public void load(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * Gets the array containing the unique identifiers of the songs saved on the local machine
     * @return list of identifiers of the songs saved in the local system
     */
    public ArrayList<Integer> getSongsSaved() {
        return songsSavedIndex;
    }
}
