import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

public class GameText {
    int x, y, spaceSize;
    float fontSize;
    String text;
    Font font;
    Color color;

    public GameText(String text, int x, int y, float fontSize, int spaceSize) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.spaceSize = spaceSize;
        this.color = Color.WHITE;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/arcade-font.ttf")).deriveFont(fontSize);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public GameText(String text, int x, int y, float fontSize, int spaceSize, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.spaceSize = spaceSize;
        this.color = color;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/arcade-font.ttf")).deriveFont(fontSize);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public void updateText(String text) {
        this.text = text;
    }

    public void addText(String text) {
        this.text += text;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.setFont(font);

        char[] words = text.toCharArray();
        int currentX = this.x;

        for (char word : words) {
            if (Character.isWhitespace(word)) {
                currentX += spaceSize;
                continue;
            }
            String string = String.valueOf(word);
            g.drawString(string, currentX, y);
            currentX += g.getFontMetrics().stringWidth(string);
        }
    }
    
}
