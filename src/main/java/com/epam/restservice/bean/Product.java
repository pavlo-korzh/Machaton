package com.epam.restservice.bean;

import java.util.List;

public class Product {

    private String name;
    private String url;
    private String price;
    private List<String> classes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", price='" + price + '\'' +
                ", classes=" + classes +
                '}';
    }
}
