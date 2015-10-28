package pw.jor.imgurwallpaper.imgur;

/**
 * Class to contain Imgur's hit json object
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
