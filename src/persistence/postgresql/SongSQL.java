package persistence.postgresql;

import persistence.SongDAOException;
import business.entities.Genre;
import business.entities.Song;
import persistence.UserDAOException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import persistence.SongDAO;
import persistence.UserDAO;
import persistence.config.APIConfig;
import persistence.config.DBConstants;
import persistence.config.DBConfig;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static persistence.config.APIConfig.COVERS_ROOT_FOLDER;
import static persistence.config.APIConfig.SONGS_ROOT_FOLDER;

/**
 * Public class that implements {@link SongDAO} interface with SQL persistence in the database configured on start-up.
 * @see Song
 * @see SongDAO
 * @see DBConstants
 * @see DBConfig
 */
public class SongSQL implements SongDAO {

    private static final String SONG_FORMAT = "mp3";
    private static final String IMAGE_FORMAT = "jpg";

    /**
     * Public method to create a new song in the database. An instance of {@link Song} is passed as a parameter and
     * the values are persisted on the database. Note that all the fields will be edited so it's important to make
     * sure that all important fields are not empty.
     * @param song instance of {@link Song} containing the values to add
     * @param songFile instance of {@link File} containing the song to upload
     * @return true (1) if the song has been created correctly or false (2) if the song hasn't been created correctly.
     * @throws SongDAOException if the query fails to execute or the database connection can't be opened
     */
    @Override
    public boolean createSong(Song song, File songFile) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String createSongSQL = "INSERT INTO " + DBConstants.TABLE_SONG + "(" + DBConstants.COL_ID_NICKNAME
                    + ", " + DBConstants.SONG_COL_ALBUM + ", " + DBConstants.SONG_COL_AUTHOR + ", " + DBConstants.SONG_COL_GENRE
                    + ", " + DBConstants.SONG_COL_PATH + ", " + DBConstants.SONG_COL_TITLE + ", " + DBConstants.SONG_COL_DURATION + ")"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING " + DBConstants.COL_ID_SONG;
            PreparedStatement createSongSTMT = c.prepareStatement(createSongSQL);
            createSongSTMT.setString(1, song.getUser());
            createSongSTMT.setString(2, song.getAlbum());
            createSongSTMT.setString(3, song.getAuthor());
            createSongSTMT.setString(4, String.valueOf(song.getGenre()));
            createSongSTMT.setString(5, song.getImagePath());
            createSongSTMT.setString(6, song.getTitle());
            createSongSTMT.setInt(7, song.getDuration());
            ResultSet rs = createSongSTMT.executeQuery();

