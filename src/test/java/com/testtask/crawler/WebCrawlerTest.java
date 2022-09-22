package com.testtask.crawler;

import com.testtask.crawler.downloader.Downloader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebCrawlerTest {

    @Test
    public void shouldCrawl() {
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";
        var siteText1 = "Site Text 1";
        var siteText2 = "Site Text 2";
        Downloader downloader = mock(Downloader.class);
        when(downloader.download(eq(url1))).thenReturn(siteText1);
        when(downloader.download(eq(url2))).thenReturn(siteText2);

        Crawler crawler = new WebCrawler(downloader);

        var result = crawler.download(List.of(url1, url2));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(siteText1, result.get(0).getText());
        Assertions.assertEquals(siteText2, result.get(1).getText());
    }

    @Test
    public void shouldThrowExceptionOnNullURLs() {
        Crawler crawler = new WebCrawler(mock(Downloader.class));

        Assertions.assertThrows(NullPointerException.class, () -> crawler.download(null));
    }
}
