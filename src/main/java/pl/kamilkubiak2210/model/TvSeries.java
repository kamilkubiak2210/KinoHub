package pl.kamilkubiak2210.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class TvSeries extends Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 4444L;
    private final int amountOfEpisodes;
    private final int amountOfSeasons;
    private final String producer;

    public TvSeries(String title, Genre genre, String description, double rating, int amountOfEpisodes, int amountOfSeasons, String producer) {
        super(title, genre, description, rating);
        this.amountOfEpisodes = amountOfEpisodes;
        this.amountOfSeasons = amountOfSeasons;
        this.producer = producer;
    }

    public int getAmountOfEpisodes() {
        return amountOfEpisodes;
    }

    public int getAmountOfSeasons() {
        return amountOfSeasons;
    }

    public String getProducer() {
        return producer;
    }

    @Override
    public String toString() {
        return getTitle() + ", liczba sezonów: " + amountOfSeasons + ", liczba odcinków: " + amountOfEpisodes +
                ", producent: " + producer + ", gatunek: " + getGenre().getDescription() + ", opis: " + getDescription() + ", ocena: " + getRating();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TvSeries tvSeries)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return amountOfEpisodes == tvSeries.amountOfEpisodes && amountOfSeasons == tvSeries.amountOfSeasons && Objects.equals(producer, tvSeries.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amountOfEpisodes, amountOfSeasons, producer);
    }
}