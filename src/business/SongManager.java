package business;

import business.entities.*;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import persistence.SongDAO;
import persistence.SongDAOException;
import presentation.controllers.LyricsListener;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Public class that implements and manages all logic related to songs
 */
public class SongManager {
    private static final int NUMBER_OF_GENRES = 16;

    private final SongDAO songDAO;
    private final PlayerManager playerManager;

    /**
     * Constructor for the songManager instance
     * @param songDAO data access object for the song business entity
     * @param playerManager player manager
     */
    public SongManager(SongDAO songDAO, PlayerManager playerManager) {
        this.songDAO = songDAO;
        this.playerManager = playerManager;
    }

    /**
     * Initializes the getLyrics thread.
     * @param lyricsListener listener for grt lyrics.
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

    public static final int SONG_ID_ATTRIBUTE_INDEX = 0;
    public static final int SONG_TITLE_ATTRIBUTE_INDEX = 1;
    public static final int SONG_ALBUM_ATTRIBUTE_INDEX = 2;
    public static final int SONG_AUTHOR_ATTRIBUTE_INDEX = 3;
    public static final int SONG_DURATION_ATTRIBUTE_INDEX = 4;
    public static final int SONG_USER_ATTRIBUTE_INDEX = 5;
    public static final int SONG_GENRE_ATTRIBUTE_INDEX = 6;
    public static final int SONG_IMAGE_ATTRIBUTE_INDEX = 7;

    /**
     * Gets all songs in the system
     * @return an Arraylist of an ArrayList of String containing all the songs' attributes
     */
    public ArrayList<ArrayList<String>> getAllSongs() {
        try {
            ArrayList<Song> songs = songDAO.getAllSongs();
            ArrayList<ArrayList<String>> songsStr = new ArrayList<>();

            if (songs != null) {
                for (Song s : songs) {
                    ArrayList<String> attributes = new ArrayList<>();
                    attributes.add(SONG_ID_ATTRIBUTE_INDEX, Integer.toString(s.getId()));
                    attributes.add(SONG_TITLE_ATTRIBUTE_INDEX, s.getTitle());
                    attributes.add(SONG_ALBUM_ATTRIBUTE_INDEX, s.getAlbum());
                    attributes.add(SONG_AUTHOR_ATTRIBUTE_INDEX, s.getAuthor());
                    attributes.add(SONG_DURATION_ATTRIBUTE_INDEX, Integer.toString(s.getDuration()));
                    attributes.add(SONG_USER_ATTRIBUTE_INDEX, s.getUser());
                    attributes.add(SONG_GENRE_ATTRIBUTE_INDEX, String.valueOf(s.getGenre()));
                    attributes.add(SONG_IMAGE_ATTRIBUTE_INDEX, s.getImagePath());

                    songsStr.add(attributes);
                }
            }

            return songsStr;
        } catch (SongDAOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets all songs from a playlist
     * @return an ArrayList of an Arraylist of String containing all the songs' attributes
     */
    public ArrayList<ArrayList<String>> getAllPlaylistSongs(int playlistId) {
        try {
            ArrayList<Song> songs = songDAO.getSongsByPlaylistID(playlistId);
            ArrayList<ArrayList<String>> songsStr = new ArrayList<>();

            if (songs != null) {
                for (Song s : songs) {
                    ArrayList<String> attributes = new ArrayList<>();
                    attributes.add(SONG_ID_ATTRIBUTE_INDEX, Integer.toString(s.getId()));
                    attributes.add(SONG_TITLE_ATTRIBUTE_INDEX, s.getTitle());
                    attributes.add(SONG_ALBUM_ATTRIBUTE_INDEX, s.getAlbum());
                    attributes.add(SONG_AUTHOR_ATTRIBUTE_INDEX, s.getAuthor());
                    attributes.add(SONG_DURATION_ATTRIBUTE_INDEX, Integer.toString(s.getDuration()));
                    attributes.add(SONG_USER_ATTRIBUTE_INDEX, s.getUser());
                    attributes.add(SONG_GENRE_ATTRIBUTE_INDEX, String.valueOf(s.getGenre()));
                    attributes.add(SONG_IMAGE_ATTRIBUTE_INDEX, s.getImagePath());

                    songsStr.add(attributes);
                }
            }

            return songsStr;
        } catch (SongDAOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Checks whether the song's attributes are correct
     * @param title a String containing the title of the song
     * @param album a String containing the album of the song
     * @return a boolean indicating whether the song is already defined in album
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
     * @param author a String containing the name of the author
     * @return a boolean indicating whether the author is correct
     */
    public boolean newAuthorIsValid(String author) {
        if (author.isBlank()) {
            return false;
        }

        try {
            ArrayList<String> authors = songDAO.getAllAuthors();

            if (authors == null) return true;

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
     * @param file an instance of File containing the MP3 file
     * @param image an instance of File containing the JPEG image
     * @param title a String containing the title of the song
     * @param album a String containing the album of the song
     * @param genre an item in the {@link Genre} enumeration
     * @param author a String containing the name of the author of the song
     * @param path a String containing the path to the song image
     * @param user an instance of {@link User} representing the user that adds the song
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

    //TODO: add comment
    public BufferedImage getCoverImage(int songID) {
        return songDAO.downloadCoverImage(songID);
    }

    //TODO: remove method if unused
    public int[] getNumberOfSongsByGenre() throws SongDAOException{
        int i = 0;
        int[] data = new int[NUMBER_OF_GENRES];

        for (Genre genre : Genre.values()){
            ArrayList<Song> array = songDAO.getSongsByGenre(genre);
            data[i] = array == null ? 0 : array.size();
            i++;
        }

        return data;
    }

    public int[] getNumberOfSongs() throws SongDAOException{
        int[] data = new int[NUMBER_OF_GENRES];
        ArrayList<Song> songs = songDAO.getAllSongs();
        if (songs != null) {
            for (Song song : songs) {
                switch (song.getGenre()) {
                    case ROCK -> data[0]++;
                    case POP -> data[1]++;
                    case RAP -> data[2]++;
                    case TRAP -> data[3]++;
                    case DEMBOW -> data[4]++;
                    case DISCO -> data[5]++;
                    case RB -> data[6]++;
                    case SOUL -> data[7]++;
                    case COUNTRY -> data[8]++;
                    case REGGAE -> data[9]++;
                    case TECHNO -> data[10]++;
                    case BLUES -> data[11]++;
                    case JAZZ -> data[12]++;
                    case METAL -> data[13]++;
                    case PUNK -> data[14]++;
                    case SWING -> data[15]++;
                    default -> {
                    }
                }
            }
        }

        return data;
    }

    /**
     * Checks whether the song to delete is currently playing
     * @param songID an integer representing the ID of the song to delete
     * @return a boolean indicating whether the song can be deleted
     */
    public boolean songCanBeDeleted(int songID) {
        // check song not playing (user already checked)
        return playerManager.getCurrentSong() != songID;
    }

    /**
     * Permanently deletes a song from the system
     * @param song an integer representing the ID of the song to delete
     * @return a boolean indicating whether the song could be deleted
     */
    public boolean deleteSong(int song) {
        try {
            songDAO.deleteSong(song);
            //playlistManager.removeSongFromPlaylists(song);
            return true;
        } catch (SongDAOException e) {
            return false;
        }
    }

    /**
     * This method will start a thread that will delete all related
     * @param currentUser String containing unique identifier of current user
     */
    public void deleteAllSongsFromUser(String currentUser) throws SongDAOException{
        ArrayList<Song> songs = songDAO.getSongsByUserID(currentUser);
        SongUnloader songUnloader = new SongUnloader(songs, songDAO);
        songUnloader.start();
    }
}