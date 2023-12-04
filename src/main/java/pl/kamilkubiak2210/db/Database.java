package pl.kamilkubiak2210.db;

import pl.kamilkubiak2210.db.exceptions.DuplicateException;
import pl.kamilkubiak2210.model.Actor;
import pl.kamilkubiak2210.model.Movie;
import pl.kamilkubiak2210.model.TvSeries;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<Movie> movies = new ArrayList<>();
    private final List<TvSeries> tvSeries = new ArrayList<>();
    private final List<Actor> actors = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public List<TvSeries> getTvSeries() {
        return tvSeries;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void addMovie(Movie movie) {
        boolean isDuplicate = checkForDuplicate(movie);
        if (!isDuplicate) {
            movies.add(movie);
        } else {
            throw new DuplicateException("Duplikat filmu");
        }
    }

    public void addTvSeries(TvSeries tvSerie) {
        boolean isDuplicate = checkForDuplicate(tvSerie);
        if (!isDuplicate) {
            tvSeries.add(tvSerie);
        } else {
            throw new DuplicateException("Duplikat serialu");
        }
    }

    public void addActor(Actor actor) {
        boolean isDuplicate = checkForDuplicate(actor);
        if (!isDuplicate) {
            actors.add(actor);
        } else {
            throw new DuplicateException("Duplikat aktora");
        }
    }

    public boolean checkForDuplicate(Object object) {
        if (actors.stream().anyMatch(actor -> actor.equals(object))) {
            return true;
        } else if (movies.stream().anyMatch(movie -> movie.equals(object))) {
            return true;
        } else if (tvSeries.stream().anyMatch(tvSeries -> tvSeries.equals(object))) {
            return true;
        } else {
            return false;
        }
    }
}