            if (rs.next()) {
                c.close();
                return uploadSong(songFile, rs.getInt(1)); //todo: uncomment for delivery
                //return true;
            } else {
                c.close();
                return false;
            }
        } catch (SQLException | DbxException | IOException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * Gets all the authors stored in the database.
     * @return an ArrayList of String containing the names of the different authors
     * @throws SongDAOException if the query fails to execute correctly or the database connection
     * can't be opened.
     */
    public ArrayList<String> getAllAuthors() throws SongDAOException {
        try {
            ArrayList<String> authors = new ArrayList<>();
            String query = "SELECT DISTINCT(" + DBConstants.SONG_COL_AUTHOR + ") FROM " + DBConstants.TABLE_SONG
                    + " ORDER BY " + DBConstants.SONG_COL_AUTHOR + " ASC";
            Connection c = DBConfig.getInstance().openConnection();
            ResultSet result = c.prepareStatement(query).executeQuery();

            while (result.next()) {
                authors.add(result.getString(1));
            }

            c.close();
            return authors.size() > 0 ? authors : null;
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will return one instance of the class {@link Song} if the identifier provided matches with any
     * value stored in the database.
     * @param songID identifier of the song
     * @return instance of {@link Song} with the values retrieved from the database
     * @throws SongDAOException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public Song getSongByID(int songID) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String selectSongSQL = "SELECT * FROM " + DBConstants.TABLE_SONG + " WHERE id_song = ?";
            PreparedStatement selectSongSTMT = c.prepareStatement(selectSongSQL);
            selectSongSTMT.setInt(1, songID);
            ResultSet rs = selectSongSTMT.executeQuery();

            Song song = null;
            if (rs.next()) {
                song = getSongFromResultSet(rs);
            }

            c.close();
            return song;
        } catch (SQLException e) {
            throw  new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param playlistID identifier to query the songs
     * @return List of Songs with all the data from the database
     * @throws SongDAOException if the query fails to execute correctly or the database connection
     * can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByPlaylistID(int playlistID) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT s.* FROM " + DBConstants.TABLE_SONG + " s, " + DBConstants.TABLE_SONG_PLAYLIST + " ps " +
                    "WHERE s." + DBConstants.COL_ID_SONG + " = ps." + DBConstants.COL_ID_SONG
                    + " AND ps." + DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            selectSongsSTMT.setInt(1, playlistID);
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param userID identifier to query the songs
     * @return List of Songs with all the data from the database
     * @throws SongDAOException if the query fails to execute correctly or the database connection
     * can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByUserID(String userID) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT * FROM " + DBConstants.TABLE_SONG
                    + " WHERE " + DBConstants.COL_ID_NICKNAME + " = ?";

            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            selectSongsSTMT.setString(1, userID);
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param authorName identifier to query the songs
     * @return List of Songs with all the data from the database
     * @throws SongDAOException if the query fails to execute correctly or the database connection
     * can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByAuthorName(String authorName) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT * FROM " + DBConstants.TABLE_SONG
                    + " WHERE " + DBConstants.SONG_COL_AUTHOR + " = ?";

            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            selectSongsSTMT.setString(1, authorName);
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    @Override
    public ArrayList<Song> getAllSongs() throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT * FROM " + DBConstants.TABLE_SONG;

            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param title identifier to query the songs
     * @return List of Songs with all the data from the database
     * @throws SongDAOException if the query fails to execute correctly or the database connection
     * can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByTitle(String title) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT * FROM " + DBConstants.TABLE_SONG
                    + " WHERE " + DBConstants.SONG_COL_TITLE + " = ?";

            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            selectSongsSTMT.setString(1, title);
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param album identifier to query the songs
     * @return List of Songs with all the data from the database
     * @throws SongDAOException if the query fails to execute correctly or the database connection
     * can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByAlbum(String album) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT * FROM " + DBConstants.TABLE_SONG
                    + " WHERE " + DBConstants.SONG_COL_ALBUM + " = ?";

            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            selectSongsSTMT.setString(1, album);
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param genre identifier to query the songs
     * @return List of Songs with all the data from the database
     * @throws SongDAOException if the query fails to execute correctly or the database connection
     * can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByGenre(Genre genre) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT * FROM " + DBConstants.TABLE_SONG
                    + " WHERE " + " UPPER( " + DBConstants.SONG_COL_GENRE + ") = ?";

            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            selectSongsSTMT.setString(1, String.valueOf(genre));
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will return all the songs that match their title, author name, album name or genre type with the
     * key String given in the function parameters.
     * @param key String key containing the value to search.
     * @return List of {@link Song} if the query finds matches or null if the query doesn't find any matching song.
     * @throws SongDAOException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByKeyword(String key) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Song> songs = new ArrayList<>();

            String selectSongsSQL = "SELECT * FROM " + DBConstants.TABLE_SONG
                    + " WHERE " + DBConstants.SONG_COL_AUTHOR + " ILIKE ? OR " + DBConstants.SONG_COL_GENRE + " ILIKE ? OR " +
                    DBConstants.SONG_COL_ALBUM + " ILIKE ? OR " + DBConstants.SONG_COL_TITLE + " ILIKE ?";

            PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
            selectSongsSTMT.setString(1, "%" + key + "%");
            selectSongsSTMT.setString(2, "%" + key + "%");
            selectSongsSTMT.setString(3, "%" + key + "%");
            selectSongsSTMT.setString(4, "%" + key + "%");
            return getSongs(c, songs, selectSongsSTMT);
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will update the values of the given {@link Song} and overwrite everything. Please note that values
     * that you don't wish to updated must be passed as well as the function will overwrite everything from the row.
     * @param song instance of {@link Song} containing the updated values
     * @return true (1) the song has been updated correctly or false (2) if the given song doesn't exist in the
     * database.
     * @throws SongDAOException if the query fails to execute correctly or the database can't be opened
     */
    @Override
    public boolean updateSong(Song song) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String updateSongSQL = "UPDATE " + DBConstants.TABLE_SONG + " SET " +
                    DBConstants.SONG_COL_TITLE + " = ?, " + DBConstants.SONG_COL_ALBUM + " = ?, " +
                    DBConstants.SONG_COL_GENRE + " = ?, " + DBConstants.SONG_COL_PATH + " = ?, " +
                    DBConstants.SONG_COL_AUTHOR + " = ? WHERE " + DBConstants.COL_ID_SONG + " = ?";
            PreparedStatement updateSongSTMT = c.prepareStatement(updateSongSQL);
            updateSongSTMT.setString(1, song.getTitle());
            updateSongSTMT.setString(2, song.getAlbum());
            updateSongSTMT.setString(3, String.valueOf(song.getGenre()));
            updateSongSTMT.setString(4, song.getImagePath());
            updateSongSTMT.setString(5, song.getAuthor());
            updateSongSTMT.setInt(6, song.getId());
            int count = updateSongSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will delete all rows affected in the database of the given song identifier.
     * @param songID identifier for the song
     * @return true (1) if the song has been deleted correctly or false (2) if the songID doesn't match any
     * identifier stored in the database.
     * @throws SongDAOException if the query fails to execute correctly or the database can't be opened
     * or the file can't be deleted from the API
     */
    @Override
    public boolean deleteSong(int songID) throws SongDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            if (!deleteSongFile(songID)) return false;

            String deleteSongSQL = "DELETE FROM " + DBConstants.TABLE_SONG + " WHERE " + DBConstants.COL_ID_SONG + " = ?";
            PreparedStatement deleteSongSTMT = c.prepareStatement(deleteSongSQL);
            deleteSongSTMT.setInt(1, songID);
            int count = deleteSongSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException | DbxException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    /**
     * This method will download a song from the Dropbox API given the unique identifier of a song
     * @param songID unique identifier for the song we wish to download
     * @return {@link AudioInputStream} instance to be reproduced or exception is thrown
     * @throws SongDAOException if the API client can't access the song or the Output stream can't be
     * opened or the file format is unsupported
     */
    @Override
    public AudioInputStream downloadSong(int songID) throws SongDAOException {
        try {
            //This seems like is not following 'Tell, don't ask rule' but it is actually very polite
            DbxClientV2 client = APIConfig.getInstance().getClient();
            //DbxDownloader<FileMetadata> downloader = client.files().download(SONGS_ROOT_FOLDER + "/" + songID + "." + SONG_FORMAT);

            // This Byte output stream will contain the downloaded song
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //Method to download the song
            //downloader.download(baos);
            FileMetadata metadata = client.files().downloadBuilder(SONGS_ROOT_FOLDER + "/" + songID +"." + SONG_FORMAT).download(baos);

            //Convert the ByteOutputStream to an AudioInputStream
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

            return mp3ToWav(AudioSystem.getAudioInputStream(bais));
        } catch (DbxException | IOException | UnsupportedAudioFileException e) {
            throw new SongDAOException(e.getMessage());
        }
    }

    @Override
    public BufferedImage downloadCoverImage(int songID) throws SongDAOException {
        try {
            //This seems like is not following 'Tell, don't ask rule' but it is actually very polite
            DbxClientV2 client = APIConfig.getInstance().getClient();
            //DbxDownloader<FileMetadata> downloader = client.files().download(COVERS_ROOT_FOLDER + "/" + songID + "." + IMAGE_FORMAT);

            // This Byte output stream will contain the downloaded file
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //Method to download the file
            //downloader.download(baos);
            FileMetadata metadata = client.files().downloadBuilder("/covers/1.jpg").download(baos);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

            return ImageIO.read(bais);
        } catch (DbxException | IOException e){
            throw new SongDAOException(e.getMessage());
        }
    }

    /*
        Private method to upload a song to the DropboxAPI
         */
    private boolean uploadSong(File song, int songID) throws DbxException, IOException {
        InputStream in = new FileInputStream(song);
        DbxClientV2 client = APIConfig.getInstance().getClient();

        FileMetadata metadata = client.files().uploadBuilder(SONGS_ROOT_FOLDER+"/"+songID+"."+SONG_FORMAT)
                .withMode(WriteMode.ADD)
                .withClientModified(new Date(song.lastModified()))
                .uploadAndFinish(in);

        return true;
    }

    /*
    Private method to delete a song from the dropbox API
     */
    private boolean deleteSongFile(int songID) throws DbxException {
        DbxClientV2 client = APIConfig.getInstance().getClient();
        DeleteResult metadata = client.files().deleteV2(SONGS_ROOT_FOLDER+"/"+songID+"."+SONG_FORMAT);
        // Will only reach here if delete operation is successful
        return true;
    }

    /*
    Private method the abstract execution on the main query functions
     */
    private Song getSongFromResultSet(ResultSet rs) throws SQLException {
        /*
        |   Col 1   |   Col 2   |   Col 3   |   Col 4   |   Col 5   |   Col 6   |   Col 7   |   Col 8   |   Col 9   |
        |  id_song  |  title    | duration  |   path    |(not used) |   genre   |   author  |   album   |   id_user |
         */
        return new Song(
                rs.getInt(1), rs.getString(2), rs.getString(8),
                Genre.valueOf(rs.getString(6).toUpperCase(Locale.ROOT)), rs.getString(7),
                rs.getString(4), rs.getInt(3), rs.getString(9)
        );
    }

    /*
    Function abstracted from repeated code
     */
    private ArrayList<Song> getSongs(Connection c, ArrayList<Song> songs, PreparedStatement selectSongsSTMT) throws SQLException {
        ResultSet rs = selectSongsSTMT.executeQuery();

        while(rs.next()) {
            Song song = getSongFromResultSet(rs);
            songs.add(song);
        }

        c.close();
        return songs.size() > 0 ? songs : null;
    }

    private AudioInputStream mp3ToWav(AudioInputStream mp3Stream) throws UnsupportedAudioFileException, IOException {
        AudioFormat sourceFormat = mp3Stream.getFormat();
        //Converts the current format with the one matching the .wav files
        AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                sourceFormat.getSampleRate(), 16,
                sourceFormat.getChannels(),
                sourceFormat.getChannels() * 2,
                sourceFormat.getSampleRate(),
                false);

        //System.out.println(convertFormat.getFrameSize());

        AudioInputStream ais = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
        //ais.getFrameLength();
        // Returns the new reconstructed input stream
        return AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
    }
}