package com.testtask.crawler.downloader;

import com.testtask.exception.CategorizerException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
public class JSoupDownloader implements Downloader {

    @Override
    public String download(String url) {
        try {
            return Jsoup.connect(url).get().wholeText();
        } catch (IOException | IllegalArgumentException e) {
            // JSoup throws IllegalArgumentException in case of malformed URL, so we need to handle it here as well
            var msg = "Problem with download";
            log.error(msg);
            throw new CategorizerException(msg, e);
        }
    }
}
