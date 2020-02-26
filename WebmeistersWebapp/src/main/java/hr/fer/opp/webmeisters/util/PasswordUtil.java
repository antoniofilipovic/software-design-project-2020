package hr.fer.opp.webmeisters.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String passwordEncrypt(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] buff = pass.getBytes();
            md.update(buff, 0, buff.length);
            return bytetohex(md.digest());
        } catch(NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static byte[] hextobyte(String keyText) {

        if(keyText.length()%2 == 1) {
            throw new IllegalArgumentException("Invalid string!");
        }
        int arrayLength = keyText.length()/2;
        byte[] bytes = new byte[arrayLength];
        for(int i = 0; i < keyText.length(); i += 2) {
            int first = Character.digit(keyText.charAt(i), 16);
            int second = Character.digit(keyText.charAt(i+ 1), 16);
            if(first == -1 || second == -1) {
                throw new IllegalArgumentException("Invalid string!");
            }
            bytes[i/2] =  (byte) ((first << 4) + second);
        }

        return bytes;
    }

    public static String bytetohex(byte[] bytearray) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytearray.length; i++) {
            sb.append(String.format("%02x", bytearray[i] & 0xff));
        }
        return sb.toString();
    }

    public static String[] extractPathInfo(String path) {
        String info = path.substring(1);
        String[] parts = info.split("/");
        return parts;
    }
}
