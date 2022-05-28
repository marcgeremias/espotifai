package business.entities;

/**
 * Public class that encapsulates information related to a Song
 */
public class Song {

    private int id;
    private String title;
    private String album;
    private Genre genre;
    private String author;
    private String imagePath;
    private int duration;
    private String user;

    /**
     * Constructor for a Song instance
     * @param id int
     * @param title String
     * @param album String
     * @param genre Genre
     * @param author String
     * @param imagePath String
     * @param duration int
     * @param user String
     */
    public Song(int id, String title, String album, Genre genre, String author, String imagePath, int duration, String user) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.author = author;
        this.imagePath = imagePath;
        this.duration = duration;
        this.user = user;
    }

    /**
     * Constructor for Song instance
     * @param title String
     * @param album String
     * @param genre Genre
     * @param author String
     * @param imagePath String
     * @param duration int
     * @param user String
     */
    public Song(String title, String album, Genre genre, String author, String imagePath, int duration, String user){
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.author = author;
        this.imagePath = imagePath;
        this.duration = duration;
        this.user = user;
    }

    /**
     * Getter for id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for title
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for album
     * @return String
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Getter for genre
     * @return Genre instance
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Getter for author
     * @return String
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Getter for image path
     * @return String
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Getter for user
     * @return String
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for duration
     * @return int
     */
    public int getDuration() {
        return duration;
    }
}