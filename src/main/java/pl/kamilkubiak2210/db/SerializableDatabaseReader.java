package pl.kamilkubiak2210.db;

import pl.kamilkubiak2210.model.Actor;
import pl.kamilkubiak2210.model.Movie;
import pl.kamilkubiak2210.model.TvSeries;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableDatabaseReader implements Serializable {
    public List<List<?>> read() {
        List<List<?>> allLists = new ArrayList<>();
        List<Actor> actorList;
        List<Movie> movieList;
        List<TvSeries> seriesList;
        try (
                FileInputStream fileInputStream = new FileInputStream(SerializableDatabaseWriter.FILENAME);
                ObjectInputStream ois = new ObjectInputStream(fileInputStream)
        ) {
            actorList = (List<Actor>) ois.readObject();
            movieList = (List<Movie>) ois.readObject();
            seriesList = (List<TvSeries>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Nie można odnaleźć pliku: " + SerializableDatabaseWriter.FILENAME + ", lub jest uszkodzony");
        }
        allLists.add(actorList);
        allLists.add(movieList);
        allLists.add(seriesList);
        return allLists;
    }
}