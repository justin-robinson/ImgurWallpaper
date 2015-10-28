package pw.jor.imgurwallpaper.gui;

import pw.jor.SafeThread;
import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.image.Container;
import pw.jor.imgurwallpaper.parser.HTMLParser;
import pw.jor.imgurwallpaper.image.Writer;
import pw.jor.imgurwallpaper.parser.JSONParser;
import pw.jor.imgurwallpaper.parser.ParserAbstract;

import java.util.ArrayList;


/**
 * Worker thread to download images
 *
 * @author jrobinson
 * @since 10/20/15
 */
public class Worker extends SafeThread {

    /**
     * Downloads images from gallery or galleries provided from gui
     */
    public void action () {

        // what url did the user pick?
        GUI gui = GUI.getInstance();
        String selection;
        String url;
        String body;


        // user defined or pre populated url?
        gui.selection = gui.radios.getSelection().getActionCommand();

        // what url are we using?
        int selectedURLIndex=0;

        do{
            // stalls process if thread was suspended
            blockSuspended();

            // get url
            selection= getGUISelection(selectedURLIndex++);
            url = createURL(selection);

            // get parser contents
            body = Downloader.download(url);

            // parse body for image hashes
            ParserAbstract parser = getParser(selection);
            parser.parse(body);

            // image hashes from the parser
            ArrayList<String> imageHashes = parser.getImageHashes();

            // image writer
            Writer writer = new Writer();

            // resets counter for image number back to 1
            Container.resetImageNumberCounter();

            // write hashes to file
            for ( String fileName : imageHashes ) {

                // stalls process if the thread was suspended
                blockSuspended();

                // downloads image and writes file
                writer.write(fileName);

                // stop if thread died
                if ( !isAlive() ) {
                    break;
                }
            }

        } while(
                gui.downloadAllCheckBox.isSelected()
                && selectedURLIndex < GUI.galleries.length
                && isAlive()
                );

        gui.println("Done");

    }

    /**
     * Gets the input from the gui
     *
     * @param selection index into gallery urls from GUI
     * @return url to download
     */
    private String getGUISelection(int selection) {

        String url = "";

        // download all urls checked?
        if(GUI.getInstance().downloadAllCheckBox.isSelected()){
            url = GUI.galleries[selection];
        }
        // user input url?
        else if(GUI.getInstance().selection.equals(GUI.USER_SELECTION)){
            url = GUI.getInstance().textField.getText();
        }
        // prepopulated url?
        else if(GUI.getInstance().selection.equals(GUI.DEFINED_SELECTION)){
            url = GUI.galleries[GUI.getInstance().comboBox.getSelectedIndex()];
        }

        return url;
    }

    /**
     * Create proper url to scrape from gallery url
     *
     * @param url full url or image hash
     * @return url to download
     */
    private String createURL(String url) {

        // if url is just a hash, create the Imgur url
        return isURL(url)
                ? url + "/new/page/1/hit?scrolled"
                : "http://imgur.com/ajaxalbums/getimages/" + url + "/hit.json?all=true";

    }

    /**
     * Tests string to see if it's a url
     *
     * @param url full url or image hash
     * @return whether the url is actually a url
     */
    private boolean isURL ( String url ) {
        return url.startsWith("http", 0);
    }

    /**
     * Gets the correct parser based on the url
     *
     * @param url full url or image hash
     * @return correct ParserAbstract class instance
     */
    private ParserAbstract getParser( String url ) {

            return isURL(url)
                    ? new HTMLParser()
                    : new JSONParser();


    }
}
