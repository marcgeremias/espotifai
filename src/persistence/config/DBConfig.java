package persistence.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

/**
 * Singleton class for loading database configuration variables
 */
public class DBConfig {

    private static DBConfig sDBConfig;

    private static final String PATH = "./res/config.json";
    private static final String JDBC_POSTGRES_PROTOCOL = "jdbc:postgresql://";

    private String dbHost;
    private int dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    /**
     * This method is called to get the DBConfig instance as a singleton class. If the class has already
     * been instanced it will return the previous instance
     * @return  instance of {@link DBConfig}
     */
    public static DBConfig getInstance(){
        if (sDBConfig == null){
            try {
                sDBConfig = new DBConfig();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return sDBConfig;
    }

    /*
        This private constructor will try to open a file and will throw an exception if file can't be accessed.
        Otherwise it will load the data from the file to the private attributes of the class
     */
    private DBConfig() throws IOException{
        FileReader fr = new FileReader(PATH);
        /*
            Next line reads JSON file and creates an array, we then access the first position, and
            we convert it to a JSON object to retrieve the data
        */
        JsonObject data = JsonParser.parseReader(fr).getAsJsonArray().get(0).getAsJsonObject();

        this.dbHost = data.get("db_host").getAsString();
        this.dbPort = data.get("db_port").getAsInt();
        this.dbName = data.get("db_name").getAsString();
        this.dbUser = data.get("db_user").getAsString();
        this.dbPassword = data.get("db_password").getAsString();
    }

    /**
     * Getter for dbHost private attribute
     * @return String containing database host URL
     */
    public String getDbHost() {
        return dbHost;
    }

    /**
     * Getter for dbPort private attribute
     * @return int containing database port connection
     */
    public int getDbPort() {
        return dbPort;
    }

    /**
     * Getter for dbName private attribute
     * @return String containing database name
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Getter for dbUser private attribute
     * @return String containing database username connection
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * Getter for dbPassword private attribute
     * @return String containing database password for username
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * Getter for URL shortcut for JDBC connection function
     * @return String containing all the necessary information to connect with JDBC
     */
    public String getConnectionURL(){
        return JDBC_POSTGRES_PROTOCOL + "" + dbHost + ":" + dbPort + "/" + dbName;
    }
}
