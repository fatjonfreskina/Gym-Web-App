package utils;

import constants.Constants;

import java.io.File;

/**
 * @author Francesco Caldivezzi
 */
public class FileManager {
    public static void removeAvatar(String path, String taxCode) {
        File fileToremove = new File(path);
        fileToremove.delete();
        fileToremove = new File(Constants.AVATAR_PATH_FOLDER + File.separator + taxCode);
        fileToremove.delete();
    }

}
