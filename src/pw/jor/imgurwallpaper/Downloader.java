package pw.jor.imgurwallpaper;

import pw.jor.imgurwallpaper.gui.GUI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.URL;


/**
 * All download logic is here
 */
public class Downloader {

    private static final String DOWNLOAD_LIST_URL = "https://jor.pw/downloads/walls.txt";

    /**
     * gets the contents of a url
     * @param url
     * @return
     */
    public static String getPageContents ( String url ) {
        String page="";
        try{
            page = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
        }catch (MalformedURLException e){
            Main.gui.println("URL is invalid");
        }catch(IOException e){
            Main.gui.println("404 Page Not Found");
        }catch(IllegalArgumentException e){
            Main.gui.println("URL is invalid");
        }

        return page;
    }

    /**
     * Buffers url into image
     * @param urlString
     * @return
     */
    public static BufferedImage getImage ( String urlString ) {

        BufferedImage bufferedImage = null;

        try{
            URL url = new URL(urlString);
            bufferedImage = ImageIO.read(url);
        } catch ( MalformedURLException e ) {
            Main.gui.println("URL is invalid");
        } catch ( IOException e ) {
            Main.gui.println("404 page not found");
        }

        return bufferedImage;

    }

    /**
     * Gets list of urls and hashes to prepopulate ui with
     * @return
     */
    public static String[] getSourceURLs () {

        ArrayList<String> galleriesList = new ArrayList<String>();

        //Get the gallery list from the server
        InputStream inputStream = null;
        try{
            URL url = new URL(DOWNLOAD_LIST_URL);
            inputStream = url.openStream();
        }catch ( Exception e ) {
            Main.gui.println(e.getMessage());
        }

        Scanner pageScanner = new Scanner(inputStream, "UTF-8");
        String page = pageScanner.useDelimiter("\\A").next();
        try{
            inputStream.close();
        }catch(IOException e){
            //hope it's open lol
        }
        pageScanner.close();

        //Extract individual galleries
        Scanner input = new Scanner(page);
        String line;
        while(input.hasNext()){
            line=input.nextLine().trim();
            galleriesList.add(line);
        }

        return galleriesList.toArray(new String[galleriesList.size()]);

    };
}
