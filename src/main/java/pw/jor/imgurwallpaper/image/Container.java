package pw.jor.imgurwallpaper.image;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by jrobinson on 10/26/15.
 */
public class Container {

    private BufferedImage bufferedImage;
    private int imageNumber;
    private File file;

    public static int imageNumberCounter = 0;

    Container(BufferedImage bufferedImage, File file ) {
        this.bufferedImage = bufferedImage;
        this.file = file;
        this.imageNumber = ++imageNumberCounter;
    }

    public BufferedImage getBufferedImage () {
        return this.bufferedImage;
    }

    public File getFile () {
        return this.file;
    }

    public int getImageNumber () {
        return this.imageNumber;
    }

    public static void resetImageNumberCounter() {
        imageNumberCounter = 0;
    }

    public String getOutputPrefix () {
        return this.getImageNumber() + ". " + this.getFile().getName() + " ";
    }

}
