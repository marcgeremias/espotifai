package persistence.postgresql;

import business.entities.*;
import persistence.*;
import persistence.config.DBConstants;
import persistence.config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.config.DBConstants.PLAYLIST_COL_ORDER;

/**
 * Public class that implements {@link PlaylistDAO} with SQL persistence in the database configured on start-up.
 * @see Playlist
 * @see PlaylistDAO
 * @see DBConfig
 * @see DBConstants
 */
public class PlaylistSQL implements PlaylistDAO {

    /**
     * This method recieves a new {@link Playlist} object and executes a INSERT statement into the playlist table
     * in the database.
     * @param playlist Instance of {@link Playlist}
     * @return (1) true if the playlist was created successfully or (2) false otherwise.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public boolean createPlaylist(Playlist playlist) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String insertPlaylistSQL = "INSERT INTO " + DBConstants.TABLE_PLAYLIST + "(" + DBConstants.PLAYLIST_COL_TITLE
                    + ", " + DBConstants.COL_ID_NICKNAME + ") VALUES (?, ?)";
            PreparedStatement insertPlaylistSTMT = c.prepareStatement(insertPlaylistSQL);
            insertPlaylistSTMT.setString(1, playlist.getName());
            insertPlaylistSTMT.setString(2, playlist.getOwner());
            int count = insertPlaylistSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    // TODO: És correcte passar els DAO per param?
    /**
     * Public method to get instance of {@link Playlist} given the ID. If the ID is valid, it will return the
     * requested playlist object.
     * @param playlistID Integer containing the unique identifier of the {@link Playlist} we want to retrieve
     * @param userDAO DataAccessObject for reconstructing the User (owner of playlist) in the Playlist object
     * @param songDAO DataAccessObject for reconstructing the List of Song in the Playlist object
     * @return instance of Playlist with all the data filled (included User and list of Song) or <b>null</b>.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public Playlist getPlaylistByID(int playlistID, UserDAO userDAO, SongDAO songDAO) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String selectPlaylistSQL = "SELECT * FROM " + DBConstants.TABLE_PLAYLIST + " WHERE " + DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement selectPlaylistSTMT = c.prepareStatement(selectPlaylistSQL);
            selectPlaylistSTMT.setInt(1, playlistID);
            ResultSet rs1 = selectPlaylistSTMT.executeQuery();
        /*
         |   Col 1      |   Col 2   |   Col 3   |
         | id_playlist  |   title   | id_owner  |
         */
            Playlist playlist = null;
            if (rs1.next()) {
                try {
                    playlist = new Playlist(
                            rs1.getInt(1), // == playlistID
                            rs1.getString(2),
                            rs1.getString(3)
                            //userDAO.getUserByID(rs1.getString(3)),
                            //songDAO.getSongsByPlaylistID(playlistID)
                    );
                } catch (SQLException e) {
            /*
                Because DAO is generic and throws a generic Exception we need to make sure that if Exception is
                thrown that we transform it to a SQLException
             */
                    throw new SQLException(e.getMessage());
                }
            }

            c.close();
            return playlist;
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * Public method to get all instances of {@link Playlist} saved in the database.
     * @return list of instances of Playlist with all the data filled (included User and list of Song) or <b>null</b>.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public ArrayList<Playlist> getAllPlaylists() throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Playlist> playlists = new ArrayList<>();

            String selectPlaylistsSQL = "SELECT * FROM " + DBConstants.TABLE_PLAYLIST;
            PreparedStatement selectPlaylistSTMT = c.prepareStatement(selectPlaylistsSQL);
            ResultSet rs1 = selectPlaylistSTMT.executeQuery();
        /*
         |   Col 1      |   Col 2   |   Col 3   |
         | id_playlist  |   title   | id_owner  |
         */
            return getPlaylists(c, playlists, rs1);
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * This method will return all the playlists found with matching String given in the function parameters.
     * @param title key String with the value to query the playlist
     * @param userDAO DataAccessObject for reconstructing the User (owner of playlist) in the Playlist object
     * @param songDAO DataAccessObject for reconstructing the List of Song in the Playlist object
     * @return (1) List of {@link Playlist} or (2) <b>null</b>.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public ArrayList<Playlist> getPlaylistsByTitle(String title, UserDAO userDAO, SongDAO songDAO) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<Playlist> playlists = new ArrayList<>();

