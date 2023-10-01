package config;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MazeLoad {
    public static Cell[][] make() {
        // load a maze from a json file
        String file = "src/main/java/config/maze.json";

        try {
            String content = new String(Files.readAllBytes(Paths.get(file)));
            JSONObject json = new JSONObject(content);
            JSONArray maze = json.getJSONArray("maze");
            Cell[][] cells = new Cell[maze.length()][maze.getJSONArray(0).length()];
            for (int i = 0; i < maze.length(); i++) {
                JSONArray row = maze.getJSONArray(i);
                for (int j = 0; j < row.length(); j++) {
                    JSONObject cell = row.getJSONObject(j);
                    String type = cell.getString("type");
                    String value = cell.getString("value");
                    cells[i][j] = Cell.withContent(type, value);
                }
            }
            //print(cells);
            return cells;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void print(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            Cell[] row = cells[i];
            for (int j = 0; j < row.length; j++) {
                Cell cell = row[j];
                System.out.print(cell);
            }
            System.out.println();
        }
    }

}