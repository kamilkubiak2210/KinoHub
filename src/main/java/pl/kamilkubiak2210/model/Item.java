package pl.kamilkubiak2210.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 2222L;
    private final String title;
    private final Genre genre;
    private final String description;
    private final double rating;

    public Item(String title, Genre genre, String description, double rating) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return rating == item.rating && Objects.equals(title, item.title) && genre == item.genre && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, description, rating);
    }
}