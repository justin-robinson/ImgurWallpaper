package pw.jor.imgurwallpaper.parser;

import java.util.ArrayList;

/**
 * Abstract class for parsing image hashes from Imgur
 *
 * @author jrobinson
 * @since 10/22/15
 */
public abstract class ParserAbstract {

    private ArrayList<String> ImageHashes;

    /**
     * Constructor
     */
    public ParserAbstract ( ) {
        this.ImageHashes = new ArrayList<>();
    }

    /**
     * Gets added image hashes
     *
     * @return image hashes
     */
    public ArrayList<String> getImageHashes () {
        return ImageHashes;
    }

    /**
     * Adds hash to image hashes
     *
     * @param hash will be added to image hashes
     */
    public void addHash ( String hash ) {
        if ( !ImageHashes.contains(hash) ) {
            ImageHashes.add(hash);
        }
    }

    /**
     * Abstract function to parse body for hashes
     *
     * @param body to be parsed for hashes
     */
    public abstract void parse ( String body );
}
