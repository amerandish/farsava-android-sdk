package farsava.core.util;


import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by MHK on 19/07/18.
 * www.MHKSoft.com
 */
public class FileHelper {
    /**
     * Convert File to BASE64.
     *
     * @param file File to be converted
     * @return BASE64 encoded string
     */
    public static String fileToBase64(File file) throws IOException {
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
