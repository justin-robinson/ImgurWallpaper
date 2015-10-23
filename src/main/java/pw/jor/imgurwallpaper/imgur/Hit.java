package pw.jor.imgurwallpaper.imgur;

/**
 * Created by jrobinson on 10/22/15.
 */
public class Hit {

    public boolean success;
    public int status;
    public Data data;

    public Image[] getImages () {
        return data.images;
    }

}