            String selectPlaylistsSQL = "SELECT * FROM " + DBConstants.TABLE_PLAYLIST +
                    " WHERE " + DBConstants.PLAYLIST_COL_TITLE + " ILIKE ?";
            PreparedStatement selectPlaylistSTMT = c.prepareStatement(selectPlaylistsSQL);
            selectPlaylistSTMT.setString(1, "%" + title + "%");
            ResultSet rs1 = selectPlaylistSTMT.executeQuery();
        /*
         |   Col 1      |   Col 2   |   Col 3   |
         | id_playlist  |   title   | id_owner  |
         */
            return getPlaylists(c, playlists, rs1);
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * This method will create a link between a song and a playlist. <b>Note</b> this method will not check if the
     * song is already linked in the database, it is VERY IMPORTANT to control on upper layer if the song is already
     * in the playlist.
     * @param playlistID unique identifier of the playlist
     * @param songID unique identifier of the song
     * @return (1) true if the link has been successfully created or (2) false if it fails.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public boolean addSongToPlaylist(int playlistID, int songID, int order) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String addSongSQL = "INSERT INTO " + DBConstants.TABLE_SONG_PLAYLIST + " VALUES (?, ?, ?)";
            PreparedStatement addSongSTMT = c.prepareStatement(addSongSQL);
            addSongSTMT.setInt(1, playlistID);
            addSongSTMT.setInt(2, songID);
            addSongSTMT.setInt(3, order);
            int count = addSongSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * This method will delete any existing relation between the given identifiers in the song_playlist table.
     * @param playlistID unique identifier of the playlist
     * @param songID unique identifier of the song
     * @return (1) true if the row(s) is deleted successfully or (2) false if it fails.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public boolean deleteSongFromPlaylist(int playlistID, int songID) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String deleteSongSQL = "DELETE FROM " + DBConstants.TABLE_SONG_PLAYLIST
                    + " WHERE " + DBConstants.COL_ID_PLAYLIST + " = ? AND " + DBConstants.COL_ID_SONG + " = ?";
            PreparedStatement deleteSongSTMT = c.prepareStatement(deleteSongSQL);
            deleteSongSTMT.setInt(1, playlistID);
            deleteSongSTMT.setInt(2, songID);
            int count = deleteSongSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * Given the Playlist object, it updates the current values in the database with the new values overriding every
     * field.
     * @param playlist Instance of {@link Playlist} with the new values to be persisted on the database
     * @return (1) true if the query is executed correctly or (2) false if the query fails.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public boolean updatePlaylist(Playlist playlist) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String updatePlaylistSQL = "UPDATE " + DBConstants.TABLE_PLAYLIST +
                    " SET " + DBConstants.PLAYLIST_COL_TITLE + " = ?, " + DBConstants.COL_ID_NICKNAME + " = ? " +
                    "WHERE " + DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement updatePlaylistSTMT = c.prepareStatement(updatePlaylistSQL);
            updatePlaylistSTMT.setString(1, playlist.getName());
            updatePlaylistSTMT.setString(2, playlist.getOwner());  //TODO: canviar a Tell don't ask?
            updatePlaylistSTMT.setInt(3, playlist.getId());
            int count = updatePlaylistSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * Deletes the playlist given by the params. Cascade will be executed automatically.
     * @param playlistID unique identifier of playlist.
     * @return (1) true if the playlist is deleted correctly or (2) false otherwise.
     * @throws PlaylistDAOException if the query fails to execute or the database connection can't be opened.
     */
    @Override
    public boolean deletePlaylist(int playlistID) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String deletePlaylistSQL = "DELETE FROM " + DBConstants.TABLE_PLAYLIST + " WHERE " + DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement deletePlaylistSTMT = c.prepareStatement(deletePlaylistSQL);
            deletePlaylistSTMT.setInt(1, playlistID);
            int count = deletePlaylistSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * Method that gets all the playlists from the current user
     * @param userId the current user on the database
     * @return a playlist arraylist with all the playlists from the current user
     * @throws PlaylistDAOException
     */
    @Override
    public ArrayList<Playlist> getPlaylistByUserID(String userId) throws PlaylistDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String selectPlaylistSQL = "SELECT * FROM " + DBConstants.TABLE_PLAYLIST + " WHERE " + DBConstants.COL_ID_NICKNAME + " = ?";
            PreparedStatement selectPlaylistSTMT = c.prepareStatement(selectPlaylistSQL);
            selectPlaylistSTMT.setString(1, userId);
            ResultSet rs1 = selectPlaylistSTMT.executeQuery();
        /*
         |   Col 1      |   Col 2   |   Col 3   |
         | id_playlist  |   title   | id_owner  |
         */
            ArrayList<Playlist> playlists = getPlaylists(c, new ArrayList<>(), rs1);
            c.close();
            return playlists;

        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * This method will return the other playlists that are not from a user.
     *
     * @param userId unique identifier of user.
     * @return (1) List of {@link Playlist} or <b>null</b>
     * @throws PlaylistDAOException if there is an error storing the data.
     */
    @Override
    public ArrayList<Playlist> getDifferentPlaylistByUserID(String userId) throws PlaylistDAOException{
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String selectPlaylistSQL = "SELECT * FROM " + DBConstants.TABLE_PLAYLIST + " WHERE " + DBConstants.COL_ID_NICKNAME + " <> ?";
            PreparedStatement selectPlaylistSTMT = c.prepareStatement(selectPlaylistSQL);
            selectPlaylistSTMT.setString(1, userId);
            ResultSet rs1 = selectPlaylistSTMT.executeQuery();
        /*
         |   Col 1      |   Col 2   |   Col 3   |
         | id_playlist  |   title   | id_owner  |
         */
            ArrayList<Playlist> playlists = getPlaylists(c, new ArrayList<>(), rs1);
            c.close();
            return playlists;

        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }

