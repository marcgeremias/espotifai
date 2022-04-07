package business.entities;

public class Song {
    private String title;
    private String album;
    private Genre genre;
    private String author;
    private String file;
    private User user;

    public Song(String title, String album, Genre genre, String author, String file) {
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.author = author;
        this.file = file;
    }
}