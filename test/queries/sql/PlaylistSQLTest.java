package queries.sql;

import business.entities.Playlist;
import business.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import persistence.postgresql.PlaylistSQL;
import persistence.postgresql.SongSQL;
import persistence.postgresql.UserSQL;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistSQLTest {

    /*
    PlaylistID = 11 -> Mambo
     */

    @Test
    public void test1CreatePlaylist(){
        PlaylistSQL sql = new PlaylistSQL();
        UserSQL sqlUser = new UserSQL();

        User user = null;
        try {
            user = sqlUser.getUserByID("Jordi Male");
        } catch (SQLException e){
            e.printStackTrace();
        }

        Playlist playlist1 = new Playlist("Mambo", user);

        try {
            Assertions.assertTrue(sql.createPlaylist(playlist1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2GetPlaylistByID(){
        PlaylistSQL sql = new PlaylistSQL();
        Playlist playlist1 = null;
        Playlist playlist2 = null;
        try {
            playlist1 = sql.getPlaylistByID(11, new UserSQL(), new SongSQL());
            playlist2 = sql.getPlaylistByID(13, new UserSQL(), new SongSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert playlist1 != null;
        assert playlist2 == null;
        Assertions.assertEquals("Mambo", playlist1.getName());
    }

    @Test
    public void test3GetAllPlaylists(){
        PlaylistSQL sql = new PlaylistSQL();
        ArrayList<Playlist> playlists = null;

        try {
            playlists = sql.getAllPlaylists(new UserSQL(), new SongSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert playlists != null;
        Assertions.assertEquals(11, playlists.size());
        Assertions.assertEquals("Mambo", playlists.get(10).getName());
    }

    @Test
    public void test4GetPlaylistsByTitle(){
        PlaylistSQL sql = new PlaylistSQL();
        ArrayList<Playlist> playlists = null;
        ArrayList<Playlist> playlists_n = null;
        try {
            playlists = sql.getPlaylistsByTitle("in",new UserSQL(), new SongSQL());
            playlists_n = sql.getPlaylistsByTitle("z",new UserSQL(), new SongSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert playlists != null;
        assert playlists_n == null;
        Assertions.assertEquals(5, playlists.size());
        Assertions.assertEquals("Chillin", playlists.get(0).getName());
    }

    @Test
    public void test5AddSongToPlaylist(){
        PlaylistSQL sql = new PlaylistSQL();

        try {
            Assertions.assertTrue(sql.addSongToPlaylist(11, 11)); // Correct correct
            Assertions.assertFalse(sql.addSongToPlaylist(11, -1)); //Correct incorrect
            Assertions.assertFalse(sql.addSongToPlaylist(-1, 11)); //Incorrect correct
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test6DeleteSongFromPlaylist(){
        PlaylistSQL sql = new PlaylistSQL();

        try {
            Assertions.assertFalse(sql.deleteSongFromPlaylist(11, -1));
            Assertions.assertFalse(sql.deleteSongFromPlaylist(-1, 11));
            Assertions.assertTrue(sql.deleteSongFromPlaylist(11, 11));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test7UpdatePlaylist(){
        PlaylistSQL sql = new PlaylistSQL();
        Playlist playlist = new Playlist(11, "Mambooo", new User("Armand", "res", "res"));

        try {
            Assertions.assertTrue(sql.updatePlaylist(playlist));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test8DeletePlaylist(){
        PlaylistSQL sql = new PlaylistSQL();

        try {
            Assertions.assertTrue(sql.deletePlaylist(11));
            Assertions.assertFalse(sql.deletePlaylist(-1));
            Assertions.assertFalse(sql.deletePlaylist(200));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
