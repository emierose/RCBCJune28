/* 
 *	Created by Ronald Phillip C. Cui Feb 10, 2014
 */
package UnlitechDevFramework.src.ud.framework.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class StringUtil {

    public static String streamToString(InputStream source) throws IOException {

        if (source == null)
            return null;

        String result = "";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(source));

            String line = "";

            while ((line = br.readLine()) != null)
                result += line;

            br.close();
        } catch (IOException e) {
            throw e;
        }
        return result;
    }


    /**
     * @param length - length of the code to be generated
     * @return random generated alphanumeric code with a length of the given parameter.
     */
    public static String generateCode(int length) {
        // TODO Auto-generated method stub

        String codelist = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        String code = "";
        for (int i = 1; i <= length; i++) {
            int count = random.nextInt(codelist.length() - 1);
            code = code.concat("" + codelist.charAt(count));
        }
        return code;
    }

    public static String md5(String message) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String digest = null;

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(message.getBytes("UTF-8"));
        //converting byte array to Hexadecimal String
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        digest = sb.toString();

        return digest;

    }


}
