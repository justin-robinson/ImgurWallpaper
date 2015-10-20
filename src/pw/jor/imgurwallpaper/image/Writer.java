package pw.jor.imgurwallpaper.image;

import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.gui.GUI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by jrobinson on 10/20/15.
 */
public class Writer {

    private static String outputFolder = null;
    public static String FILE_FORMAT = "jpg";

    public static void writeFiles ( ArrayList<String> imageHashes, GUI gui ) {

        // write all images to file
        if(imageHashes.size() > 0){
            gui.println(imageHashes.size() + " images found!");
            File file;
            BufferedImage bufferedImage;
            FileOutputStream writer;
            String filePath;
            int imageNumber = 1;

            int width, height,
                    minWidth = (int)gui.minWidth.getValue(),
                    minHeight = (int)gui.minHeight.getValue(),
                    maxWidth = (int)gui.maxWidth.getValue() == 0 ? Integer.MAX_VALUE : (int)gui.maxWidth.getValue(),
                    maxHeight = (int)gui.maxHeight.getValue() == 0 ? Integer.MAX_VALUE : (int)gui.maxHeight.getValue();

            for( String fileName : imageHashes ){
                fileName += "." + FILE_FORMAT;
                filePath = Paths.get(getOutputFolder(), fileName).toString();
                file=new File(filePath);
                if(!file.exists()){

                    bufferedImage = Downloader.getImage("http://i.imgur.com/"+fileName, gui);
                    width = bufferedImage.getWidth();
                    height = bufferedImage.getHeight();

                    // is this image the right size?
                    if ( minWidth <= width && width <= maxWidth &&
                         minHeight <= height && height <= maxHeight ) {
                        try {

                            writer=new FileOutputStream(filePath);

                            gui.println(imageNumber + ". Downloading " + fileName);

                            try {
                                ImageIO.write(
                                        bufferedImage,
                                        FILE_FORMAT,
                                        file);
                            } catch ( IOException e ) {
                                gui.println("Error reading or writing image: " + file.getName());
                            }

                            try {
                                writer.close();
                            } catch ( IOException e ) {
                                gui.println("Error closing reader or writer");
                            }
                        } catch (FileNotFoundException e) {
                            gui.println("Can't open file for writing");
                        }
                    } else {
                        gui.println(imageNumber + ". " + fileName + " does not meet min/max dimensions");
                    }
                }
                else{
                    gui.println(imageNumber + ". " + fileName + " already exists");
                }

                imageNumber++;
            }
        }
        else{
            //System.out.println("No valid images found");
            gui.println("No valid images found");
        }
    }


    private static String getOutputFolder () {

        if ( outputFolder == null ){
            // get user's home folder
            String userDir = System.getenv("USERPROFILE") == null
                    ? System.getenv("HOME")
                    : System.getenv("USERPROFILE");

            // append subfolders
            Path outputPath = Paths.get(userDir, "Pictures", "Wallpapers");

            // get path string
            outputFolder = outputPath.toString();

            // make folders
            File dir = new File(outputFolder);
            dir.mkdir();

        }

        return outputFolder;

    }
}
