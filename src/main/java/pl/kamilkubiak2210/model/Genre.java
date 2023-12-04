package pl.kamilkubiak2210.model;

public enum Genre {
    SCI_FI("sci-fi"),
    ACTION("akcja"),
    COMEDY("komedia"),
    THRILLER("kryminał");

    private final String description;

    Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Genre convert(String genre) {
        for (Genre value : Genre.values()) {
            if (value.getDescription().equalsIgnoreCase(genre)) {
                return value;
            }
        }
        return null;
    }

    public static String printAllGenresDescription() {
        Genre[] values = Genre.values();
        int valuesCounter = 0;
        StringBuilder sb = new StringBuilder();
        for (Genre value : values) {
            if (valuesCounter + 1 != Genre.values().length) {
                sb.append(value.getDescription()).append(" ,");
                valuesCounter++;
            } else {
                sb.append(value.getDescription());
            }
        }
        return sb.toString();
    }
}