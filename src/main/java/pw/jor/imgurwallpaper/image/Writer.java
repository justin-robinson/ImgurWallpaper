package pw.jor.imgurwallpaper.image;

import pw.jor.Test;
import pw.jor.Tester;
import pw.jor.environment.User;
import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.Main;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by jrobinson on 10/20/15.
 */
public class Writer {

    private static String outputFolder = null;
    public static String FILE_FORMAT = "jpg";

    public static void writeFiles ( ArrayList<String> imageHashes ) {

        // write all images to file
        Main.gui.println(imageHashes.size() + " images found!");
        Container imageContainer;
        File file;

        Container.resetImageNumberCounter();

        Tester tester = new Tester();

        // does the file exists
        tester.addTest(new Test<Container>(
                        iC -> !iC.getFile().exists(),
                        iC -> Main.gui.println(iC.getOutputPrefix() + "already exists!")
        ));

        // is the image the right size?
        tester.addTest(new Test<Container>(
                iC -> !Constraint.isRightSize(
                        iC.getBufferedImage().getWidth(),
                        iC.getBufferedImage().getHeight()),
                iC -> Main.gui.println(iC.getOutputPrefix() + "is not the right size")
        ));

        for( String fileName : imageHashes ) {
            file = new File(Paths.get(getOutputFolder(), fileName + "." + FILE_FORMAT).toString());
            imageContainer = new Container(
                    Downloader.getImage("http://i.imgur.com/" + file.getName() ),
                    file);

            // write to file is all tests passed
            if ( tester.test(imageContainer) ) {

                try {

                    FileOutputStream writer = new FileOutputStream(imageContainer.getFile().getPath());

                    Main.gui.println(imageContainer.getOutputPrefix() + "DOWNLOADING");

                    try {
                        ImageIO.write(
                                imageContainer.getBufferedImage(),
                                FILE_FORMAT,
                                imageContainer.getFile());
                    } catch (IOException e) {
                        Main.gui.println("Error reading or writing image: " + imageContainer.getFile().getName());
                        Main.gui.println(e.getMessage());
                    }

                    try {
                        writer.close();
                    } catch (IOException e) {
                        Main.gui.println("Error closing reader or writer");
                    }
                } catch (FileNotFoundException e) {
                    Main.gui.println("Can't open file for writing");
                }
            }


        }
        Main.gui.println("Done");
    }

    private static String getOutputFolder () {

        if ( outputFolder == null ){
            outputFolder = User.getUserPicturesFolder().toString();

        }

        return outputFolder;

    }
}
