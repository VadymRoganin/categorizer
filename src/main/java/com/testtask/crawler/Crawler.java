package com.testtask.crawler;

import com.testtask.model.Page;

import java.util.List;

public interface Crawler {

    /**
     * Downloads websites
     *
     * @param urls Website URLs
     * @return sites list
     */
    List<Page> download(List<String> urls);
}
