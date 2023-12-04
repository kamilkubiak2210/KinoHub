package pl.kamilkubiak2210.controller;

public enum Option {
    ADD_MOVIE(0, " - Dodanie filmu"),

    ADD_TV_SERIES(1, " - Dodanie serialu"),

    ADD_ACTOR(2, " - Dodanie aktora"),

    PRINT_ALL(3, " - Wyświetl wszystko"),
    SAVE_ALL(4, " - Zapisz wszystko"),

    EXIT(5, " - Wyjdź");

    private final int number;
    private final String description;

    Option(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public static Option convert(int option) {
        for (Option value : Option.values()) {
            if (value.getNumber() == option) {
                return value;
            }
        }
        return null;
    }
}