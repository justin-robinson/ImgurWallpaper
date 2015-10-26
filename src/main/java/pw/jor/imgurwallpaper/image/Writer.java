package pw.jor.imgurwallpaper.image;

import pw.jor.environment.User;
import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

        Predicate<Container> fileExistsPredicate = iC -> !iC.getFile().exists();
        Consumer<Container> fileExistsConsumer = iC -> Main.gui.println(iC.getOutputPrefix() + "already exists!");

        Predicate<Container> rightSizePredicate = iC -> !Constraint.isRightSize(
                iC.getBufferedImage().getWidth(),
                iC.getBufferedImage().getHeight());
        Consumer<Container> rightSizeConsumer = iC -> Main.gui.println(iC.getOutputPrefix() + "is not the right size");

        for( String fileName : imageHashes ) {
            file = new File(Paths.get(getOutputFolder(), fileName + "." + FILE_FORMAT).toString());
            imageContainer = new Container(
                    Downloader.getImage("http://i.imgur.com/" + file.getName() ),
                    file);

            Tester tester = new Tester();

            // does the file exist already?
            tester.testImage(imageContainer,
                    fileExistsPredicate,
                    fileExistsConsumer);

            // is the image the right size?
            tester.testImage(imageContainer,
                    rightSizePredicate,
                    rightSizeConsumer);

            // write to file is all tests passed
            if ( tester.allPassed() ) {

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
