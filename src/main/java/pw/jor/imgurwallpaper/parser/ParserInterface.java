package pw.jor.imgurwallpaper.parser;

import java.util.ArrayList;

/**
 * Abstract class for parsing image hashes from Imgur
 *
 * @author jrobinson
 * @since 10/22/15
 */
public interface ParserInterface {

    /**
     * Abstract function to parse body for hashes
     *
     * @param body to be parsed for hashes
     */
    ArrayList<String> parse ( String body );
}
