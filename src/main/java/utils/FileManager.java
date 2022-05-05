package utils;

import constants.Constants;

import java.io.File;

/**
 * Utility class used to remove avatars
 *
 * @author Francesco Caldivezzi
 */
public class FileManager {

    /**
     * Removes the avatar of a user
     *
     * @param path    the path to the user avatar
     * @param taxCode the user tax code
     */
    public static void removeAvatar(String path, String taxCode) {
        File fileToremove = new File(path);
        fileToremove.delete();
        fileToremove = new File(Constants.AVATAR_PATH_FOLDER + File.separator + taxCode);
        fileToremove.delete();
    }

}
