package business;

import persistence.SongDAO;
import persistence.postgresql.SongSQL;

public class SongManager {
    private SongDAO songDAO;

    public SongManager(SongDAO songDAO) {
        this.songDAO = songDAO;
    }
}