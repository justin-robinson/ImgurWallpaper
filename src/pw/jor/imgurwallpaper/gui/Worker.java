package pw.jor.imgurwallpaper.gui;

import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.Main;
import pw.jor.imgurwallpaper.page.Parser;
import pw.jor.imgurwallpaper.image.Writer;


/**
 * Logic to handle "submit" button
 */
public class Worker extends Thread {

    public static final String HTTP = "http";
    public static final String IMGUR_URL_PREFIX = "http://imgur.com/a/";

    public void main(String[] args) {
        Worker worker = new Worker();
        worker.setDaemon(true);
        worker.start();
    }
    public void run () {

        // what url did the user pick?
        String url = "";

        // user defined or pre populated url?
        Main.gui.selection = Main.gui.radios.getSelection().getActionCommand();

        // what url are we using?
        int selectedURLIndex=0;

        do{
            // get url
            url= getSelectedURL(selectedURLIndex++);

            // get page contents
            String page = Downloader.getPageContents(url);

            // parse page for image hashes
            Parser parser = new Parser(page);

            // write hashes to file
            Writer.writeFiles(parser.searchPage());

        } while(Main.gui.downloadAllCheckBox.isSelected() && selectedURLIndex < Main.gui.galleries.length);

    }

    private String getSelectedURL(int selection) {

        String url = "";

        // download all urls checked?
        if(Main.gui.downloadAllCheckBox.isSelected()){
            url = Main.gui.galleries[selection];
        }
        // user input url?
        else if(Main.gui.selection.equals(Main.gui.USER_SELECTION)){
            url = Main.gui.textField.getText();
        }
        // prepopulated url?
        else if(Main.gui.selection.equals(Main.gui.DEFINED_SELECTION)){
            url = Main.gui.galleries[Main.gui.comboBox.getSelectedIndex()];
        }

        // if url is just a hash, prepend the imgur url
        url = url.startsWith(HTTP, 0)
                ? url
                : IMGUR_URL_PREFIX + url;

        return url;
    }
}
