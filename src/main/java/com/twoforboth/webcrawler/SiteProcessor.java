package com.twoforboth.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SiteProcessor {

    // Thoughts
    // Given a URL
    // parse pagedata
    // keep a hash of each url -- key is the url -- value is the sublinks
    // indicate if traversed to -- to prevent multiple traversals
    // indicate if broken link
    // this should work in similar fashion of the cities problem

    private String siteRoot = "";
    private ArrayList<URL> traversedURLS = new ArrayList<>();
    private LinkedHashMap<URL, ArrayList<URL>> siteMap = new LinkedHashMap<>();
    private WebParser webParser = new WebParser();

    public void process(URL url) {

        if (siteRoot.length() == 0) {
            siteRoot = url.toString();
        }

        traversedURLS.add(url);

        ArrayList<URL> pageLinks = new ArrayList<>();

        try {
            pageLinks = webParser.getPageLinks(url);
        }
        catch (IOException ioException) {

        }

        if (!siteMap.containsKey(url)) {
            siteMap.put(url, pageLinks);
        }

        for (URL pageLink : pageLinks) {
            if (pageLink.toString().startsWith(siteRoot) && !traversedURLS.contains(pageLink)) {
                process(pageLink);
            }
        }
    }

    public String getReport() {

        StringBuilder stringBuilder = new StringBuilder();

        for (URL site : siteMap.keySet()) {
            stringBuilder.append(site.toString());
            stringBuilder.append("\r\n");
            for (URL siteLinks : siteMap.get(site)) {
                stringBuilder.append('\t');
                stringBuilder.append(siteLinks.toString());
                stringBuilder.append("\r\n");
            }
        }

        return stringBuilder.toString();
    }

}
