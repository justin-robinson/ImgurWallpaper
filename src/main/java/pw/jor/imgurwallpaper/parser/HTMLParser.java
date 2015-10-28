package pw.jor.imgurwallpaper.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Paths;

/**
 * Parses the Imgur DOM for hashes
 *
 * @author jrobinson
 * @since 10/22/15
 */
public class HTMLParser extends ParserAbstract {

    /**
     * Parses Imgur DOM for hashes
     *
     * @param body html DOM
     */
    public void parse ( String body ) {

        // import body into DOM parser
        Document document = Jsoup.parse(body);

        // find links to images
        Elements imgs = document.select("div.post a.image-list-link");

        // add image hash to hashes
        for ( Element img : imgs ) {
            String imageURL = img.attr("href");
            String hash = Paths.get(imageURL).getFileName().toString();

            addHash(hash);
        }

    }
}
