package pw.jor.imgurwallpaper.parser;

import pw.jor.imgurwallpaper.imgur.ImgurGallery;

/**
 * @author jrobinson
 * @since 10/28/15
 */
public class ParserFactory {

    public static Parser factory ( ImgurGallery.GalleryType galleryType ) {

        ParserInterface parserInterface;

        switch ( galleryType ) {
            case URL:
                parserInterface = new HTMLParser();
                break;
            case HASH:
                parserInterface = new JSONParser();
                break;
            default:
                throw new ExceptionInInitializerError("Invalid gallery type");
        };

        return new Parser(parserInterface);
    }
}
