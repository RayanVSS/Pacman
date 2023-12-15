package lib;

import java.io.InputStream;
import javafx.scene.text.Font;

public class FontLoader {
    private static Font pixel_font;
    static {
        try {
            InputStream fontStream = FontLoader.class.getResourceAsStream("/Font/pixel_font.ttf");
            pixel_font = Font.loadFont(fontStream, 20.0);
            if (pixel_font == null) {
                // Pour MacOS jsp pk ça marche pas
                pixel_font = Font.loadFont(FontLoader.class.getResourceAsStream("/Font/pixel_font.ttf"), 20);
                if (pixel_font == null) {
                    pixel_font = Font.font("Arial", 20); // Default font si ça marche pas
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du chargement de la police de caractère");
        }
    }

    public static Font getPixelFont() {
        return pixel_font;
    }

    public static Font getPixelFont(double size) {
        return Font.font(pixel_font.getFamily(), size);
    }
}
