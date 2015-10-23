package pw.jor.imgurwallpaper.parser;

import com.google.gson.Gson;
import pw.jor.imgurwallpaper.image.Constraint;
import pw.jor.imgurwallpaper.imgur.Hit;
import pw.jor.imgurwallpaper.imgur.Image;

import java.util.ArrayList;

/**
 * Created by jrobinson on 10/22/15.
 */
public class JSONParser extends ParserAbstract {

    public static Gson gson;

    public JSONParser () {
        super();

        gson = new Gson();
    }

    public void parse ( String body ) {

        Hit hit = gson.fromJson(body, Hit.class);

        for ( Image image : hit.getImages() ) {
            if ( Constraint.isRightSize(image.width, image.height) ) {
                ImageHashes.add(image.hash);
            }
        }

    }
}
