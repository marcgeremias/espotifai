package business;

import business.entities.Song;
import persistence.SongDAO;
import persistence.SongDAOException;

import java.io.File;
import java.util.ArrayList;

/**
 * Thread class that deletes all trace of a song in the filesystem
 */
public class SongUnloader extends Thread{

    private static final String SONGS_PATH = "res/songs/";
    private static final String LOCAL_SONGS_EXTENSION = ".wav";

    private final ArrayList<Song> songs;
    private final SongDAO songDAO;

    /**
     * Public constructor for the SongUnloader Thread
     * @param songs
     * @param songDAO
     */
    public SongUnloader(ArrayList<Song> songs, SongDAO songDAO){
        this.songs = songs;
        this.songDAO = songDAO;
    }

    /**
     * Runnable method that executes delete of all songs associated with user
     */
    @Override
    public void run() {
        for (Song song : songs){
            songDAO.deleteFilesystem(song.getId());
            File songWAV = new File(SONGS_PATH + song.getId() + LOCAL_SONGS_EXTENSION);
            songWAV.delete();
        }
    }
}
