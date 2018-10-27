package com.twoforboth.webcrawler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class WebCrawlerIT {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void shouldExitWithStatusZeroForValidRun() {

        exit.expectSystemExitWithStatus(0);

        String args[] = new String[] { "http://wiprodigital.com" };

        WebCrawler.main(args);
    }

}
