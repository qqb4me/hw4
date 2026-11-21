package championship;

public enum Position {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий");

    private final String russianName;

    Position(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }

    public static Position fromCsv(String str) {
        return Position.valueOf(str.trim().toUpperCase());
    }
}
