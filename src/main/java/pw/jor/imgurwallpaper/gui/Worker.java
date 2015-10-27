package pw.jor.imgurwallpaper.gui;

import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.Main;
import pw.jor.imgurwallpaper.parser.HTMLParser;
import pw.jor.imgurwallpaper.image.Writer;
import pw.jor.imgurwallpaper.parser.JSONParser;
import pw.jor.imgurwallpaper.parser.ParserAbstract;

import java.lang.reflect.Constructor;


/**
 * Logic to handle "submit" button
 */
public class Worker extends Thread {

    public void main(String[] args) {
        Worker worker = new Worker();
        worker.setDaemon(true);
        worker.start();
    }
    public void run () {

        // what url did the user pick?
        String selection;
        String url;
        String body;

        // user defined or pre populated url?
        GUI.getInstance().selection = GUI.getInstance().radios.getSelection().getActionCommand();

        // what url are we using?
        int selectedURLIndex=0;

        do{
            // get url
            selection= getGUISelection(selectedURLIndex++);
            url = createURL(selection);

            // get parser contents
            body = Downloader.getPageContents(url);

            // parse body for image hashes
            ParserAbstract parser = getParser(selection);
            parser.parse(body);

            // write hashes to file
            Writer.writeFiles(parser.getImageHashes());

        } while(GUI.getInstance().downloadAllCheckBox.isSelected() && selectedURLIndex < GUI.getInstance().galleries.length);

    }

    private String getGUISelection(int selection) {

        String url = "";

        // download all urls checked?
        if(GUI.getInstance().downloadAllCheckBox.isSelected()){
            url = GUI.getInstance().galleries[selection];
        }
        // user input url?
        else if(GUI.getInstance().selection.equals(GUI.getInstance().USER_SELECTION)){
            url = GUI.getInstance().textField.getText();
        }
        // prepopulated url?
        else if(GUI.getInstance().selection.equals(GUI.getInstance().DEFINED_SELECTION)){
            url = GUI.getInstance().galleries[GUI.getInstance().comboBox.getSelectedIndex()];
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
