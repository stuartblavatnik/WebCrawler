package com.twoforboth.webcrawler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class SiteProcessorTest {

    @Mock
    private WebParser webParserMock;

    @InjectMocks
    private SiteProcessor siteProcessor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnOneUrlWhenProcess() throws IOException {

        URL url = new URL("http://www.google.com");

        Mockito.when(webParserMock.getPageLinks(url.toString())).thenReturn(new ArrayList<>());
        siteProcessor.setWebParser(webParserMock);

        siteProcessor.process(url.toString());

        Assert.assertEquals(url.toString(), siteProcessor.getReport());
    }

    @Test
    public void shouldReturnOneParentUrlAndOneChildUrlWhenProcess() throws IOException {

        URL parentUrl = new URL("http://www.google.com");
        URL childUrl = new URL("http://www.google.com/coolpic.png");

        Mockito.when(webParserMock.getPageLinks(parentUrl.toString())).thenReturn(new ArrayList<>(Collections.singletonList(childUrl.toString())));
        siteProcessor.setWebParser(webParserMock);

        siteProcessor.process(parentUrl.toString());

        Assert.assertEquals(parentUrl.toString() + System.lineSeparator() + "\t" + childUrl, siteProcessor.getReport());
    }

    @Test
    public void shouldReturnParentParentChildWhenProcess() throws IOException {

        URL parentUrl = new URL("http://www.google.com");
        URL childUrl = new URL("http://www.google.com/subpage1.html");
        URL grandChildUrl = new URL("http://www.google.com/subpage1.html/coolpic.png");

        Mockito.when(webParserMock.getPageLinks(parentUrl.toString())).thenReturn(new ArrayList<>(Collections.singletonList(childUrl.toString())));
        Mockito.when(webParserMock.getPageLinks(childUrl.toString())).thenReturn(new ArrayList<>(Collections.singletonList(grandChildUrl.toString())));
        siteProcessor.setWebParser(webParserMock);

        siteProcessor.process(parentUrl.toString());

        Assert.assertEquals(parentUrl.toString() + System.lineSeparator() + "\t" + childUrl + System.lineSeparator() + "\t\t" + grandChildUrl, siteProcessor.getReport());
    }
}
