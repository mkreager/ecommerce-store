package com.store.models;

import java.math.BigDecimal;

public class Cd {

    private final String id;
    private final String title;
    private final String artist;
    private final String year;
    private final String description;
    private final BigDecimal price;
    private final String label;
    private final CdCategory category;
    private final String imgUrl;
    
    public Cd() {
        this.id = "";
        this.title = "";
        this.artist = "";
        this.year = "";
        this.description = "";
        this.price = new BigDecimal(0.0);
        this.label = "";
        this.category = CdCategory.COUNTRY;
        this.imgUrl = "";
    }
    
    public Cd(String id, String title, String artist, String year, String description,
              BigDecimal price, String label, CdCategory category, String imgUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.description = description;
        this.price = price;
        this.label = label;
        this.category = category;
        this.imgUrl = imgUrl;
    }

    public Cd(String id, String title, String artist, String year, String description,
              BigDecimal price, String label, String category, String imgUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.description = description;
        this.price = price;
        this.label = label;
        this.category = CdCategory.valueOf(category);
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getLabel() {
        return label;
    }

    public CdCategory getCategory() {
        return category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        return "Cd{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", year='" + year + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", label='" + label + '\'' +
                ", category=" + category +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

}
