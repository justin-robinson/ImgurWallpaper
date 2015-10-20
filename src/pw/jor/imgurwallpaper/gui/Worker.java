package pw.jor.imgurwallpaper.gui;

import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.page.Parser;
import pw.jor.imgurwallpaper.image.Writer;


/**
 * Logic to handle "submit" button
 */
public class Worker extends Thread {

    public static final String HTTP = "http";
    public static final String IMGUR_URL_PREFIX = "http://imgur.com/a/";

    private GUI gui;

    public Worker(GUI gui) {
        this.gui = gui;
    }

    public void main(String[] args) {
        Worker worker = new Worker(gui);
        worker.setDaemon(true);
        worker.start();
    }
    public void run () {

        // what url did the user pick?
        String url = "";

        // user defined or pre populated url?
        gui.selection = gui.radios.getSelection().getActionCommand();

        // what url are we using?
        int selectedURLIndex=0;

        do{
            // get url
            url= getSelectedURL(selectedURLIndex++);

            // get page contents
            String page = Downloader.getPageContents(url, gui);

            // parse page for image hashes
            Parser parser = new Parser(page, gui);

            // write hashes to file
            Writer.writeFiles(parser.searchPage(), gui);

        } while(gui.downloadAllCheckBox.isSelected() && selectedURLIndex < gui.galleries.length);

    }

    private String getSelectedURL(int selection) {

        String url = "";

        // download all urls checked?
        if(gui.downloadAllCheckBox.isSelected()){
            url = gui.galleries[selection];
        }
        // user input url?
        else if(gui.selection.equals(gui.USER_SELECTION)){
            url = gui.textField.getText();
        }
        // prepopulated url?
        else if(gui.selection.equals(gui.DEFINED_SELECTION)){
            url = gui.galleries[gui.comboBox.getSelectedIndex()];
        }

        // if url is just a hash, prepend the imgur url
        url = url.startsWith(HTTP, 0)
                ? url
                : IMGUR_URL_PREFIX + url;

        return url;
    }
}
