package pw.jor.environment;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.SystemUtils;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;



/**
 * Created by jrob0 on 10/23/2015.
 */
public class User {

    public static Path getUserPicturesFolder () {

        Path picturePath;

        if ( SystemUtils.IS_OS_WINDOWS ) {
            String key = "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders";
            String value = "My Pictures";

            String pictureVariable = Advapi32Util.registryGetStringValue(
                    WinReg.HKEY_CURRENT_USER,
                    key,
                    value
            );

            pictureVariable = Variable.expand(pictureVariable);

            picturePath = Paths.get(pictureVariable, "Wallpapers");

        } else {
            picturePath = Paths.get(SystemUtils.USER_HOME, "Pictures", "Wallpapers");
        }

        // make folders
        File dir = new File(picturePath.toString());
        dir.mkdir();

        return picturePath;
    }
}
