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
 *
 * @author jrobinson
 * @since 10/23/15
 */
public class Downloader {

    private static final String DOWNLOAD_LIST_URL = "http://jor.pw/downloads/walls.txt";

    /**
     * Gets the contents of a url
     *
     * @param url link to page
     * @return contents of page at url
     */
    public static String download(String url) {

        String page="";
        InputStream inputStream = null;
        Scanner streamScanner = null;

        try{
            // download page
            inputStream = new URL(url).openStream();
            streamScanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
            page = streamScanner.hasNext() ? streamScanner.next() : page;
        } catch ( MalformedURLException e ) {
            GUI.getInstance().println("URL is invalid");
        } catch ( IOException e ) {
            GUI.getInstance().println("404 Page Not Found");
        } catch ( IllegalArgumentException e ) {
            GUI.getInstance().println("URL is invalid");
        } finally {

            // close stream and scanner
            if ( inputStream != null ) {
                try{
                    inputStream.close();
                } catch ( IOException e ) {
                    GUI.getInstance().println("Error closing inputStream");
                }
            }

            if ( streamScanner != null ) {
                streamScanner.close();
            }
        }

        return page;
    }

    /**
     * Buffers url into image
     *
     * @param url link to page
     * @return buffered image from url
     */
    public static BufferedImage getImage ( String url ) {

        BufferedImage bufferedImage = null;

        try{
            bufferedImage = ImageIO.read(new URL(url));
        } catch ( MalformedURLException e ) {
            GUI.getInstance().println("URL is invalid");
        } catch ( IOException e ) {
            GUI.getInstance().println("404 parser not found");
        }

        return bufferedImage;

    }

    /**
     * Gets list of urls and gallery hashes to prepopulate ui with
     *
     * @return list of default urls and gallery hashes available to download
     */
    public static String[] getSourceURLs () {

        // where we will store the urls and gallery hashes
        ArrayList<String> galleriesList = new ArrayList<>();

        // download page
        String page = download(DOWNLOAD_LIST_URL);

        //Extract individual galleries
        Scanner pageScanner = new Scanner(page);
        while( pageScanner.hasNext() ){
            galleriesList.add(pageScanner.nextLine().trim());
        }

        // close streams and scanners
        pageScanner.close();

        // convert array list to array
        return galleriesList.toArray(new String[galleriesList.size()]);

    }
}
