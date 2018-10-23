package com.twoforboth.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SiteProcessor {

    private String siteRoot = "";
    private ArrayList<URL> traversedURLS = new ArrayList<>();
    private LinkedHashMap<URL, ArrayList<URL>> siteMap = new LinkedHashMap<>();

    private WebParser webParser;
    public void setWebParser(WebParser webParser) {
        this.webParser = webParser;
    }

    public void process(URL url) {

        if (siteRoot.length() == 0) {
            siteRoot = url.toString();
        }

        traversedURLS.add(url);

        ArrayList<URL> pageLinks;

        try {
            pageLinks = webParser.getPageLinks(url);
        }
        catch (IOException ioException) {
            pageLinks = new ArrayList<>();
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
            stringBuilder.append(System.lineSeparator());
            for (URL siteLinks : siteMap.get(site)) {
                stringBuilder.append('\t');
                stringBuilder.append(siteLinks.toString());
                stringBuilder.append(System.lineSeparator());
            }
        }

        return stringBuilder.toString();
    }

}
