package business;

import business.entities.*;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import persistence.SongDAO;
import persistence.SongDAOException;
import persistence.UserDAO;
import persistence.config.APILyrics;
import presentation.controllers.LyricsListener;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SongManager {
    private static int NUMBER_OF_GENRES = 16;

    private SongDAO songDAO;
    private PlaylistManager playlistManager;
    private PlayerManager playerManager;

    public SongManager(SongDAO songDAO, PlaylistManager playlistManager, PlayerManager playerManager) {
        this.songDAO = songDAO;
        this.playlistManager = playlistManager;
        this.playerManager = playerManager;
    }

    /**
     * Inicializa the getLyrics thread.
     * @param lyricsListener listner for grt lyrics.
     * @param songTitle song title.
     * @param songAuthor song author.
     */
    public void getLyrics(LyricsListener lyricsListener, String songTitle, String songAuthor){
        LyricsLoader lyricsLoader = new LyricsLoader(lyricsListener, songTitle, songAuthor);
        lyricsLoader.start();
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
     * Gets all songs from a playlist
     * @return an ArrayList of Songs containing all songs
     */
    public ArrayList<Song> getAllPlaylistSongs(int playlistId) {
        try {
            return songDAO.getSongsByPlaylistID(playlistId);
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

            if (existingSongs == null) return true;

            for (Song existingSong : existingSongs) {
                if (existingSong.getTitle().equalsIgnoreCase(title)) {
                    return false;
                }
            }

            return true;
        } catch (SongDAOException e) {
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
    public void addSong(File file, File image, String title, String album, Genre genre, String author, String path, String user) throws SongDAOException, UnsupportedAudioFileException, IOException {
        // Extract song duration from file
        AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
        Map properties = baseFileFormat.properties();
        int duration = (int) ((Long) properties.get("duration") / 1000000);

        if (path.isBlank()) {
            path = "no path";
        }

        Song song = new Song(title, album, genre, author, path, duration, user);

        songDAO.createSong(song, file, image);
    }

    public BufferedImage getCoverImage(int songID) throws SongDAOException {
        return songDAO.downloadCoverImage(songID);
    }

    public int[] getNumberOfSongsByGenre() throws SongDAOException{
        int i = 0;
        int[] data = new int[NUMBER_OF_GENRES];

        for (Genre genre : Genre.values()){
            ArrayList<Song> array = songDAO.getSongsByGenre(genre);
            data[i] = array == null ? 0 : array.size();
            System.out.println(data[i]);
            i++;
        }

        return data;
    }

    /**
     * Checks whether the song to delete is currently playing and whether it belongs
     * to the currently logged-in user
     * @param songID an integer representing the ID of the song to delete
     * @param currentUser a String containing the username of the current user
     * @return a boolean indicating whether the song can be deleted
     */
    public boolean songCanBeDeleted(int songID, String currentUser) {
        try {
            Song song = songDAO.getSongByID(songID);
            // check song not playing
            if (playerManager.getCurrentSong() == songID) System.out.println("Song is playing");
            // check song user is current user
            //return song.getUser().equals(currentUser) && !isPlaying();
            return playerManager.getCurrentSong() != songID && song.getUser().equals(currentUser);
        } catch (SongDAOException e) {
            return false;
        }
    }

    /**
     * Permanently deletes a song from the system
     * @param song an integer representing the ID of the song to delete
     * @return a boolean indicating whether the song could be deleted
     */
    public boolean deleteSong(int song) {
        try {
            songDAO.deleteSong(song);
            playlistManager.removeSongFromPlaylists(song);
            return true;
        } catch (SongDAOException e) {
            return false;
        }
    }
}