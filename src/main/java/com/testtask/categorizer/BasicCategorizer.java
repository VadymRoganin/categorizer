package com.testtask.categorizer;

import com.testtask.crawler.Crawler;
import com.testtask.exception.CategorizerException;
import com.testtask.model.Category;
import com.testtask.model.CategoryWord;
import com.testtask.model.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
public class BasicCategorizer implements Categorizer {

    private final Crawler crawler;

    private final Set<Category> categories = new HashSet<>();

    public BasicCategorizer(Crawler crawler) {
        this.crawler = crawler;
    }

    public void initializeModel(List<Category> categories) {
        this.categories.addAll(categories);
    }

    @Override
    public Set<Page> categorize(List<String> urls, List<Category> categories) {
        validate(categories, urls);
        return crawler.download(urls).stream()
                .peek(s -> setCategories(s, categories))
                .filter(s -> !s.getCategories().isEmpty())
                .collect(Collectors.toSet());
    }

    private void validate(List<Category> categories, List<String> urls) {
        if (isNull(categories)) {
            var msg = "Categories cannot be null";
            throw new CategorizerException(msg);
        }

        if (isNull(urls)) {
            var msg = "URLs cannot be null";
            throw new CategorizerException(msg);
        }

        if (!this.categories.containsAll(categories)) {
            var msg = "Invalid categories supplied";
            log.error(msg);
            throw new CategorizerException(msg);
        }
    }

    private void setCategories(Page page, List<Category> categories) {
        var filteredCategories = categories.stream()
                .filter(c -> filterByCategory(c, page.getText()))
                .toList();

        page.setCategories(filteredCategories);
    }

    private boolean filterByCategory(Category c, String siteText) {
        var keywordsArr = c.keywords().stream()
                .map(CategoryWord::word)
                .toArray(String[]::new);

        return StringUtils.containsAnyIgnoreCase(siteText, keywordsArr);
    }
}
