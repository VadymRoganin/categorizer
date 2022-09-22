package com.testtask.categorizer;

import com.testtask.model.Category;
import com.testtask.model.Page;

import java.util.List;
import java.util.Set;

public interface Categorizer {


    /**
     * Categorizes URL list according to categories list
     *
     * @param urls       URLs list
     * @param categories categories list
     * @return URL - set containing sites with at least one matching category
     */
    Set<Page> categorize(List<String> urls, List<Category> categories);

    /**
     * Initializes categories model
     *
     * @param categories categories list to initialize with
     */
    void initializeModel(List<Category> categories);
}
