import java.awt.Color;
import java.awt.Graphics;

public class Star {
    private int x, y, size;
    private Color star;

    public Star() {
        x = (int)(Math.random() * 798);
        y = (int)(Math.random() * 598);
        size = (int)(Math.random() * 4 + 1);
        int brightness = (255 - ((4 - size) * 15));
        star = new Color(255, 255, 255, brightness);
    }

    public void draw(Graphics g) {
        g.setColor(star);
        g.fillRect(x, y, size, size);
    }

    public void move() {
        x -= size;
        if (x < 0) {
            x = 800 + size;
            y = (int)(Math.random() * (600 - size / 2));
            size = (int)(Math.random() * 4 + 1);
        }
    }
}
