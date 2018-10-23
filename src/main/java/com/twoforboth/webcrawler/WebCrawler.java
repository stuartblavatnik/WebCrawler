package com.twoforboth.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.exit;

public class WebCrawler {

    private static SiteProcessor siteProcessor = new SiteProcessor();

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: WebCrawler URL");
            exit(1);
        }

        URL url = null;

        try {
            url = new URL(args[0]);
        }
        catch (MalformedURLException malformedURLException) {
            System.out.println("Malformed URL " + args[0]);
            exit(1);
        }

        try {
            siteProcessor.process(url);
        }
        catch (Exception exception) {
            System.out.println("Error processing.");
            exit(1);
        }

        System.out.println(siteProcessor.getReport());
    }

}
