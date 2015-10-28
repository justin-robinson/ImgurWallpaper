package pw.jor.environment;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.SystemUtils;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;



/**
 * OS User helper methods
 *
 * @author jrobinson
 * @since 10/23/15
 */
public class User {

    /**
     * Returns Pictures library folder in Win7+ otherwise the Pictures folder in a user's home directory
     * @return full path to current user's pictures folder
     */
    public static Path getUserPicturesFolder () {

        Path picturePath;

        if ( SystemUtils.IS_OS_WINDOWS ) {

            // read windows registry for the location of the pictures library
            String pictureVariable = Advapi32Util.registryGetStringValue(
                    WinReg.HKEY_CURRENT_USER,
                    "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders",
                    "My Pictures"
            );

            // expand environement variables in path
            pictureVariable = Variable.expand(pictureVariable, Variable.WINDOWS_CMD);

            picturePath = Paths.get(pictureVariable);

        } else {
            picturePath = Paths.get(SystemUtils.USER_HOME, "Pictures");
        }

        return picturePath;
    }
}
