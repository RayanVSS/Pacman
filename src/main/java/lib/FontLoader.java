package lib;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import javafx.scene.text.Font;

public class FontLoader {
    private static Font pixel_font;
    static {
        try {
            URL fontUrl = FontLoader.class.getResource("/Font/pixel_font.ttf");
            File f = Paths.get(fontUrl.toURI()).toFile();
            pixel_font = Font.loadFont(f.toURI().toURL().toString(), 20.0);
            if (pixel_font == null) {
                //Pour MacOS jsp pk ça marche pas
                pixel_font = Font.loadFont(FontLoader.class.getResourceAsStream("/Font/pixel_font.ttf"), 20);
                if (pixel_font == null) {
                    pixel_font = Font.font("Arial", 20); //Default font si ça marche pas
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du chargement de la police de caractère");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Font getPixelFont() {
        return pixel_font;
    }

    public static Font getPixelFont(double size) {
        return Font.font(pixel_font.getFamily(), size);
    }
}
