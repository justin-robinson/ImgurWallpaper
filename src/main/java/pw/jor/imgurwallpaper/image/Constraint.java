package pw.jor.imgurwallpaper.image;

import pw.jor.imgurwallpaper.Main;

/**
 * Created by jrobinson on 10/23/15.
 */
public class Constraint {

    public static boolean isRightSize(int width, int height) {
        int minWidth = (int) Main.gui.minWidth.getValue(),
                minHeight = (int)Main.gui.minHeight.getValue(),
                maxWidth = (int)Main.gui.maxWidth.getValue() == 0 ? Integer.MAX_VALUE : (int)Main.gui.maxWidth.getValue(),
                maxHeight = (int)Main.gui.maxHeight.getValue() == 0 ? Integer.MAX_VALUE : (int)Main.gui.maxHeight.getValue();

        return (minWidth <= width && width <= maxWidth &&
                minHeight <= height && height <= maxHeight);
    }
}
