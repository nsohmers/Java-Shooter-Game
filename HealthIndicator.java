import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HealthIndicator {
    private static BufferedImage[] image;
    private int x, y;

    public HealthIndicator(int x, int y) {
        image = new BufferedImage[2];
        this.x = x;
        this.y = y;

        String filePath;

        filePath = "Images/heart_images/greyheart.png";
        try {
            image[0] = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        filePath = "Images/heart_images/redheart.png";
        try {
            image[1] = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void draw(int lives, Graphics g) {
        int currentx = x;
        for (int hearts = 0; hearts < 3; hearts++) {
            if (lives - hearts > 0) {
                g.drawImage(image[1], currentx, y, null);
            } else {
                g.drawImage(image[0], currentx, y, null);
            }

            currentx += 40;
        }
    }
}
