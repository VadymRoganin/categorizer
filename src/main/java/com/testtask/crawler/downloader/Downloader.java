package com.testtask.crawler.downloader;

public interface Downloader {
    /**
     * Downloads site text using supplied URL
     *
     * @param url URL
     * @return site text
     */
    String download(String url);
}
