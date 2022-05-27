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

    public BufferedImage getCoverImage(int songID) throws SongDAOException {
        return songDAO.downloadCoverImage(songID);
    }

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
        System.out.println(songs.size());

        for (int i = 0; i < songs.size(); i++) {
            switch (songs.get(i).getGenre()){
                case ROCK:
                    data[0]++;
                    break;
                case POP:
                    data[1]++;
                    break;
                case RAP:
                    data[2]++;
                    break;
                case TRAP:
                    data[3]++;
                    break;
                case DEMBOW:
                    data[4]++;
                    break;
                case DISCO:
                    data[5]++;
                    break;
                case RB:
                    data[6]++;
                    break;
                case SOUL:
                    data[7]++;
                    break;
                case COUNTRY:
                    data[8]++;
                    break;
                case REGGAE:
                    data[9]++;
                    break;
                case TECHNO:
                    data[10]++;
                    break;
                case BLUES:
                    data[11]++;
                    break;
                case JAZZ:
                    data[12]++;
                    break;
                case METAL:
                    data[13]++;
                    break;
                case PUNK:
                    data[14]++;
                    break;
                case SWING:
                    data[15]++;
                    break;
                default:
                    break;
            }
        }

        return data;
    }

    /**
     * Checks whether the song to delete is currently playing and whether it belongs
     * to the currently logged-in user
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
            playlistManager.removeSongFromPlaylists(song);
            return true;
        } catch (SongDAOException e) {
            return false;
        }
    }
}