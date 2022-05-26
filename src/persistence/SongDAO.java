package persistence;

import business.entities.Genre;
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
     * This method searched the storage system and returns a instance of {@link Song} if the values match.
     * @param songID unique identifier of the {@link Song} instance
     * @return (1) Instance of {@link Song} if the identifier matches with stored values in the system or
     * (2) null otherwise
     * @throws SongDAOException if there is an error accessing the database.
     */
    Song getSongByID(int songID) throws SongDAOException;

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
     * This method will return all the songs that belong to an author given its name.
     * @param authorName String containing the name of the author to filter the songs.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByAuthorName(String authorName) throws SongDAOException;

    /**
     * This method will return all the songs in the database.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getAllSongs() throws SongDAOException;

    /**
     * This method will return all the songs that have the same title. <b>Note</b> that songs can have the same
     * name and be different.
     * @param title String containing the title of the song.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByTitle(String title) throws SongDAOException;

    /**
     * This method will return all the songs listed under the same album name.
     * @param album String containing the album of the song
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByAlbum(String album) throws SongDAOException;

    /**
     * This method will return all the songs listed under the same genre.
     * @param genre instance of {@link Genre} to filter the songs.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByGenre(Genre genre) throws SongDAOException;

    /**
     * This method will search in the storage system any song that matches with the song title, album, author
     * or genre given the <b>key</b> parameter.
     * @param key String containing a value used to filter the search.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws SongDAOException if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByKeyword(String key) throws SongDAOException;

    /**
     * This method will update the values of a song given its unique identifier. <b>Note</b> that ALL values will
     * be replaced from the desired song, so it's important to make sure that other values of the song that we do not
     * wish to update remain the same. All information stored in the system regarding this song will be overwritten.
     * @param song instance of {@link Song} containing the new values to persist in the system.
     * @return (1) true if the song has been updated successfully or (2) false if the identifier doesn't match any song.
     * @throws SongDAOException if there is an error accessing the database.
     */
    boolean updateSong(Song song) throws SongDAOException;

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
     * @throws SongDAOException if the image can't be retrieved from the system or doesn't exist
     */
    BufferedImage downloadCoverImage(int songID) throws SongDAOException;
}