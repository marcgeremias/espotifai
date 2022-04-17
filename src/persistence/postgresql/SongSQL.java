package persistence.postgresql;

import business.entities.Genre;
import business.entities.Song;
import persistence.SongDAO;
import persistence.UserDAO;
import persistence.config.DBConstants;
import persistence.config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Public class that implements {@link SongDAO} interface with SQL persistence in the database configured on start-up.
 * @see Song
 * @see SongDAO
 * @see DBConstants
 * @see DBConfig
 */
public class SongSQL implements SongDAO {

    /**
     * Public method to create a new song in the database. An instance of {@link Song} is passed as a parameter and
     * the values are persisted on the database. Note that all the fields will be edited so it's important to make
     * sure that all important fields are not empty.
     * @param song instance of {@link Song} containing the values to add
     * @return true (1) if the song has been created correctly or false (2) if the song hasn't been created correctly.
     * @throws SQLException if the query fails to execute or the database connection can't be opened
     */
    @Override
    public boolean createSong(Song song) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();

        String createSongSQL = "INSERT INTO "+ DBConstants.TABLE_SONG +"("+ DBConstants.COL_ID_NICKNAME
                +", "+ DBConstants.SONG_COL_ALBUM +", "+ DBConstants.SONG_COL_AUTHOR +", "+ DBConstants.SONG_COL_GENRE
                +", "+ DBConstants.SONG_COL_PATH +", "+ DBConstants.SONG_COL_TITLE +", "+ DBConstants.SONG_COL_DURATION+")"
                +" VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement createSongSTMT = c.prepareStatement(createSongSQL);
        createSongSTMT.setString(1, song.getUser().getName());
        createSongSTMT.setString(2, song.getAlbum());
        createSongSTMT.setString(3, song.getAuthor());
        createSongSTMT.setString(4, String.valueOf(song.getGenre()));
        createSongSTMT.setString(5, song.getImagePath());
        createSongSTMT.setString(6, song.getTitle());
        createSongSTMT.setInt(7, song.getDuration());
        int count = createSongSTMT.executeUpdate();