    /**
     * This method will return the song index order from a playlist.
     *
     * @param playlistId unique identifier of playlist.
     * @return (1) List of song order or <b>null</b>
     * @throws PlaylistDAOException if there is an error storing the data.
     */
    @Override
    public ArrayList<Integer> getSongOrderByPlaylistId(int playlistId) throws PlaylistDAOException{
        try {
            ArrayList<Integer> arrayListOrder = new ArrayList<>();

            Connection c = DBConfig.getInstance().openConnection();

            String selectPlaylistSQL = "SELECT ps." + DBConstants.PLAYLIST_COL_ORDER + "  FROM "
                    + DBConstants.TABLE_SONG_PLAYLIST + " AS ps WHERE ps." + DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement selectPlaylistSTMT = c.prepareStatement(selectPlaylistSQL);
            selectPlaylistSTMT.setInt(1, playlistId);
            ResultSet rs1 = selectPlaylistSTMT.executeQuery();

            while (rs1.next()) {
                arrayListOrder.add(rs1.getInt(1));
            }

            c.close();
            return arrayListOrder;

        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());
        }
    }


    /*
    Private method to get playlist object data from result set
     */
    private ArrayList<Playlist> getPlaylists(Connection c, ArrayList<Playlist> playlists, ResultSet rs1) throws SQLException {
        try {
            while (rs1.next()) {
                Playlist playlist = new Playlist(
                        rs1.getInt(1), // == playlistID
                        rs1.getString(2),
                        rs1.getString(3)
                        //userDAO.getUserByID(rs1.getString(3)),
                        //songDAO.getSongsByPlaylistID(rs1.getInt(1))
                );
                playlists.add(playlist);
            }
        } catch (SQLException e){

            throw new SQLException(e.getMessage());
        }

        c.close();
        return playlists.size() > 0 ? playlists : null;
    }

    /**
     * This method swaps the idSong1 order with the idSong2 inside the playlist selected.
     *
     * @param idPlaylist unique identifier of playlist.
     * @param idSong1 song1 id to swap
     * @param idSong2 song2 id to swap
     * @throws PlaylistDAOException if there is an error storing the data.
     */
    @Override
    public void swapSongsOrder(int idPlaylist, int idSong1, int idSong2) throws PlaylistDAOException{
        try {
            Connection c = DBConfig.getInstance().openConnection();
            int prevOrder1 = 0;
            int prevOrder2 = 0;

            String select1 = "SELECT \"" + PLAYLIST_COL_ORDER + "\" FROM " + DBConstants.TABLE_SONG_PLAYLIST + " WHERE " +
                    DBConstants.COL_ID_SONG + " = ? AND " + DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement select1STMT = c.prepareStatement(select1);
            select1STMT.setInt(1, idSong1);
            select1STMT.setInt(2, idPlaylist);
            ResultSet rs1 = select1STMT.executeQuery();
            if (rs1.next()) {
                prevOrder1 = rs1.getInt(1);
            }

            String select2 = "SELECT \"" + PLAYLIST_COL_ORDER + "\" FROM " + DBConstants.TABLE_SONG_PLAYLIST + " WHERE " +
                    DBConstants.COL_ID_SONG + " = ? AND " + DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement select2STMT = c.prepareStatement(select2);
            select2STMT.setInt(1, idSong2);
            select2STMT.setInt(2, idPlaylist);
            ResultSet rs2 = select2STMT.executeQuery();
            if (rs2.next()) {
                prevOrder2 = rs2.getInt(1);
            }

            String update1 = "UPDATE "+ DBConstants.TABLE_SONG_PLAYLIST + " SET \"" + PLAYLIST_COL_ORDER + "\" = ? " +
                    "WHERE " + DBConstants.COL_ID_SONG + " = ? AND "+ DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement update1STMT = c.prepareStatement(update1);
            update1STMT.setInt(1, prevOrder2);
            update1STMT.setInt(2, idSong1);
            update1STMT.setInt(3, idPlaylist);
            update1STMT.execute();

            String update2 = "UPDATE "+ DBConstants.TABLE_SONG_PLAYLIST + " SET \"" + PLAYLIST_COL_ORDER + "\" = ? " +
                    "WHERE " + DBConstants.COL_ID_SONG + " = ? AND "+ DBConstants.COL_ID_PLAYLIST + " = ?";
            PreparedStatement update2STMT = c.prepareStatement(update2);
            update2STMT.setInt(1, prevOrder1);
            update2STMT.setInt(2, idSong2);
            update2STMT.setInt(3, idPlaylist);
            update2STMT.execute();

            c.close();
        } catch (SQLException e) {
            throw new PlaylistDAOException(e.getMessage());

        }
    }


}