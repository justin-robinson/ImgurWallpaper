package pw.jor.imgurwallpaper.gui;

import pw.jor.SafeThread;
import pw.jor.imgurwallpaper.Downloader;
import pw.jor.imgurwallpaper.image.ImageContainer;
import pw.jor.imgurwallpaper.imgur.ImgurGallery;
import pw.jor.imgurwallpaper.parser.*;
import pw.jor.imgurwallpaper.image.ImageWriter;

import java.util.ArrayList;
import java.util.function.Function;


/**
 * Worker thread to download images
 *
 * @author jrobinson
 * @since 10/20/15
 */
public class Worker extends SafeThread {

    private String[] galleryIdentifiers;
    private Function<Object, Object> onFinishFunction;

    /**
     * Constructor
     * @param galleryIdentifiers galleryIdentifiers to download
     */
    public Worker ( String[] galleryIdentifiers) {
        this.galleryIdentifiers = galleryIdentifiers;
    }

    /**
     * Downloads images from gallery or galleryIdentifiers provided from gui
     */
    public void action () {

        // image writer
        ImageWriter imageWriter = new ImageWriter();

        for ( String galleryIdentifier : galleryIdentifiers) {

            // stop if thread is died
            if ( !isAlive() ) {
                break;
            }

            // stalls process if thread was suspended
            blockSuspended();

            // get Imgur gallery from identifier
            ImgurGallery gallery = new ImgurGallery(galleryIdentifier);

            // get content for parser to parse
            String body = Downloader.download(gallery.getUrl());

            // parse body for image hashes
            Parser parser = ParserFactory.factory(gallery.getGalleryType().toString());
            ArrayList<String> imageHashes = parser.parse(body);

            // resets counter for image number back to 1
            ImageContainer.resetImageNumberCounter();

            // write hashes to file
            for ( String fileName : imageHashes ) {

                // stalls process if the thread was suspended
                blockSuspended();

                // downloads image and writes file
                imageWriter.write(fileName);

                // stop if thread died
                if ( !isAlive() ) {
                    break;
                }
            }
        }

        if ( onFinishFunction != null ) {
            onFinishFunction.apply(this);
        }

    }

    public void onFinish ( Function<Object, Object> function ) {
        onFinishFunction = function;
    }
}
