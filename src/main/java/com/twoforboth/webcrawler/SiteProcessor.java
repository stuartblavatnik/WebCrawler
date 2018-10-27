package com.twoforboth.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SiteProcessor {

     private ArrayList<String> traversedURLS = new ArrayList<>();
    private Site siteRoot = null;
    private Site currentSite = null;

    private WebParser webParser;
    public void setWebParser(WebParser webParser) {
        this.webParser = webParser;
    }

    public void process(String url) {

        url = cleanURL(url);

        if (siteRoot == null) {
            siteRoot = new Site(url);
            siteRoot.setLevel(0);
            siteRoot.setParent(null);
            currentSite = siteRoot;
        }

        System.out.println("Working on: " + currentSite.getUrl());

        traversedURLS.add(url);

        ArrayList<String> pageLinks;

        try {
            pageLinks = webParser.getPageLinks(url);
        }
        catch (IOException ioException) {
            pageLinks = new ArrayList<>();
        }

        Site parentSite = currentSite;
        for (String pageLink : pageLinks) {

            pageLink = cleanURL(pageLink);

            if (!traversedURLS.contains(pageLink)) {
                if (isPartOfSite(pageLink)) {

                    Site childSite = new Site(pageLink);
                    childSite.setParent(parentSite);
                    childSite.setLevel(parentSite.getLevel() + 1);
                    currentSite.addChildSite(childSite);
                    currentSite = childSite;

                    process(pageLink);
                    currentSite = parentSite;
                } else {
                    Site externalSite = new Site(pageLink);
                    currentSite.addChildSite(externalSite);
                }
            }
        }
    }

    private boolean isPartOfSite(String url) {

        boolean isPartOfSite;

        if (url.startsWith(siteRoot.getUrl())) {
            isPartOfSite = true;
        }
        else {

            try {
                URL url1 = new URL(url);
                URL url2 = new URL(siteRoot.getUrl());
                isPartOfSite = (url1.getHost().equals(url2.getHost()));
            }
            catch (MalformedURLException malformedURLException)
            {
                isPartOfSite = false;
            }
        }

        return isPartOfSite;
    }

    private String cleanURL(String originalURL) {
        if (originalURL.length() > 0 && originalURL.endsWith("/")) {
            originalURL = originalURL.substring(0, originalURL.length() - 1);
        }

        return originalURL;
    }

    private String getReportElements(Site site) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < site.getLevel(); ++i) {
            sb.append('\t');
        }
        sb.append(site.getUrl());
        for (Site childSite : site.getChildren()) {
            sb.append(System.lineSeparator());
            sb.append(getReportElements(childSite));

        }

        return sb.toString();
    }

    public String getReport() {
        return getReportElements(siteRoot);
    }

}
