package pw.jor.imgurwallpaper.image;

import pw.jor.Test;
import pw.jor.Tester;
import pw.jor.environment.User;
import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.gui.GUI;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Paths;

/**
 * Writes images to file
 *
 * @author jrobinson
 * @since 10/20/15
 */
public class Writer {

    private static String outputDirectory = null;
    public static String FILE_FORMAT = "jpg";

    private Tester<Container> tester;

    /**
     * Sets up tester to be used on all images
     */
    public Writer () {

        tester = new Tester<>();

        // does the file exists
        tester.addTest(new Test<>(
                iC -> !iC.getFile().exists(),
                iC -> {},
                iC -> GUI.getInstance().println(iC.getOutputPrefix() + "already exists!")
        ));

        // is the image the right size?
        tester.addTest(new Test<>(
                iC -> Constraint.isRightSize(
                        iC.getBufferedImage().getWidth(),
                        iC.getBufferedImage().getHeight()),
                iC -> {},
                iC -> GUI.getInstance().println(iC.getOutputPrefix() + "is not the right size (" +
                        iC.getBufferedImage().getWidth() + " x " + iC.getBufferedImage().getHeight() + ")")
        ));

    }

    /**
     * Downloads and writes image to file
     *
     * @param hash Imgur hash to download
     */
    public void write ( String hash ) {

        // create file name from hash
        String fileName = hash + "." + FILE_FORMAT;

        // container for image content and save location
        Container imageContainer = new Container(
                Downloader.getImage("http://i.imgur.com/" + fileName ),
                new File(Paths.get(getOutputDirectory(), fileName).toString()));

        // write to file is all tests passed
        if ( tester.test(imageContainer) ) {

            try {

                // create file writer
                FileOutputStream writer = new FileOutputStream(imageContainer.getFile().getPath());

                GUI.getInstance().println(imageContainer.getOutputPrefix() + "DOWNLOADING");

                // attempt to write file
                try {
                    ImageIO.write(
                            imageContainer.getBufferedImage(),
                            FILE_FORMAT,
                            imageContainer.getFile());
                } catch (IOException e) {
                    GUI.getInstance().println("Error reading or writing image: " + imageContainer.getFile().getName());
                    GUI.getInstance().println(e.getMessage());
                }

                // close the writer
                try {
                    writer.close();
                } catch (IOException e) {
                    GUI.getInstance().println("Error closing reader or writer");
                }
            } catch (FileNotFoundException e) {
                GUI.getInstance().println("Can't open file for writing");
            }
        }
    }

    /**
     * Gets and creates directory for writing images
     *
     * @return directory to write images
     */
    private static String getOutputDirectory() {

        if ( outputDirectory == null ){

            // get the directory
            outputDirectory =
                    Paths.get(
                            User.getUserPicturesFolder().toString(),
                            "Wallpapers").toString();


            // ensure folder exists
            if ( ! new File(outputDirectory).mkdir() ) {
                GUI.getInstance().error("Error creating output directory : " + outputDirectory);
            }

        }

        return outputDirectory;

    }
}
