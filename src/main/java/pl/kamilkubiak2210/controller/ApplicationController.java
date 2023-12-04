package pl.kamilkubiak2210.controller;

import pl.kamilkubiak2210.controller.exceptions.OptionNotExistsException;
import pl.kamilkubiak2210.db.Database;
import pl.kamilkubiak2210.db.SerializableDatabaseReader;
import pl.kamilkubiak2210.db.TextDatabaseReader;
import pl.kamilkubiak2210.db.exceptions.DuplicateException;
import pl.kamilkubiak2210.io.ApplicationInputOutput;
import pl.kamilkubiak2210.io.exceptions.IncorrectDataException;
import pl.kamilkubiak2210.model.Actor;
import pl.kamilkubiak2210.model.Movie;
import pl.kamilkubiak2210.model.TvSeries;
import pl.kamilkubiak2210.model.exceptions.GenreNotExistsException;

import java.util.InputMismatchException;
import java.util.List;

public class ApplicationController {
    private Database database = new Database();
    private final ApplicationInputOutput reader = new ApplicationInputOutput();
    private final TextDatabaseReader textDatabaseReader = new TextDatabaseReader();
    private final SerializableDatabaseReader serializableDatabaseReader = new SerializableDatabaseReader();

    public void mainLoop() {
        loadAllDatabaseFiles();
        int userOption;
        do {
            reader.printOptions();
            userOption = reader.readInt();
            try {
                options(userOption);
            } catch (OptionNotExistsException e) {
                System.err.println(e.getMessage());
            }
        } while (userOption != Option.EXIT.getNumber());
    }

    private void options(int option) {
        Option convertedOption = Option.convert(option);
        if (convertedOption != null) {
            switch (convertedOption) {
                case ADD_MOVIE -> addMovie();
                case ADD_TV_SERIES -> addTvSeries();
                case ADD_ACTOR -> addActor();
                case PRINT_ALL -> printAll();
                case SAVE_ALL -> reader.saveAll(database);
                case EXIT -> reader.exit();
            }
        } else {
            throw new OptionNotExistsException("Opcja nie istnieje");
        }
    }

    private void addMovie() {
        try {
            Movie movie = reader.createMovie();
            database.addMovie(movie);
        } catch (DuplicateException | InputMismatchException | GenreNotExistsException | IncorrectDataException e) {
            System.err.println(e.getMessage());
        }

    }

    private void addTvSeries() {
        try {
            TvSeries tvSeries = reader.createTvSeries();
            database.addTvSeries(tvSeries);
        } catch (DuplicateException | InputMismatchException | GenreNotExistsException | IncorrectDataException e) {
            System.err.println(e.getMessage());
        }
    }

    private void addActor() {
        try {
            Actor actor = reader.createActor();
            database.addActor(actor);
        } catch (DuplicateException e) {
            System.err.println(e.getMessage());
        }
    }

    private void printAll() {
        reader.printAll(database.getActors(), database.getMovies(), database.getTvSeries());
    }

    private void loadAllDatabaseFiles() {
        database = textDatabaseReader.read();
        readSerializableDatabase();
    }

    private void readSerializableDatabase() {
        try {
            List<List<?>> read = serializableDatabaseReader.read();
            reader.checkFileIntegrity(read, database);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}