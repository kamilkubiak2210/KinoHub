package pl.kamilkubiak2210.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableDatabaseWriter implements Serializable {
    static final String FILENAME = "files/application.db";

    public void write(Database database) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(database.getActors());
            oos.writeObject(database.getTvSeries());
            oos.writeObject(database.getMovies());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}