package pw.jor.imgurwallpaper.image;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Container for an image and it's output path
 *
 * @author jrobinson
 * @since 10/26/15
 */
public class ImageContainer {

    private BufferedImage bufferedImage;
    private int imageNumber;
    private File file;

    public static int imageNumberCounter = 0;

    /**
     * Constructor
     *
     * @param bufferedImage image to save
     * @param file where to save image
     */
    public ImageContainer(BufferedImage bufferedImage, File file) {
        this.bufferedImage = bufferedImage;
        this.file = file;
        this.imageNumber = ++imageNumberCounter;
    }

    /**
     * Gets contained image
     *
     * @return contained image
     */
    public BufferedImage getBufferedImage () {
        return this.bufferedImage;
    }

    /**
     * gets file where image will be saved
     *
     * @return file where image will be saved
     */
    public File getFile () {
        return this.file;
    }

    /**
     * Gets image number
     *
     * @return image number
     */
    public int getImageNumber () {
        return this.imageNumber;
    }

    /**
     * Resets image number counter
     */
    public static void resetImageNumberCounter() {
        imageNumberCounter = 0;
    }

    /**
     * Gets prefix for gui and stdout messages
     *
     * @return prefix for gui and stdout messages
     */
    public String getOutputPrefix () {
        return this.getImageNumber() + ". " + this.getFile().getName() + " ";
    }

}
