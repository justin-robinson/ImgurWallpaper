package pw.jor.imgurwallpaper.imgur.json;

/**
 * Class to contain Imgur's hit HASH object
 *
 * @author jrobinson
 * @since 10/22/15
 */
public class Hit {

    public boolean success;
    public int status;
    public Data data;

    public Image[] getImages () {
        return data.images;
    }

}
