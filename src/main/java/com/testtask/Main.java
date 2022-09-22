package com.testtask;

import com.testtask.categorizer.BasicCategorizer;
import com.testtask.categorizer.Categorizer;
import com.testtask.crawler.WebCrawler;
import com.testtask.crawler.downloader.JSoupDownloader;
import com.testtask.exception.CategorizerException;
import com.testtask.model.Category;
import com.testtask.model.Page;
import lombok.extern.slf4j.Slf4j;
import com.testtask.storage.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            var msg = "Two arguments are expected: a path to file with URLs and a path to file with categories";
            log.error(msg);
            throw new CategorizerException(msg);
        }

        var urlsPath = args[0];
        var categoriesPath = args[1];

        List<String> urls;
        try (Stream<String> stream = Files.lines(Paths.get(urlsPath))) {
            log.info("Reading URLs from file: {}", urlsPath);
            urls = stream.filter(l -> !l.isEmpty())
                    .toList();

        } catch (IOException e) {
            var msg = "Problem with reading urls file";
            log.error(msg);
            throw new CategorizerException(msg, e);
        }

        Storage storage = new Storage(categoriesPath);
        var categories = storage.load();

        Categorizer categorizer = new BasicCategorizer(new WebCrawler(new JSoupDownloader()));
        categorizer.initializeModel(categories);

        Set<Page> pages = categorizer.categorize(urls, categories);

        log.info("Job finished");
        log.info("Results:");

        pages.forEach(s -> log.info("URL: {}, Categories: {}", s.getUrl(), s.getCategories().stream()
                .map(Category::name)
                .collect(Collectors.joining(", "))));
    }
}
