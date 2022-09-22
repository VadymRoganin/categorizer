package com.testtask.crawler;

import com.testtask.crawler.downloader.Downloader;
import com.testtask.model.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class WebCrawler implements Crawler {

    private final Downloader downloader;

    public WebCrawler(Downloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public List<Page> download(List<String> urls) {
        Objects.requireNonNull(urls, "URLs list cannot be null");
        return urls.stream()
                .map(url -> new Page(url, downloader.download(url), null))
                .toList();
    }
}
