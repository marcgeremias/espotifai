package business.entities;

public class User {

    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private User(User that){
        this.name = that.name;
        this.email = that.email;
        this.password = that.password;
    }

    public User clone(){
        return new User(this);
    }

    public void addSong(Song song) {
        //
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}