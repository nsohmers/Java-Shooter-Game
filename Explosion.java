import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Explosion {
    private static BufferedImage[] frames;
    private static final int frameRate = 3;
    private int x, y, frameTimer, frameIteration;
    boolean visible;

    public Explosion(int x, int y, int size) {
        frames = new BufferedImage[8];
        frameIteration = 0;
        frameTimer = 0;
        this.x = x;
        this.y = y;
        visible = true;

        if (size == 1) {
            String filePath;
            for (int i = 0; i<frames.length; i++) {
                filePath = "Images/explosion_frames/" + i + ".png";
                try {
                    frames[i] = ImageIO.read(new File(filePath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (size == 2) {
            String filePath;
            for (int i = 0; i<frames.length; i++) {
                filePath = "Images/bigger-explosion_frames/" + i + ".png";
                try {
                    frames[i] = ImageIO.read(new File(filePath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Invalid Size");
        }
        
    }

    public boolean isVisible() {
        return visible;
    }

    public void draw(Graphics g) {
        if (frameIteration < 7) {
            frameIteration = (frameTimer / frameRate) % frames.length;
            g.drawImage(frames[frameIteration], x, y, null);
        } else {
            visible = false;
        }
        frameTimer++;
    }
}
