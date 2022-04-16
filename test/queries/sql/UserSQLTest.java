package queries.sql;

import business.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import persistence.postgresql.UserSQL;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserSQLTest {

    /**
     * This method should be called if we desire to test all users because in this case order matters as some rows
     * of the database may not exist and are needed in this test.
     * <b>IMPORTANT</b>: make sure that there is not a user in the database with the nickname = 'test1' because
     * the tests will fail.
     */
    @Test
    public void testUserSQL(){
        test1CreateUser();
        test2SelectUserByID();
        test3GetAllUsers();
        test4UpdateUser();
        test5ValidateUser();
        test6DeleteUser();
    }

    @Test
    public void test1CreateUser(){
        UserSQL sql = new UserSQL();

        try {
            Assertions.assertTrue(sql.createUser(new User("test1", "test1@gmail.com", "test1")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2SelectUserByID(){
        UserSQL sql = new UserSQL();
        User user1 = null;
        User user2 = null;
        try {
            user1 = sql.getUserByID("test1");
            user2 = sql.getUserByID("test1111");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert user1 != null;
        assert user2 == null;
        Assertions.assertEquals("test1", user1.getName());
        Assertions.assertEquals("test1@gmail.com", user1.getEmail());
        Assertions.assertNotEquals("test1", user1.getPassword()); //Password should be encrypted
    }

    @Test
    public void test3GetAllUsers(){
        UserSQL sql = new UserSQL();

        ArrayList<User> users = null;
        try {
            users = sql.getAllUsers();
        } catch (SQLException e){
            e.printStackTrace();
        }

        assert users != null;
        Assertions.assertEquals("Guillem", users.get(0).getName()); // Corresponding of the row in the database
        Assertions.assertEquals("Claudia", users.get(4).getName());
        Assertions.assertEquals("Armand", users.get(9).getName());
    }

    @Test
    public void test4UpdateUser(){
        UserSQL sql = new UserSQL();

        // New values to modify (nickname must remain equal as it is the identifier for the user)
        User updated = new User("test1", "test1_updated@gmail.com", "test1");
        User updated_wrong = new User("test1111", "test1_updated@gmail.com", "test1");

        //We will update the password of user test1 in the database
        User test1 = null; // Test1 contains originar test1
        User test1_updated = null; //test1_updated contains updated test1
        try {
            test1 = sql.getUserByID("test1");

            Assertions.assertFalse(sql.updateUser(updated_wrong)); // Check for incorrect nickname
            Assertions.assertTrue(sql.updateUser(updated));

            test1_updated = sql.getUserByID("test1");
        } catch (SQLException e){
            e.printStackTrace();
        }

        assert test1 != null;
        assert test1_updated != null;
        // Only email has been updated so we check if they are actually diferent
        Assertions.assertNotEquals(test1.getEmail(), test1_updated.getEmail());
    }

    @Test
    public void test5ValidateUser(){
        UserSQL sql = new UserSQL();

        /*
        We want to validate the user 'test1' with email 'test1@gmail.com'
         */
        User userValidated1 = null; // Good credentials nickname
        User userValidated2 = null; // Bad credentials nickname
        User userValidated3 = null; // Good credentials email
        User userValidated4 = null; // Bad credentials email
        try {
            userValidated1 = sql.validateUser("test1");
            userValidated2 = sql.validateUser("test1111");
            userValidated3 = sql.validateUser("test1_updated@gmail.com"); // We updated the email in last test
            userValidated4 = sql.validateUser("test1@gmail.com"); // Should not exist anymore
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(userValidated1);
        Assertions.assertNull(userValidated2);
        Assertions.assertNotNull(userValidated3);
        Assertions.assertNull(userValidated4);

        Assertions.assertEquals("test1", userValidated1.getName());
        Assertions.assertEquals("test1", userValidated3.getName());

    }

    @Test
    public void test6DeleteUser(){
        UserSQL sql = new UserSQL();

        try {
            Assertions.assertFalse(sql.deleteUser("test1111"));
            User check1 = sql.getUserByID("test1");
            Assertions.assertNotNull(check1);
            Assertions.assertTrue(sql.deleteUser("test1"));
            User check2 = sql.getUserByID("test1");
            Assertions.assertNull(check2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
