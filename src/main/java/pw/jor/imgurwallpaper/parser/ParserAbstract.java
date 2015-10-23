package pw.jor.imgurwallpaper.parser;

import java.util.ArrayList;

/**
 * Created by jrobinson on 10/22/15.
 */
public abstract class ParserAbstract {

    private ArrayList<String> ImageHashes;

    public ParserAbstract ( ) {
        this.ImageHashes = new ArrayList<>();
    }

    public ArrayList<String> getImageHashes () {
        return ImageHashes;
    }

    public void addHash ( String hash ) {
        if ( !ImageHashes.contains(hash) ) {
            ImageHashes.add(hash);
        }
    }

    public abstract void parse ( String body );
}
