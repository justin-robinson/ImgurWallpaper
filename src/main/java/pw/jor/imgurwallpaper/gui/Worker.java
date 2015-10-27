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
 * Logic to handle "submit" button
 */
public class Worker extends SafeThread {

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
            body = Downloader.getPageContents(url);

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

                // stalls if the thread was suspended
                blockSuspended();

                // downloads image and writes file
                writer.write(fileName);

                // stop if thread died
                if ( !threadActive() ) {
                    break;
                }
            }

        } while(
                gui.downloadAllCheckBox.isSelected()
                && selectedURLIndex < GUI.galleries.length
                && threadActive()
                );

        gui.println("Done");


    }

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

    private String createURL(String hash) {

        String url;

        // if url is just a hash, create the imgur url
        url = isURL(hash)
                ? hash + "/new/page/1/hit?scrolled"
                : galleryHashToURL(hash);

        return url;
    }

    private boolean isURL ( String url ) {
        return url.startsWith("http", 0);
    }

    private String galleryHashToURL(String hash) {
        return "http://imgur.com/ajaxalbums/getimages/" + hash + "/hit.json?all=true";
    }

    private ParserAbstract getParser( String url ) {

            return isURL(url)
                    ? new HTMLParser()
                    : new JSONParser();


    }
}
