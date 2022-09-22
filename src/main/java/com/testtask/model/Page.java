package com.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class Page {

    private String url;
    private String text;
    private List<Category> categories = new ArrayList<>();

    @Override
    public String toString() {
        return "Site{" +
                "url='" + url
                + "'}";
    }
}
