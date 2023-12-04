package pl.kamilkubiak2210.db;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import pl.kamilkubiak2210.model.Actor;
import pl.kamilkubiak2210.model.Genre;
import pl.kamilkubiak2210.model.Movie;
import pl.kamilkubiak2210.model.TvSeries;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextDatabaseReader implements DatabaseReader {
    @Override
    public Database read() {
        Database database = new Database();
        try {
            database.getMovies().addAll(readMovies());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            database.getTvSeries().addAll(readTvSeries());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            database.getActors().addAll(readActor());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return database;
    }

    private List<TvSeries> readTvSeries() {
        List<TvSeries> newList = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(new FileReader(TextDatabaseWriter.TV_SERIES_FILENAME), TextDatabaseWriter.CSV_FORMAT)) {
            List<CSVRecord> lines = csvParser.stream().skip(1L).toList();
            for (CSVRecord line : lines) {
                String[] lineValues = line.values();
                String title = lineValues[0];
                int seasons = Integer.parseInt(lineValues[1]);
                int episodes = Integer.parseInt(lineValues[2]);
                String producer = lineValues[3];
                String genre = lineValues[4];
                Genre convert = Genre.convert(genre);
                String description = lineValues[5];
                double rating = Double.parseDouble(lineValues[6]);
                TvSeries tvSeries = new TvSeries(title, convert, description, rating, episodes, seasons, producer);
                newList.add(tvSeries);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nie można odnaleźć pliku: " + TextDatabaseWriter.TV_SERIES_FILENAME);
        }
        return newList;
    }

    private List<Movie> readMovies() {
        List<Movie> newList = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(new FileReader(TextDatabaseWriter.MOVIE_FILENAME), TextDatabaseWriter.CSV_FORMAT)) {
            List<CSVRecord> lines = csvParser.stream().skip(1L).toList();
            for (CSVRecord line : lines) {
                String[] lineValues = line.values();
                String title = lineValues[0];
                int year = Integer.parseInt(lineValues[1]);
                String director = lineValues[2];
                String genre = lineValues[3];
                Genre convert = Genre.convert(genre);
                String description = lineValues[4];
                double rating = Double.parseDouble(lineValues[5]);
                Movie movie = new Movie(title, convert, description, rating, year, director);
                newList.add(movie);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nie można odnaleźć pliku: " + TextDatabaseWriter.MOVIE_FILENAME);
        }
        return newList;
    }

    private List<Actor> readActor() {
        List<Actor> newList = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(new FileReader(TextDatabaseWriter.ACTOR_FILENAME), TextDatabaseWriter.CSV_FORMAT)) {
            List<CSVRecord> lines = csvParser.stream().skip(1L).toList();
            for (CSVRecord line : lines) {
                String[] lineValues = line.values();
                String firstName = lineValues[0];
                String lastName = lineValues[1];
                String country = lineValues[2];
                Actor actor = new Actor(firstName, lastName, country);
                newList.add(actor);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nie można odnaleźć pliku: " + TextDatabaseWriter.ACTOR_FILENAME);
        }
        return newList;
    }
}