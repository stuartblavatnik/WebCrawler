package com.twoforboth.webcrawler;


import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class WebCrawlerTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void shouldExitWithStatusZeroForValidRun() {

        exit.expectSystemExitWithStatus(0);

        String args[] = new String[] { "http://wiprodigital.com" };

        WebCrawler.main(args);
    }

    @Test
    public void shouldExitWithStatusOneForInvalidURL() {

        exit.expectSystemExitWithStatus(1);

        String args[] = new String[] { "abc.com" };

        WebCrawler.main(args);
    }

    @Test
    public void shouldExitWithStatusOneForNoArguments() {

        exit.expectSystemExitWithStatus(1);

        String args[] = new String[] { };

        WebCrawler.main(args);
    }

    @Test
    public void shouldExitWithStatus1ForMoreThanOneArguments() {

        exit.expectSystemExitWithStatus(1);

        String args[] = new String[] { "http://www.twoforboth.com", "hello"};

        WebCrawler.main(args);
    }


}
