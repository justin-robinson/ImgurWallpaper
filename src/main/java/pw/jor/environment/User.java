package pw.jor.environment;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.apache.commons.lang3.SystemUtils;

/**
 * Created by jrob0 on 10/23/2015.
 */
public class User {
    // get user's home folder
    String userDir = System.getenv("USERPROFILE") == null
            ? System.getenv("HOME")
            : System.getenv("USERPROFILE");

    public static Path getUserPicturesFolder () {

        Path pictureFolder;

        if ( SystemUtils.IS_OS_WINDOWS ) {
            // HKEY_CURRENT_USER/SOFTWARE/MicrosoftWindows/CurrentVersion/Explorer/User Shell Folders
            Preferences preferences = Preferences.userRoot();
            preferences = preferences.node("/HKCU/SOFTWARE/Microsoft/Windows/CurrentVersion/Explorer/User Shell Folders");
            String[] chil;
            try {
                chil = preferences.childrenNames();
            } catch ( BackingStoreException e ) {

            }
            String p = preferences.get("My Pictures", "asdf");
            pictureFolder = Paths.get(SystemUtils.USER_HOME, "balsssssss");

        } else {
            pictureFolder = Paths.get(SystemUtils.USER_HOME, "Pictures", "Wallpapers");
        }

        // make folders
        File dir = new File(pictureFolder.toString());
        dir.mkdir();

        return pictureFolder;
    }
}
