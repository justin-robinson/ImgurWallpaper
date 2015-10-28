package pw.jor.imgurwallpaper.parser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * @author jrobinson
 * @since 10/28/15
 */
public class Parser {

    private ArrayList<String> ImageHashes;
    private ParserInterface parserInterface;

    /**
     * Constructor
     */
    public Parser( ParserInterface parserInterface ) {
        this.ImageHashes = new ArrayList<>();
        this.parserInterface = parserInterface;
    }

    /**
     * Parses body for image hashes using parser interface
     *
     * @param body what to parse
     * @return image hashes found
     */
    public ArrayList<String> parse ( String body ) {
        return parserInterface.parse(body);
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
}
