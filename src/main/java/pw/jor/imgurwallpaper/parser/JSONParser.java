package pw.jor.imgurwallpaper.parser;

import com.google.gson.Gson;
import pw.jor.imgurwallpaper.image.ImageConstraint;
import pw.jor.imgurwallpaper.imgur.json.Hit;
import pw.jor.imgurwallpaper.imgur.json.Image;

import java.util.ArrayList;

/**
 * Finds image hashes in Imgur's HASH object
 *
 * @author jrobinson
 * @since 10/22/15
 */
public class JSONParser implements ParserInterface {

    public Gson gson = new Gson();

    /**
     * Search json for image hashes
     *
     * @param json Imgur hit object
     */
    public ArrayList<String> parse ( String json ) {

        ArrayList<String> imageHashes = new ArrayList<>();

        // parse HASH into object
        Hit hit = gson.fromJson(json, Hit.class);

        // add all correctly sized images to the hash
        for ( Image image : hit.getImages() ) {
            if ( ImageConstraint.isRightSize(image.width, image.height) ) {
                imageHashes.add(image.hash);
            }
        }

        return imageHashes;

    }
}
