package pw.jor.imgurwallpaper.parser;

/**
 * @author jrobinson
 * @since 10/28/15
 */
public class ParserFactory {

    public static Parser factory ( String galleryType ) {

        ParserInterface parserInterface;

        switch ( galleryType ) {
            case "URL":
                parserInterface = new HTMLParser();
                break;
            case "HASH":
                parserInterface = new JSONParser();
                break;
            default:
                throw new ExceptionInInitializerError("Invalid gallery type");
        };

        return new Parser(parserInterface);
    }
}
