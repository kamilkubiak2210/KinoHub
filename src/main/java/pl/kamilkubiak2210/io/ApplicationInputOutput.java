package pl.kamilkubiak2210.io;

import pl.kamilkubiak2210.controller.Option;
import pl.kamilkubiak2210.db.Database;
import pl.kamilkubiak2210.db.SerializableDatabaseWriter;
import pl.kamilkubiak2210.db.TextDatabaseWriter;
import pl.kamilkubiak2210.io.exceptions.IncorrectDataException;
import pl.kamilkubiak2210.model.Actor;
import pl.kamilkubiak2210.model.Genre;
import pl.kamilkubiak2210.model.Movie;
import pl.kamilkubiak2210.model.TvSeries;
import pl.kamilkubiak2210.model.exceptions.GenreNotExistsException;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ApplicationInputOutput {
    private final Scanner scanner = new Scanner(System.in);
    private final int currentYear = LocalDateTime.now().getYear();
    private final TextDatabaseWriter textDatabaseWriter = new TextDatabaseWriter();
    private final SerializableDatabaseWriter serializableDatabaseWriter = new SerializableDatabaseWriter();

    public Movie createMovie() {
        System.out.println("Tworzenie nowego filmu");
        System.out.println("Podaj tytuł filmu:");
        String title = scanner.nextLine().toLowerCase();
        System.out.println("Podaj rok filmu:");
        int year = 0;
        try {
            year = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Rok musi być liczbą");
        }
        scanner.nextLine();
        int minYearForMovie = 1800;
        if (year < minYearForMovie || year > currentYear) {
            throw new IncorrectDataException("Podaj wartość pomiędzy rokiem " + minYearForMovie + " a " + currentYear);
        }
        System.out.println("Podaj reżysera filmu:");
        String director = scanner.nextLine().toLowerCase();

        System.out.println("Podaj gatunek filmu: " + Genre.printAllGenresDescription());
        Genre convertedGenre = getGenre();
        System.out.println("Podaj opis filmu:");
        String description = scanner.nextLine().toLowerCase();

        System.out.println("Podaj ocenę serialu (0-10), liczbę zmiennoprzecinkową zapisz przy użyciu przecinka:");
        double rating = getRating();
        return new Movie(title, convertedGenre, description, rating, year, director);
    }

    public TvSeries createTvSeries() {
        System.out.println("Tworzenie nowego serialu");
        System.out.println("Podaj tytuł serialu:");
        String title = scanner.nextLine().toLowerCase();

        System.out.println("Podaj liczbę sezonów :");
        int seasons = -1;
        int episodes = -1;
        try {
            seasons = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Podaj liczbę odcinków :");
            episodes = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Sezony i odcinki muszą być liczbą");
        }
        if (seasons < 1 || episodes < 1) {
            scanner.nextLine();
            throw new IncorrectDataException("Sezony i odcinki muszą znajdować się w zakresie dodatnim");
        }
        scanner.nextLine();
        System.out.println("Podaj producenta serialu:");
        String producer = scanner.nextLine().toLowerCase();

        System.out.println("Podaj gatunek serialu: " + Genre.printAllGenresDescription());
        Genre convertedGenre = getGenre();
        System.out.println("Podaj opis serialu:");
        String description = scanner.nextLine().toLowerCase();

        System.out.println("Podaj ocenę serialu (0-10), liczbę zmiennoprzecinkową zapisz przy użyciu przecinka:");
        double rating = getRating();
        return new TvSeries(title, convertedGenre, description, rating, episodes, seasons, producer);
    }

    private double getRating() {
        double rating = -1;
        try {
            rating = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.err.println("Ocena musi być liczbą zwykłą, lub zmiennoprzecinkową reprezentowaną przecinkiem");
        }
        double minRating = 0;
        double maxRating = 10;
        if (rating < minRating | rating > maxRating) {
            scanner.nextLine();
            throw new IncorrectDataException("Podaj wartość pomiędzy " + (int) minRating + " a " + (int) maxRating);
        }
        scanner.nextLine();
        return rating;
    }

    public Actor createActor() {
        System.out.println("Tworzenie nowego aktora");
        System.out.println("Podaj imię aktora:");
        String firstName = scanner.nextLine().toLowerCase();
        System.out.println("Podaj nazwisko aktora:");
        String lastName = scanner.nextLine().toLowerCase();
        System.out.println("Podaj kraj pochodzenia:");
        String country = scanner.nextLine().toLowerCase();
        return new Actor(firstName, lastName, country);
    }

    private Genre getGenre() {
        String genre = scanner.nextLine();
        Genre convertedGenre = Genre.convert(genre);
        if (convertedGenre == null) {
            throw new GenreNotExistsException("Nie ma takiej kategorii jak: " + genre);
        }
        return convertedGenre;
    }

    public int readInt() {
        int number = -1;
        try {
            number = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Opcja musi być liczbą");
        }
        scanner.nextLine();
        return number;
    }

    public void printOptions() {
        for (Option option : Option.values()) {
            System.out.println(option.getNumber() + option.getDescription());
        }
    }

    public void printAll(List<Actor> actors, List<Movie> movies, List<TvSeries> tvSeries) {
        System.out.println("Filmy:");
        if (movies.isEmpty()) {
            System.out.println("Lista filmów jest pusta");
        } else {
            System.out.println(movies);
        }
        System.out.println("Seriale:");
        if (tvSeries.isEmpty()) {
            System.out.println("Lista seriali jest pusta");
        } else {
            System.out.println(tvSeries);
        }
        System.out.println("Aktorzy:");
        if (actors.isEmpty()) {
            System.out.println("Lista aktorów jest pusta");
        } else {
            System.out.println(actors);
        }
    }

    public void exit() {
        System.out.println("Zamykam program");
    }

    public void saveAll(Database database) {
        textDatabaseWriter.write(database);
        serializableDatabaseWriter.write(database);
        System.out.println("Zapisano");
    }

    public void checkFileIntegrity(List<List<?>> read, Database database) {
        boolean actors = read.get(0).equals(database.getActors());
        boolean tvSeries = read.get(1).equals(database.getTvSeries());
        boolean movies = read.get(2).equals(database.getMovies());
        if (actors && tvSeries && movies) {
            System.out.println("Pliki .CSV zgadzają się z plikiem głównym");
        } else {
            System.err.println("Pliki .CSV nie zgadzają się z plikiem głównym, sprawdź pliki .CSV");
            System.err.println("Ostatni zapis z głównego pliku:");
            System.out.println("Aktorzy: " + read.get(0));
            System.out.println("Seriale: " + read.get(1));
            System.out.println("Filmy: " + read.get(2));
        }
    }
}