package pl.kamilkubiak2210.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Movie extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1111L;
    private final int year;
    private final String director;

    public Movie(String title, Genre genre, String description, double rating, int year, String director) {
        super(title, genre, description, rating);
        this.year = year;
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public String toString() {
        return getTitle() + ", rok: " + year + ", reżyser: " + director +
                ", gatunek: " + getGenre().getDescription() + ", opis: " + getDescription() + ", ocena: " + getRating();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie movie)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return year == movie.year && Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), year, director);
    }
}