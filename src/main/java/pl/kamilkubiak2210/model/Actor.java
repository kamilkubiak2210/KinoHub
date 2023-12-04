package pl.kamilkubiak2210.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record Actor(String firstName, String lastName, String country) implements Serializable {
    @Serial
    private static final long serialVersionUID = 3333L;

    @Override
    public String toString() {
        return firstName + " " + lastName + ", pochodzenie: " + country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Actor actor = (Actor) o;
        return Objects.equals(firstName, actor.firstName) && Objects.equals(lastName, actor.lastName) && Objects.equals(country, actor.country);
    }
}