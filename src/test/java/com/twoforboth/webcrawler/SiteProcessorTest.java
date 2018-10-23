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
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnOneUrlWhenProcess() throws IOException {

        URL url = new URL("http://www.google.com");

        Mockito.when(webParserMock.getPageLinks(url)).thenReturn(new ArrayList<>());
        siteProcessor.setWebParser(webParserMock);

        siteProcessor.process(url);

        Assert.assertEquals(url.toString() + System.lineSeparator(), siteProcessor.getReport());
    }

    @Test
    public void shouldReturnOneParentUrlAndOneChildUrlWhenProcess() throws IOException {

        URL parentUrl = new URL("http://www.google.com");
        URL childUrl = new URL("http://www.google.com/coolpic.png");

        Mockito.when(webParserMock.getPageLinks(parentUrl)).thenReturn(new ArrayList<>(Collections.singletonList(childUrl)));
        siteProcessor.setWebParser(webParserMock);

        siteProcessor.process(parentUrl);

        Assert.assertEquals(parentUrl.toString() + System.lineSeparator() + "\t" + childUrl + System.lineSeparator() + childUrl.toString() + System.lineSeparator(), siteProcessor.getReport());
    }


}
