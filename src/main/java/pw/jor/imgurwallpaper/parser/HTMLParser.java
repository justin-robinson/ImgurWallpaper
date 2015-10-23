package pw.jor.imgurwallpaper.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pw.jor.imgurwallpaper.Main;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class HTMLParser extends ParserAbstract {

    public void parse ( String body ) {
        Document document = Jsoup.parse(body);

        Elements imgs = document.select("div.post a.image-list-link");

        for ( Element img : imgs ) {
            String imageURL = img.attr("href");
            String hash = Paths.get(imageURL).getFileName().toString();

            addHash(hash);
        }

    }
}
