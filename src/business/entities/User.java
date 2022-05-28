package business.entities;

/**
 * Public class for the User entity
 */
public class User {

    private String name;
    private String email;
    private String password;

    /**
     * Default user constructor
     * @param name String
     * @param email String
     * @param password String
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /*
    Private constructor for cloning user
     */
    private User(User that){
        this.name = that.name;
        this.email = that.email;
        this.password = that.password;
    }

    /**
     * This method clones a User instance
     * @return this User cloned instance
     */
    public User clone(){
        return new User(this);
    }

    /**
     * Getter for the name attribute
     * @return name attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the email attribute
     * @return email attribute
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for password attribute
     * @return password attribute
     */
    public String getPassword() {
        return password;
    }
}