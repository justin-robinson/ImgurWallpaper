package pw.jor.imgurwallpaper.image;

import pw.jor.Test;
import pw.jor.Tester;
import pw.jor.environment.User;
import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.Main;
import pw.jor.imgurwallpaper.gui.GUI;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Writes images to file
 * Created by jrobinson on 10/20/15.
 */
public class Writer {

    private static String outputFolder = null;
    public static String FILE_FORMAT = "jpg";

    private Tester<Container> tester;

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
                iC -> !Constraint.isRightSize(
                        iC.getBufferedImage().getWidth(),
                        iC.getBufferedImage().getHeight()),
                iC -> {},
                iC -> GUI.getInstance().println(iC.getOutputPrefix() + "is not the right size")
        ));

    }

    public void write ( String fileName ) {

        File file = new File(Paths.get(getOutputFolder(), fileName + "." + FILE_FORMAT).toString());
        Container imageContainer = new Container(
                Downloader.getImage("http://i.imgur.com/" + file.getName() ),
                file);

        // write to file is all tests passed
        if ( tester.test(imageContainer) ) {

            try {

                FileOutputStream writer = new FileOutputStream(imageContainer.getFile().getPath());

                GUI.getInstance().println(imageContainer.getOutputPrefix() + "DOWNLOADING");

                try {
                    ImageIO.write(
                            imageContainer.getBufferedImage(),
                            FILE_FORMAT,
                            imageContainer.getFile());
                } catch (IOException e) {
                    GUI.getInstance().println("Error reading or writing image: " + imageContainer.getFile().getName());
                    GUI.getInstance().println(e.getMessage());
                }

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

    private static String getOutputFolder () {

        if ( outputFolder == null ){
            outputFolder = User.getUserPicturesFolder().toString();

        }

        return outputFolder;

    }
}
