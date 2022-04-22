package business;

import business.entities.Genre;
import business.entities.Song;
import business.entities.User;
import com.dropbox.core.util.IOUtil;
import persistence.SongDAO;
import persistence.UserDAO;
import persistence.postgresql.SongSQL;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class SongManager {
    private SongDAO songDAO;
    private UserDAO userDAO;

    public SongManager(SongDAO songDAO, UserDAO userDAO) {
        this.songDAO = songDAO;
        this.userDAO = userDAO;
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
            ArrayList<Song> existingSongs = songDAO.getSongsByAlbum(album, userDAO);

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

        // TODO: Might be a good idea adding an AuthorDAO to be able to have a list of the diferent authors
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
    public void addSong(String title, String album, Genre genre, String author, String path, String user) {
        // TODO: Is it possible to change user in Song so as it is a String (simplifies implementation)?
        Song song = new Song(title, album, genre, author, path, 90, new User(user, "a@g.c", "4321"));
        //Song song = new Song(title, album, genre, author, path, 90, user);    // preferable implementation
        IOUtil.ProgressListener progressListener = l -> System.out.println("ProgressTest");

        // TODO: Review SongDAO and SongSQL implementations:
        // todo: songDAO should only receive and instance Song
        // todo: songSQL should internally allow to create a song by receiving only an instance of Song

        try {
            songDAO.createSong(song, new File(""), progressListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}