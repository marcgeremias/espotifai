package business;

import java.util.Base64;

/**
 * Public class to encode and decode Strings for the database system
 */
public class Crypt {

    /**
     * This method will encode a given string
     * @param str String to encode
     * @return encoded String instance
     */
    public static String encode(String str){
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode(str.getBytes());
        return new String(encoded);
    }

    /**
     * This method will decode a given string to do further checks
     * @param str String to decode
     * @return new String with the decoded values
     */
    public static String decode(String str){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(str);
        return new String(decoded);
    }
}
