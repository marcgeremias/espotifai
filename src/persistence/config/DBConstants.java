package persistence.config;

public class DBConstants {

    public static final String TABLE_USER = "public.\"user\"";
    public static final String TABLE_SONG = "song";
    public static final String TABLE_PLAYLIST = "playlist";
    public static final String TABLE_SONG_PLAYLIST = "playlist_song";

    // Primary Keys
    public static final String COL_ID_SONG = "id_song";
    public static final String COL_ID_NICKNAME = "id_nickname";
    public static final String COL_ID_PLAYLIST = "id_playlist";

    // Columns for SONG table
    public static final String SONG_COL_ALBUM = "album";
    public static final String SONG_COL_AUTHOR = "author";
    public static final String SONG_COL_GENRE = "genre";
    public static final String SONG_COL_PATH = "image_path";
    public static final String SONG_COL_DURATION = "duration";
    public static final String SONG_COL_TITLE = "title";

    // Columns for USER table
    public static final String USER_COL_PASSWORD = "password";
    public static final String USER_COL_MAIL = "mail";

    // Columns for PLAYLIST table
    public static final String PLAYLIST_COL_TITLE = "title";
    public static final String PLAYLIST_COL_ORDER = "order";


}
