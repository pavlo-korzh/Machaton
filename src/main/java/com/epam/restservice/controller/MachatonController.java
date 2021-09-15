package com.epam.restservice.controller;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.settings.IndexSettings;
import com.epam.restservice.bean.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MachatonController {

    private final static String ANDROID_MATCHER = "Android.[1-9][1-9]|Android.[1-9]";
    private final static String ANDROID_VERSION_MATCHER = "[0-9]+";
    private SearchClient client;
    private SearchIndex<Product> index;

    public MachatonController() {
        client = DefaultSearchClient.create("C893PBPN7J", "c86dd8f0a63b33ce3ddb7f27d6a40dba");
        index = client.initIndex("machatonTest", Product.class);
        index.setSettings(
                new IndexSettings().setAttributesForFaceting(Collections.singletonList(
                        "classes"
                ))
        );
    }

    @ResponseBody
    @GetMapping("/mach/algolia")
    public List<Product> search(@RequestHeader(value = "machaton-personalization", required = true) String personalization) {

        Query query = new Query().setFilters(getFilter(getClasses(decodeHeader(personalization))));

        return index.search(query).getHits();
    }

    private String decodeHeader(String header) {
        return new String(Base64.getDecoder().decode(header));
    }

    private List<String> getClasses(String s) {
        if (s.contains("Macintosh")) {
            return Collections.singletonList("A");
        } else if (checkAndroid(s) && checkAndroidVersion(s) < 7) {
            return Arrays.asList("B", "C");
        }

        return Collections.singletonList("B");
    }

    private boolean checkAndroid(String text) {
        return !getAndroidMarker(text).isEmpty();
    }

    private String getAndroidMarker(String text) {
        Pattern pattern = Pattern.compile(ANDROID_MATCHER);
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? text.substring(matcher.start(), matcher.end()) : "";
    }

    private int checkAndroidVersion(String text) {
        Pattern pattern = Pattern.compile(ANDROID_VERSION_MATCHER);
        Matcher matcher = pattern.matcher(getAndroidMarker(text));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }

        return 0;
    }

    private String getFilter(List<String> list) {
        StringBuilder filter = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                filter.append(" OR ");
            }
            filter.append("classes:").append(list.get(i));
        }

        return filter.toString();
    }

}
