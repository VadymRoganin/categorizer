package com.testtask.categorizer;

import com.testtask.crawler.Crawler;
import com.testtask.exception.CategorizerException;
import com.testtask.model.Category;
import com.testtask.model.CategoryWord;
import com.testtask.model.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BasicCategorizerTest {

    @Test
    public void shouldCategorize() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";

        var urls = List.of(url1, url2);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";

        var siteText1 = "Site Text 1 " + keyword1;
        var siteText2 = "Site Text 2 " + keyword2;

        List<Page> pages = List.of(new Page(url1, siteText1, null), new Page(url2, siteText2, null));

        when(crawler.download(eq(urls))).thenReturn(pages);

        var categoryName1 = "Category 1";
        var categoryName2 = "Category 2";

        Category category1 = new Category(categoryName1, List.of(new CategoryWord(keyword1)));
        Category category2 = new Category(categoryName2, List.of(new CategoryWord(keyword2)));

        var categoriesList = List.of(category1, category2);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        categorizer.initializeModel(categoriesList);

        var result = categorizer.categorize(urls, categoriesList);

        var categoriesIterator = result.stream().sorted(Comparator.comparing(Page::getText)).iterator();
        var categories1 = categoriesIterator.next().getCategories();
        var categories2 = categoriesIterator.next().getCategories();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, categories1.size());
        Assertions.assertEquals(1, categories2.size());
        Assertions.assertTrue(categories1.stream().allMatch(c -> c.name().equals(categoryName1)));
        Assertions.assertTrue(categories2.stream().allMatch(c -> c.name().equals(categoryName2)));
    }

    @Test
    public void shouldCategorizeSitesWithMultipleCategories() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";

        var urls = List.of(url1, url2);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";
        var keyword3 = "keyword3";

        var siteText1 = "Site Text 1 " + keyword1;
        var siteText2 = "Site Text 2 " + keyword2 + " " + keyword3;

        List<Page> pages = List.of(new Page(url1, siteText1, null), new Page(url2, siteText2, null));

        when(crawler.download(eq(urls))).thenReturn(pages);

        var categoryName1 = "Category 1";
        var categoryName2 = "Category 2";
        var categoryName3 = "Category 3";

        Category category1 = new Category(categoryName1, List.of(new CategoryWord(keyword1)));
        Category category2 = new Category(categoryName2, List.of(new CategoryWord(keyword2)));
        Category category3 = new Category(categoryName3, List.of(new CategoryWord(keyword3)));

        var categoriesList = List.of(category1, category2, category3);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        categorizer.initializeModel(categoriesList);

        var result = categorizer.categorize(urls, categoriesList);
        var categoriesIterator = result.stream().sorted(Comparator.comparing(Page::getText)).iterator();
        var categories1 = categoriesIterator.next().getCategories();
        var categories2 = categoriesIterator.next().getCategories();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, categories1.size());
        Assertions.assertEquals(2, categories2.size());
        Assertions.assertTrue(categories1.stream().allMatch(c -> c.name().equals(categoryName1)));
        Assertions.assertTrue(categories2.stream().anyMatch(c -> c.name().equals(categoryName2)));
        Assertions.assertTrue(categories2.stream().anyMatch(c -> c.name().equals(categoryName3)));
    }

    @Test
    public void shouldCategorizeWhenUrlsAreRepeated() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";

        var urls = List.of(url1, url2, url2);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";

        var siteText1 = "Site Text 1 " + keyword1;
        var siteText2 = "Site Text 2 " + keyword2;

        List<Page> pages = List.of(new Page(url1, siteText1, null), new Page(url2, siteText2, null),
                new Page(url2, siteText2, null));

        when(crawler.download(eq(urls))).thenReturn(pages);

        var categoryName1 = "Category 1";
        var categoryName2 = "Category 2";

        Category category1 = new Category(categoryName1, List.of(new CategoryWord(keyword1)));
        Category category2 = new Category(categoryName2, List.of(new CategoryWord(keyword2)));

        var categoriesList = List.of(category1, category2);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        categorizer.initializeModel(categoriesList);

        var result = categorizer.categorize(urls, categoriesList);
        var categoriesIterator = result.stream().sorted(Comparator.comparing(Page::getText)).iterator();
        var categories1 = categoriesIterator.next().getCategories();
        var categories2 = categoriesIterator.next().getCategories();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, categories1.size());
        Assertions.assertEquals(1, categories2.size());
        Assertions.assertTrue(categories1.stream().allMatch(c -> c.name().equals(categoryName1)));
        Assertions.assertTrue(categories2.stream().allMatch(c -> c.name().equals(categoryName2)));
    }

    @Test
    public void shouldCategorizeAndNotReturnSitesWithNoCategories() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";
        var url3 = "http://url3.com";

        var urls = List.of(url1, url2, url3);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";
        var keyword3 = "keyword3";

        var siteText1 = "Site Text 1 " + keyword1;
        var siteText2 = "Site Text 2 " + keyword2;
        var siteText3 = "Site Text 3 " + keyword3;

        List<Page> pages = List.of(new Page(url1, siteText1, null), new Page(url2, siteText2, null),
                new Page(url3, siteText3, null));

        when(crawler.download(eq(urls))).thenReturn(pages);

        var categoryName1 = "Category 1";
        var categoryName2 = "Category 2";

        Category category1 = new Category(categoryName1, List.of(new CategoryWord(keyword1)));
        Category category2 = new Category(categoryName2, List.of(new CategoryWord(keyword2)));

        var categoriesList = List.of(category1, category2);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        categorizer.initializeModel(categoriesList);

        var result = categorizer.categorize(urls, categoriesList);

        var categoriesIterator = result.stream().sorted(Comparator.comparing(Page::getText)).iterator();
        var categories1 = categoriesIterator.next().getCategories();
        var categories2 = categoriesIterator.next().getCategories();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, categories1.size());
        Assertions.assertEquals(1, categories2.size());
        Assertions.assertTrue(categories1.stream().allMatch(c -> c.name().equals(categoryName1)));
        Assertions.assertTrue(categories2.stream().allMatch(c -> c.name().equals(categoryName2)));
    }

    @Test
    public void shouldThrowExceptionForUninitializedCategories() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";

        var urls = List.of(url1, url2);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";

        var siteText1 = "Site Text 1 " + keyword1;
        var siteText2 = "Site Text 2 " + keyword2;

        List<Page> pages = List.of(new Page(url1, siteText1, null), new Page(url2, siteText2, null));

        when(crawler.download(eq(urls))).thenReturn(pages);

        var categoryName1 = "Category 1";
        var categoryName2 = "Category 2";

        Category category1 = new Category(categoryName1, List.of(new CategoryWord(keyword1)));
        Category category2 = new Category(categoryName2, List.of(new CategoryWord(keyword2)));

        var categoriesList = List.of(category1, category2);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        Assertions.assertThrows(CategorizerException.class, () -> categorizer.categorize(urls, categoriesList));
    }

    @Test
    public void shouldThrowExceptionOnNullCategories() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";

        var urls = List.of(url1, url2);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";

        var siteText1 = "Site Text 1 " + keyword1;
        var siteText2 = "Site Text 2 " + keyword2;

        List<Page> pages = List.of(new Page(url1, siteText1, null), new Page(url2, siteText2, null));

        when(crawler.download(eq(urls))).thenReturn(pages);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        Assertions.assertThrows(CategorizerException.class, () -> categorizer.categorize(urls, null));
    }

    @Test
    public void shouldThrowExceptionOnNullUrls() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";

        var urls = List.of(url1, url2);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";

        var siteText1 = "Site Text 1 " + keyword1;
        var siteText2 = "Site Text 2 " + keyword2;

        List<Page> pages = List.of(new Page(url1, siteText1, null), new Page(url2, siteText2, null));

        when(crawler.download(eq(urls))).thenReturn(pages);

        var categoryName1 = "Category 1";
        var categoryName2 = "Category 2";

        Category category1 = new Category(categoryName1, List.of(new CategoryWord(keyword1)));
        Category category2 = new Category(categoryName2, List.of(new CategoryWord(keyword2)));

        var categoriesList = List.of(category1, category2);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        Assertions.assertThrows(CategorizerException.class, () -> categorizer.categorize(null, categoriesList));
    }

    @Test
    public void shouldThrowExceptionOnMalformedUrl() {
        Crawler crawler = mock(Crawler.class);
        var url1 = "http://url1.com";
        var url2 = "http://url2.com";

        var urls = List.of(url1, url2);

        var keyword1 = "keyword1";
        var keyword2 = "keyword2";

        when(crawler.download(eq(urls))).thenThrow(CategorizerException.class);

        var categoryName1 = "Category 1";
        var categoryName2 = "Category 2";

        Category category1 = new Category(categoryName1, List.of(new CategoryWord(keyword1)));
        Category category2 = new Category(categoryName2, List.of(new CategoryWord(keyword2)));

        var categoriesList = List.of(category1, category2);

        BasicCategorizer categorizer = new BasicCategorizer(crawler);

        Assertions.assertThrows(CategorizerException.class, () -> categorizer.categorize(null, categoriesList));
    }
}
