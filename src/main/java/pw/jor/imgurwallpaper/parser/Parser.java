package pw.jor.imgurwallpaper.parser;

import java.util.ArrayList;

/**
 * @author jrobinson
 * @since 10/28/15
 */
public class Parser {

    private ParserInterface parserInterface;

    /**
     * Constructor
     */
    public Parser( ParserInterface parserInterface ) {
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
}
