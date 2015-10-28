package pw.jor.imgurwallpaper.image;

import pw.jor.imgurwallpaper.Main;
import pw.jor.imgurwallpaper.gui.GUI;

/**
 * Helper method for image tests
 *
 * @author jrobinson
 * @since 10/23/15
 */
public class Constraint {

    /**
     * Tests if image dimensions are in the user provided range
     *
     * 0 values on maxes are treated as unlimited
     *
     * @param width width of image
     * @param height height of image
     * @return image is withing user size constraints
     */
    public static boolean isRightSize(int width, int height) {
        int minWidth = (int) GUI.getInstance().minWidth.getValue();
        int minHeight = (int)GUI.getInstance().minHeight.getValue();
        int maxWidth = (int)GUI.getInstance().maxWidth.getValue() == 0
                        ? Integer.MAX_VALUE
                        : (int)GUI.getInstance().maxWidth.getValue();
        int maxHeight = (int)GUI.getInstance().maxHeight.getValue() == 0
                        ? Integer.MAX_VALUE
                        : (int)GUI.getInstance().maxHeight.getValue();

        return (minWidth <= width && width <= maxWidth &&
                minHeight <= height && height <= maxHeight);
    }
}
