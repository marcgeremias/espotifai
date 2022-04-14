package queries.sql;

import business.entities.Genre;
import business.entities.Song;
import business.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import persistence.UserDAO;
import persistence.postgresql.SongSQL;
import persistence.postgresql.UserSQL;

import java.sql.SQLException;
import java.util.ArrayList;

@Deprecated
public class SongSQLTest {

    /*
    This test cannot be performed on a existing database, it must be performed on empty new database.
    Indexes must be changed.
     */

    @Test
    public void test1CreateSong(){
        ArrayList<Song> songs = getSampleSongs();
        SongSQL sql = new SongSQL();

        for(Song song : songs){
            try {
                Assertions.assertTrue(sql.createSong(song));
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test2GetSongByID(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();

        Song song1 = null; // Correct identifier
        Song song2 = null; // Incorrect identifier

        try {
            song1 = sql.getSongByID(11, userDAO);
            song2 = sql.getSongByID(-1, userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(song1);
        Assertions.assertNull(song2);
        Assertions.assertEquals("Under the Bridge", song1.getTitle());
    }

    @Test
    public void test3GetSongsByPlaylistID(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();
        ArrayList<Song> songs = null;

        try {
            songs = sql.getSongsByPlaylistID(1, userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert songs != null;
        Assertions.assertEquals("Exotic Action", songs.get(0).getTitle());
    }

    @Test
    public void test4GetSongsByUserID(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();
        ArrayList<Song> songsArmand = null;
        ArrayList<Song> songsInvalid = null;

        try {
            songsArmand = sql.getSongsByUserID("Armand", userDAO);
            songsInvalid = sql.getSongsByUserID("armandddd", userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert songsArmand != null;
        assert songsInvalid == null;
        Assertions.assertEquals(7, songsArmand.size()); // During the test there are 7 songs added by user Armand
    }

    @Test
    public void test5GetSongsByAuthorName(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();
        ArrayList<Song> songsTrueno = null;
        ArrayList<Song> songsInvalid = null;

        try {
            songsTrueno = sql.getSongsByAuthorName("Trueno", userDAO);
            songsInvalid = sql.getSongsByUserID("armandddd", userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert songsTrueno != null;
        assert songsInvalid == null;
        Assertions.assertEquals(1, songsTrueno.size());
    }

    @Test
    public void test6GetSongsByTitle(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();
        ArrayList<Song> songs1 = null;
        ArrayList<Song> songsInvalid = null;

        try {
            songs1 = sql.getSongsByTitle("Dream On", userDAO);
            songsInvalid = sql.getSongsByUserID("armandddd", userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert songs1 != null;
        assert songsInvalid == null;
        Assertions.assertEquals(1, songs1.size());
    }

    @Test
    public void test7GetSongsByAlbum(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();
        ArrayList<Song> songs1 = null;
        ArrayList<Song> songsInvalid = null;

        try {
            songs1 = sql.getSongsByAlbum("SOUR", userDAO);
            songsInvalid = sql.getSongsByUserID("armandddd", userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert songs1 != null;
        assert songsInvalid == null;
        Assertions.assertEquals(1, songs1.size());
    }

    @Test
    public void test8GetSongsByGenre(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();
        ArrayList<Song> songs1 = null;
        ArrayList<Song> songsInvalid = null;

        try {
            songs1 = sql.getSongsByGenre(Genre.ROCK, userDAO);
            songsInvalid = sql.getSongsByUserID("armandddd", userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert songs1 != null;
        assert songsInvalid == null;
        Assertions.assertEquals(3, songs1.size());
    }

    @Test
    public void test9GetSongsByKeyword(){
        SongSQL sql = new SongSQL();
        UserDAO userDAO = new UserSQL();
        ArrayList<Song> songs1 = null;
        ArrayList<Song> songsInvalid = null;

        try {
            songs1 = sql.getSongsByKeyword("ra", userDAO);
            songsInvalid = sql.getSongsByUserID("armandddd", userDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert songs1 != null;
        assert songsInvalid == null;
        Assertions.assertEquals(3, songs1.size());
    }

    @Test
    public void test10UpdateSong(){
        SongSQL sql = new SongSQL();
        User armand = new User("Armand", "ar.daussa@gmail.com", "randomstring");

        Song song1 = new Song(11,"The Power Of Equality", "Blood Sugar Sex Magik", Genre.ROCK, "Red Hot Chili Peppers","no path", 242, armand);
        Song song_wrong = new Song(234,"The Power Of Equality", "Blood Sugar Sex Magik", Genre.ROCK, "Red Hot Chili Peppers","no path", 242, armand);

        Song check = null;
        try {
            Assertions.assertFalse(sql.updateSong(song_wrong)); //Wrong ID
            Assertions.assertTrue(sql.updateSong(song1));
            check = sql.getSongByID(song1.getId(), new UserSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert check != null;
        Assertions.assertNotEquals("Under the Bridge", check.getTitle());
    }

    @Test
    public void test11DeleteSong(){
        SongSQL sql = new SongSQL();

        try {
            Assertions.assertTrue(sql.deleteSong(17));
            Assertions.assertNull(sql.getSongByID(17, new UserSQL()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Ensure that user nickname exists in the database
    private ArrayList<Song> getSampleSongs(){
        UserSQL sql = new UserSQL();
        ArrayList<Song> songs = new ArrayList<>();

        User armand = null;
        try {
            armand = sql.getUserByID("Armand");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Song song1 = new Song(11,"Under the Bridge", "Blood Sugar Sex Magik", Genre.ROCK, "Red Hot Chili Peppers","no path", 264, armand);
        Song song2 = new Song(12,"Sweet Home Alabama", "Second Helping", Genre.COUNTRY, "Lynyrd Skynyrd","no path", 284, armand);
        Song song3 = new Song(13,"Dream On", "Aerosmith", Genre.ROCK, "Aerosmith","no path", 267, armand);
        Song song4 = new Song(14,"DANCE CRIP", "DANCE CRIP", Genre.RAP, "Trueno","no path", 165, armand);
        Song song5 = new Song(15,"Benvolgut", "10 milles", Genre.POP, "Manel","no path", 255, armand);
        Song song6 = new Song(17,"drivers license", "SOUR", Genre.POP, "Olivia Rodrigo","no path", 242, armand);

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(song5);
        songs.add(song6);

        return songs;
    }

}
