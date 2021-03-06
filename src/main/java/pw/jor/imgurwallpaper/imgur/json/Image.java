package pw.jor.imgurwallpaper.imgur.json;

/**
 * Class to contain Imgur's hit.data.images[] HASH object
 *
 * @author jrobinson
 * @since 10/22/15
 */
public class Image {

    public String hash;
    public String title;
    public String description;
    public int width;
    public int height;
    public String ext;
    public boolean animated;
    public boolean prefer_video;
    public boolean looping;
    public String datetime;

}
