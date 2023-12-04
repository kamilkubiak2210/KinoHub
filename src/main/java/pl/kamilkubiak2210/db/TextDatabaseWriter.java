package pl.kamilkubiak2210.db;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import pl.kamilkubiak2210.model.Actor;
import pl.kamilkubiak2210.model.Movie;
import pl.kamilkubiak2210.model.TvSeries;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TextDatabaseWriter implements DatabaseWriter {
    static final String TV_SERIES_FILENAME = "files/tvseries.csv";
    static final List<String> TV_SERIES_HEADERS = Arrays.asList("Tytuł", "Sezony", "Odcinki", "Producent", "Gatunek", "Opis", "Ocena");
    static final String MOVIE_FILENAME = "files/movie.csv";
    static final List<String> MOVIE_HEADERS = Arrays.asList("Tytuł", "Rok", "Reżyser", "Gatunek", "Opis", "Ocena");
    static final String ACTOR_FILENAME = "files/actor.csv";
    static final List<String> ACTOR_HEADERS = Arrays.asList("Imię", "Nazwisko", "Kraj pochodzenia");
    static final CSVFormat CSV_FORMAT = CSVFormat.Builder.create().setDelimiter(';').build();

    @Override
    public void write(Database database) {
        writeTvSeries(database);
        writeMovies(database);
        writeActor(database);
    }

    public void writeTvSeries(Database database) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(TV_SERIES_FILENAME), CSV_FORMAT)) {
            csvPrinter.printRecord(TV_SERIES_HEADERS);
            for (TvSeries tvSeries : database.getTvSeries()) {
                csvPrinter.printRecord(tvSeries.getTitle(), tvSeries.getAmountOfSeasons(), tvSeries.getAmountOfEpisodes(),
                        tvSeries.getProducer(), tvSeries.getGenre().getDescription(), tvSeries.getDescription(), tvSeries.getRating());
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd w zapisie danych");
        }
    }

    public void writeMovies(Database database) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(MOVIE_FILENAME), CSV_FORMAT)) {
            csvPrinter.printRecord(MOVIE_HEADERS);
            for (Movie movie : database.getMovies()) {
                csvPrinter.printRecord(movie.getTitle(), movie.getYear(), movie.getDirector(),
                        movie.getGenre().getDescription(), movie.getDescription(), movie.getRating());
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd w zapisie danych");
        }
    }

    public void writeActor(Database database) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(ACTOR_FILENAME), CSV_FORMAT)) {
            csvPrinter.printRecord(ACTOR_HEADERS);
            for (Actor actor : database.getActors()) {
                csvPrinter.printRecord(actor.firstName(), actor.lastName(), actor.country());
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd w zapisie danych");
        }
    }
}