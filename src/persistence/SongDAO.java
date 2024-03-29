package persistence;

import business.entities.Song;

import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Public interface that manages {@link Song} persistence in the system.
 * @see Song
 */
public interface SongDAO {

    /**
     * This method stores an instance of {@link Song} passed in parameters to the storage system. <b>Note</b>
     * that there is no control with adding a song equal to another one. Unique identifiers of a song
     * are generated everytime a song is persisted therefore if we add the same song twice it will be treated
     * as diferent songs.
     * @param song instance of {@link Song} with the values to persist.
     * @param songFile instance of {@link File} containing the song to upload
     * @param imageFile instance of {@link File} containing the image of the song
     * @return (1) true if the song is added correctly or (2) false if there is an error.
     * @throws SongDAOException if there is an error accessing the database.
     */
    boolean createSong(Song song, File songFile, File imageFile) throws SongDAOException;

    /**
     * Gets all the stored authors.
     * @return an ArrayList of String containing the names of the different authors
     */
    ArrayList<String> getAllAuthors() throws SongDAOException;

    /**
     * This method will return all the songs that are part of a playlist given the playlist ID.
     * @param playlistID {@link business.entities.Playlist} unique identifier
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByPlaylistID(int playlistID) throws SongDAOException;

    /**
     * This method will return all the songs that were added by a user given its unique identifier.
     * @param userID {@link business.entities.User} unique identifier
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByUserID(String userID) throws SongDAOException;

    /**
     * This method will return all the songs in the database.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getAllSongs() throws SongDAOException;

    /**
     * This method will return all the songs listed under the same album name.
     * @param album String containing the album of the song
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByAlbum(String album) throws SongDAOException;

    /**
     * This method will delete a song from the system provided its unique identifier.
     * @param songID unique identifier for the song we wish to delete
     * @return (1) true if the song has been deleted successfully or (2) false if the identifier doesn't match any song.
     * @throws SongDAOException if there is an error accessing the database.
     */
    boolean deleteSong(int songID) throws SongDAOException;

    /**
     * This method will download a song from the song storage system given its unique identifier.
     * @param songID unique identifier for the song we wish to download
     * @return {@link AudioInputStream} instance with the song loaded and ready to be reproduced or null
     * @throws SongDAOException if the song couldn't be downloaded.
     */
    AudioInputStream downloadSong(int songID) throws SongDAOException;

    /**
     * This method will download the cover image associated with the song in the storage system
     * @param songID unique identifier of the song
     * @return BufferedImage instance with the song or null
     */
    BufferedImage downloadCoverImage(int songID);

    /**
     * This method will delete all instance in the filesystem containing the given unique identifier
     * @param songID integer containing unique identifier of the song to delete
     */
    void deleteFilesystem(int songID);
}
