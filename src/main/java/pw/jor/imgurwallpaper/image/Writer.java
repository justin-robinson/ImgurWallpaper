package pw.jor.imgurwallpaper.image;

import pw.jor.environment.User;
import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.Main;

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

    public static void writeFiles ( ArrayList<String> imageHashes ) {

        // write all images to file
        if(imageHashes.size() > 0){
            Main.gui.println(imageHashes.size() + " images found!");
            File file;
            BufferedImage bufferedImage;
            FileOutputStream writer;
            String filePath;
            int imageNumber = 1;

            int width, height;

            for( String fileName : imageHashes ) {
                fileName += "." + FILE_FORMAT;
                filePath = Paths.get(getOutputFolder(), fileName).toString();
                file=new File(filePath);
                if(!file.exists()){

                    bufferedImage = Downloader.getImage("http://i.imgur.com/"+fileName);
                    width = bufferedImage.getWidth();
                    height = bufferedImage.getHeight();

                    // is this image the right size?
                    if ( Constraint.isRightSize(width, height) ) {
                        try {

                            writer=new FileOutputStream(filePath);

                            Main.gui.println(imageNumber + ". Downloading " + fileName);

                            try {
                                ImageIO.write(
                                        bufferedImage,
                                        FILE_FORMAT,
                                        file);
                            } catch ( IOException e ) {
                                Main.gui.println("Error reading or writing image: " + file.getName());
                            }

                            try {
                                writer.close();
                            } catch ( IOException e ) {
                                Main.gui.println("Error closing reader or writer");
                            }
                        } catch (FileNotFoundException e) {
                            Main.gui.println("Can't open file for writing");
                        }
                    } else {
                        Main.gui.println(imageNumber + ". " + fileName + " does not meet min/max dimensions");
                    }
                }
                else{
                    Main.gui.println(imageNumber + ". " + fileName + " already exists");
                }

                imageNumber++;
            }
            Main.gui.println("Done");
        }
        else{
            //System.out.println("No valid images found");
            Main.gui.println("No valid images found");
        }
    }


    private static String getOutputFolder () {

        if ( outputFolder == null ){
            outputFolder = User.getUserPicturesFolder().toString();

        }

        return outputFolder;

    }
}
