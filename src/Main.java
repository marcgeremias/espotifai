import business.entities.Playlist;
import persistence.SongDAO;
import persistence.UserDAO;
import persistence.config.DBConfig;
import persistence.postgresql.PlaylistSQL;
import persistence.postgresql.SongSQL;
import persistence.postgresql.UserSQL;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //Bondia
        System.out.println("Hello World");
        PlaylistSQL sql = new PlaylistSQL();
        UserDAO userdao = new UserSQL();
        SongDAO songdao = new SongSQL();
        try {
            sql.getPlaylistByID(1, userdao, songdao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}