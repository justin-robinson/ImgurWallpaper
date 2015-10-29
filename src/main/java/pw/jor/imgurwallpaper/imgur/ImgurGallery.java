package pw.jor.imgurwallpaper.imgur;

/**
 * @author jrobinson
 * @since 10/28/15
 */
public class ImgurGallery {

    private String url;
    private GalleryType galleryType;

    public enum GalleryType {
        URL,
        HASH
    };

    /**
     * Constructor
     * @param galleryIdentifier full Imgur url or gallery hash
     */
    public ImgurGallery(String galleryIdentifier) {
        galleryType = parseGalleryType(galleryIdentifier);
        url = createURL(galleryIdentifier);
    }

    /**
     * Gets url object
     * @return url object for this Imgur gallery
     */
    public String getUrl () {
        return url;
    }

    /**
     * Gets the type of gallery
     * @return the gallery type
     */
    public GalleryType getGalleryType() {
        return galleryType;
    }

    /**
     * Create proper url to scrape from gallery url
     *
     * @param galleryIdentifier full url or image hash
     * @return url to download
     */
    private String createURL(String galleryIdentifier ) {

        String url = "";

        if ( getGalleryType() == GalleryType.HASH  ) {
            url = "http://imgur.com/ajaxalbums/getimages/" + galleryIdentifier + "/hit.HASH?all=true";
        } else if ( getGalleryType() == GalleryType.URL ) {
            url = galleryIdentifier + "/new/page/1/hit?scrolled";
        }

        return url;

    }

    /**
     * Tests galleryIdentifier to see if it's a url or hash
     *
     * @param galleryIdentifier full url or image hash
     * @return the type of gallery
     */
    private GalleryType parseGalleryType(String galleryIdentifier) {
        return galleryIdentifier.startsWith("http", 0)
                ? GalleryType.URL
                : GalleryType.HASH;
    }

}
