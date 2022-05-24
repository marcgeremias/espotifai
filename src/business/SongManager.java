package business;

import business.entities.*;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import persistence.SongDAO;
import persistence.SongDAOException;
import persistence.UserDAO;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SongManager {
    private SongDAO songDAO;
    private UserDAO userDAO;
    //private ArrayList<String> authors; // get authors from beginning then add when new author?

    public SongManager(SongDAO songDAO, UserDAO userDAO) {
        this.songDAO = songDAO;
        this.userDAO = userDAO;
    }

    /**
     * Gets all existing authors
     * @return an ArrayList of String containing the names of the authors
     */
    public ArrayList<String> getAuthors() {
        try {
            return songDAO.getAllAuthors();
        } catch (SongDAOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets all songs in the system
     * @return an ArrayList of Songs containing all songs
     */
    public ArrayList<Song> getAllSongs() {
        try {
            return songDAO.getAllSongs();
        } catch (SongDAOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Checks whether the song's attributes are correct
     * @param title: a String containing the title of the song
     * @param album: a String containing the album of the song
     * @return a boolean indicating whether the song is already defined in the album
     */
    public boolean newSongInfoIsCorrect(String title, String album) {
        if (title.isBlank() || album.isBlank()) {
            return false;
        }

        try {
            ArrayList<Song> existingSongs = songDAO.getSongsByAlbum(album);

            for (Song existingSong : existingSongs) {
                if (existingSong.getTitle().equalsIgnoreCase(title)) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            // either no songs or album not defined in database
            return true;
        }
    }

    /**
     * Checks whether a new author does not exist already
     * @param author: a String containing the name of the author
     * @return a boolean indicating whether the author is correct
     */
    public boolean newAuthorIsValid(String author) {
        if (author.isBlank()) {
            return false;
        }

        try {
            ArrayList<String> authors = songDAO.getAllAuthors();

            for (String a : authors) {
                if (a.equalsIgnoreCase(author)) return false;
            }
        } catch (SongDAOException e) {
            return false;
        }

        return true;
    }

    /**
     * Adds a new song to the system
     * @param title: a String containing the title of the song
     * @param album: a String containing the album of the song
     * @param genre: an item in the {@link Genre} enumeration
     * @param author: a String containing the name of the author of the song
     * @param path: a String containing the path to the song image
     * @param user: an instance of {@link User} representing the user that adds the song
     */
    public void addSong(File file, String title, String album, Genre genre, String author, String path, String user) throws SongDAOException, UnsupportedAudioFileException, IOException {
        // Extract song duration from file
        AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
        Map properties = baseFileFormat.properties();
        int duration = (int) ((Long) properties.get("duration") / 1000000);

        if (path.isBlank()) {
            path = "no path";
        }

        Song song = new Song(title, album, genre, author, path, duration, user);

        songDAO.createSong(song, file);
    }

    public AudioInputStream getSongStream(Song song) throws SongDAOException{
        return songDAO.downloadSong(song.getId());
    }

    public BufferedImage getCoverImage(int songID) throws SongDAOException {
        return songDAO.downloadCoverImage(songID);
    }
}