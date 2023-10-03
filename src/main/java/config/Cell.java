package config;

public record Cell(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Cell.Content initialContent) {
    public enum Content { NOTHING, DOT, ENERGIZER}

    // FIXME: all these factories are convenient, but it is not very "economic" to have so many methods!
    public static Cell open(Content c) { return new Cell(false, false, false, false, c); }
    public static Cell closed(Content c) { return new Cell(true, true, false, false, c); }
    // straight pipes
    public static Cell hPipe(Content c) { return new Cell(true, false, true, false, c); }
    public static Cell vPipe(Content c) { return new Cell(false, true, false, true, c); }
    // corner cells
    public static Cell swVee(Content c) { return new Cell(true, true, false, false, c); }
    public static Cell nwVee(Content c) { return new Cell(false, true, true, false, c); }
    public static Cell neVee(Content c) { return new Cell(false, false, true, true, c); }
    public static Cell seVee(Content c) { return new Cell(true, false, false, true, c); }
    // T-shaped cells
    public static Cell nU(Content c) { return new Cell(false, true, true, true, c); }
    public static Cell eU(Content c) { return new Cell(true, false, true, true, c); }
    public static Cell sU(Content c) { return new Cell(true, true, false, true, c); }
    public static Cell wU(Content c) { return new Cell(true, true, true, false, c); }
    // U-shaped cells
    public static Cell nTee(Content c) { return new Cell(true, false, false, false, c); }
    public static Cell eTee(Content c) { return new Cell(false, true, false, false, c); }
    public static Cell sTee(Content c) { return new Cell(false, false, true, false, c); }
    public static Cell wTee(Content c) { return new Cell(false, false, false, true, c); }

    public static Cell withContent(String type, String value) {
        Content content = switch (value) {
            case "." -> Content.DOT;
            case "ENERGIZER" -> Content.ENERGIZER;
            default -> Content.NOTHING;
        };

        return switch (type) {
            case "open" -> open(content);
            case "closed" -> closed(content);
            case "hPipe" -> hPipe(content);
            case "vPipe" -> vPipe(content);
            case "swVee" -> swVee(content);
            case "nwVee" -> nwVee(content);
            case "neVee" -> neVee(content);
            case "seVee" -> seVee(content);
            case "nU" -> nU(content);
            case "eU" -> eU(content);
            case "sU" -> sU(content);
            case "wU" -> wU(content);
            case "nTee" -> nTee(content);
            case "eTee" -> eTee(content);
            case "sTee" -> sTee(content);
            case "wTee" -> wTee(content);
            default -> throw new IllegalStateException("Valeur non trouvée: " + value);
        };
    }

}
