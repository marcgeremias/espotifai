package business.entities;

public class Song {

    private int id;
    private String title;
    private String album;
    private Genre genre;
    private String author;
    private String imagePath;
    private int duration;
    private String user;

    // TODO: change user to string
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

    public Song(String title, String album, Genre genre, String author, String imagePath, int duration, String user){
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.author = author;
        this.imagePath = imagePath;
        this.duration = duration;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getUser() {
        return user;
    }

    public int getDuration() {
        return duration;
    }
}