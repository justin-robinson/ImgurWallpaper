package pw.jor.imgurwallpaper.parser;

import com.google.gson.Gson;
import pw.jor.imgurwallpaper.image.Constraint;
import pw.jor.imgurwallpaper.imgur.Hit;
import pw.jor.imgurwallpaper.imgur.Image;

/**
 * Finds image hashes in Imgur's json object
 *
 * @author jrobinson
 * @since 10/22/15
 */
public class JSONParser extends ParserAbstract {

    public static Gson gson;

    /**
     * Constructor
     */
    public JSONParser () {
        super();

        // initialize google's json parser
        gson = new Gson();
    }

    /**
     * Search json for image hashes
     *
     * @param body json string
     */
    public void parse ( String body ) {

        // parse json into object
        Hit hit = gson.fromJson(body, Hit.class);

        // add all correctly sized images to the hash
        for ( Image image : hit.getImages() ) {
            if ( Constraint.isRightSize(image.width, image.height) ) {
                addHash(image.hash);
            }
        }

    }
}
