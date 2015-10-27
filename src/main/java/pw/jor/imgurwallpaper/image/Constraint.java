package pw.jor.imgurwallpaper.image;

import pw.jor.imgurwallpaper.Main;
import pw.jor.imgurwallpaper.gui.GUI;

/**
 * Created by jrobinson on 10/23/15.
 */
public class Constraint {

    public static boolean isRightSize(int width, int height) {
        int minWidth = (int) GUI.getInstance().minWidth.getValue(),
                minHeight = (int)GUI.getInstance().minHeight.getValue(),
                maxWidth = (int)GUI.getInstance().maxWidth.getValue() == 0 ? Integer.MAX_VALUE : (int)GUI.getInstance().maxWidth.getValue(),
                maxHeight = (int)GUI.getInstance().maxHeight.getValue() == 0 ? Integer.MAX_VALUE : (int)GUI.getInstance().maxHeight.getValue();

        return (minWidth <= width && width <= maxWidth &&
                minHeight <= height && height <= maxHeight);
    }
}
