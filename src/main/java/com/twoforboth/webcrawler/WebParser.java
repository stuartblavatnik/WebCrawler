package com.twoforboth.webcrawler;

import org.apache.commons.validator.UrlValidator;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class WebParser {

    private final HashMap<String, String> linkTypes = new HashMap<String, String>() {
        {
            put("a", "href");
            put("link", "href");
            put("img", "src");
        }
    };

    private final UrlValidator urlValidator = new UrlValidator();

    public ArrayList<URL> getPageLinks(URL url) throws IOException {

        ArrayList<URL> pageLinks = new ArrayList<>();

        Document document = null;

        try {
            document = Jsoup
                    .connect(url.toString())
                    .ignoreContentType(true)
                    .get();
        }
        catch (HttpStatusException httpStatusException) {
            return pageLinks;
        }

        for (String linkType :  linkTypes.keySet()) {

            for (Element link : document.select(linkType)) {

                String absoluteURLString = link.absUrl(linkTypes.get(linkType));
                if (urlValidator.isValid(absoluteURLString)) {
                    URL foundLink = new URL(absoluteURLString);
                    if (!pageLinks.contains(foundLink)) {
                        pageLinks.add(foundLink);
                    }
                }
            }

        }

        return pageLinks;
    }

}
