import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Meteorite extends Enemy {
    private BufferedImage image;
    private int size;
    private static int[] meteoriteWidths = {45, 79, 98};
    private static int[] meteoriteHeights = {30, 54, 49};
    private static int[] meteoriteHealths = {15, 50, 70};

    public Meteorite(int index) {
        super(800, ( (int)(Math.random() * (591 - getInitialHeight(index))) ), -1 , 0, getInitialWidth(index), getInitialHeight(index), getInitialHealth(index));
        size = index;
        String filePath = "Images/meteorite_images/meteorite" + index + ".png";
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.updateHitbox();
    }

    public int getSize() {
        return size;
    }

    private static int getInitialWidth(int index) {
        return meteoriteWidths[index - 1];
    }

    private static int getInitialHeight(int index) {
        return meteoriteHeights[index - 1];
    }

    private static int getInitialHealth(int index) {
        return meteoriteHealths[index - 1];
    }

    public void update(int playerX, int playerY) {
		if (x < 0 - w || health <= 0) {
			alive = false;
			return;
		}

        x += xspeed;		

		this.updateHitbox();
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
}