        c.close();
        return count > 0;
    }

    /**
     * This method will return one instance of the class {@link Song} if the identifier provided matches with any
     * value stored in the database.
     * @param songID identifier of the song
     * @param userDAO instance of {@link UserDAO} interface to reconstruct the song with its owner included
     * @return instance of {@link Song} with the values retrieved from the database
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public Song getSongByID(int songID, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();

        String selectSongSQL = "SELECT * FROM "+ DBConstants.TABLE_SONG +" WHERE id_song = ?";
        PreparedStatement selectSongSTMT = c.prepareStatement(selectSongSQL);
        selectSongSTMT.setInt(1, songID);
        ResultSet rs = selectSongSTMT.executeQuery();

        Song song = null;
        if(rs.next()) {
            try {
                song = getSongFromResultSet(rs, userDAO);
            } catch (Exception e) {
                throw new SQLException();
            }
        }

        c.close();
        return song;
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param playlistID identifier to query the songs
     * @param userDAO DataAccessObject to reconstruct {@link Song} instance
     * @return List of Songs with all the data from the database
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByPlaylistID(int playlistID, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();
        ArrayList<Song> songs = new ArrayList<>();

        String selectSongsSQL = "SELECT s.* FROM "+ DBConstants.TABLE_SONG +" s, "+ DBConstants.TABLE_SONG_PLAYLIST +" ps " +
                "WHERE s."+ DBConstants.COL_ID_SONG +" = ps."+ DBConstants.COL_ID_SONG
                +" AND ps."+ DBConstants.COL_ID_PLAYLIST +" = ?";
        PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
        selectSongsSTMT.setInt(1, playlistID);
        return getSongs(userDAO, c, songs, selectSongsSTMT);
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param userID identifier to query the songs
     * @param userDAO DataAccessObject to reconstruct {@link Song} instance
     * @return List of Songs with all the data from the database
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByUserID(String userID, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();
        ArrayList<Song> songs = new ArrayList<>();

        String selectSongsSQL = "SELECT * FROM "+ DBConstants.TABLE_SONG
                +" WHERE "+ DBConstants.COL_ID_NICKNAME +" = ?";

        PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
        selectSongsSTMT.setString(1, userID);
        return getSongs(userDAO, c, songs, selectSongsSTMT);
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param authorName identifier to query the songs
     * @param userDAO DataAccessObject to reconstruct {@link Song} instance
     * @return List of Songs with all the data from the database
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByAuthorName(String authorName, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();
        ArrayList<Song> songs = new ArrayList<>();

        String selectSongsSQL = "SELECT * FROM "+ DBConstants.TABLE_SONG
                +" WHERE "+ DBConstants.SONG_COL_AUTHOR +" = ?";

        PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
        selectSongsSTMT.setString(1, authorName);
        return getSongs(userDAO, c, songs, selectSongsSTMT);
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param title identifier to query the songs
     * @param userDAO DataAccessObject to reconstruct {@link Song} instance
     * @return List of Songs with all the data from the database
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByTitle(String title, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();
        ArrayList<Song> songs = new ArrayList<>();

        String selectSongsSQL = "SELECT * FROM "+ DBConstants.TABLE_SONG
                +" WHERE "+ DBConstants.SONG_COL_TITLE +" = ?";

        PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
        selectSongsSTMT.setString(1, title);
        return getSongs(userDAO, c, songs, selectSongsSTMT);
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param album identifier to query the songs
     * @param userDAO DataAccessObject to reconstruct {@link Song} instance
     * @return List of Songs with all the data from the database
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByAlbum(String album, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();
        ArrayList<Song> songs = new ArrayList<>();

        String selectSongsSQL = "SELECT * FROM "+ DBConstants.TABLE_SONG
                +" WHERE "+ DBConstants.SONG_COL_ALBUM +" = ?";

        PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
        selectSongsSTMT.setString(1, album);
        return getSongs(userDAO, c, songs, selectSongsSTMT);
    }

    /**
     * This method will query the database and return all the songs that match the given parameter.
     * @param genre identifier to query the songs
     * @param userDAO DataAccessObject to reconstruct {@link Song} instance
     * @return List of Songs with all the data from the database
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByGenre(Genre genre, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();
        ArrayList<Song> songs = new ArrayList<>();

        String selectSongsSQL = "SELECT * FROM "+ DBConstants.TABLE_SONG
                +" WHERE "+ DBConstants.SONG_COL_GENRE +" = ?";

        PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
        selectSongsSTMT.setString(1, String.valueOf(genre));
        return getSongs(userDAO, c, songs, selectSongsSTMT);
    }

    /**
     * This method will return all the songs that match their title, author name, album name or genre type with the
     * key String given in the function parameters.
     * @param key String key containing the value to search.
     * @param userDAO DataAccessObject to reconstruct {@link Song} instance
     * @return List of {@link Song} if the query finds matches or null if the query doesn't find any matching song.
     * @throws SQLException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public ArrayList<Song> getSongsByKeyword(String key, UserDAO userDAO) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();
        ArrayList<Song> songs = new ArrayList<>();

        String selectSongsSQL = "SELECT * FROM "+ DBConstants.TABLE_SONG
                +" WHERE "+ DBConstants.SONG_COL_AUTHOR +" ILIKE ? OR "+ DBConstants.SONG_COL_GENRE +" ILIKE ? OR "+
                DBConstants.SONG_COL_ALBUM +" ILIKE ? OR "+ DBConstants.SONG_COL_TITLE +" ILIKE ?";

        PreparedStatement selectSongsSTMT = c.prepareStatement(selectSongsSQL);
        selectSongsSTMT.setString(1, "%"+key+"%");
        selectSongsSTMT.setString(2, "%"+key+"%");
        selectSongsSTMT.setString(3, "%"+key+"%");
        selectSongsSTMT.setString(4, "%"+key+"%");
        return getSongs(userDAO, c, songs, selectSongsSTMT);
    }

    /**
     * This method will update the values of the given {@link Song} and overwrite everything. Please note that values
     * that you don't wish to updated must be passed as well as the function will overwrite everything from the row.
     * @param song instance of {@link Song} containing the updated values
     * @return true (1) the song has been updated correctly or false (2) if the given song doesn't exist in the
     * database.
     * @throws SQLException if the query fails to execute correctly or the database can't be opened.
     */
    @Override
    public boolean updateSong(Song song) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();

        String updateSongSQL = "UPDATE "+ DBConstants.TABLE_SONG +" SET " +
                DBConstants.SONG_COL_TITLE +" = ?, "+ DBConstants.SONG_COL_ALBUM +" = ?, "+
                DBConstants.SONG_COL_GENRE +" = ?, "+ DBConstants.SONG_COL_PATH +" = ?, "+
                DBConstants.SONG_COL_AUTHOR +" = ? WHERE "+ DBConstants.COL_ID_SONG +" = ?";
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
    }

    /**
     * This method will delete all rows affected in the database of the given song identifier.
     * @param songID identifier for the song
     * @return true (1) if the song has been deleted correctly or false (2) if the songID doesn't match any
     * identifier stored in the database.
     * @throws SQLException if the query fails to execute correctly or the database can't be opened.
     */
    @Override
    public boolean deleteSong(int songID) throws SQLException {
        Connection c = DBConfig.getInstance().openConnection();

        String deleteSongSQL = "DELETE FROM "+DBConstants.TABLE_SONG +" WHERE "+ DBConstants.COL_ID_SONG +" = ?";
        PreparedStatement deleteSongSTMT = c.prepareStatement(deleteSongSQL);
        deleteSongSTMT.setInt(1, songID);
        int count = deleteSongSTMT.executeUpdate();

        c.close();
        return count > 0;
    }

    /*
    Private method the abstract execution on the main query functions
     */
    private Song getSongFromResultSet(ResultSet rs, UserDAO userDAO) throws Exception{
        /*
        |   Col 1   |   Col 2   |   Col 3   |   Col 4   |   Col 5   |   Col 6   |   Col 7   |   Col 8   |   Col 9   |
        |  id_song  |  title    | duration  |   path    |(not used) |   genre   |   author  |   album   |   id_user |
         */
        return new Song(
                rs.getInt(1), rs.getString(2), rs.getString(8),
                Genre.valueOf(rs.getString(6).toUpperCase(Locale.ROOT)), rs.getString(7),
                rs.getString(4), rs.getInt(3), userDAO.getUserByID(rs.getString(9))
        );
    }

    /*
    Function abstracted from repeated code
     */
    private ArrayList<Song> getSongs(UserDAO userDAO, Connection c, ArrayList<Song> songs, PreparedStatement selectSongsSTMT) throws SQLException {
        ResultSet rs = selectSongsSTMT.executeQuery();

        try {
            while(rs.next()){
                Song song = getSongFromResultSet(rs, userDAO);
                songs.add(song);
            }
        } catch (Exception e){
            throw new SQLException(e.getMessage());
        }

        c.close();
        return songs.size() > 0 ? songs : null;
    }
}