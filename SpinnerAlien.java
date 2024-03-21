import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class SpinnerAlien extends Enemy {
    private static BufferedImage[] frames;
    private static final int frameRate = 5;
    private int frameTimer;

    public SpinnerAlien() {
        super(800, ( (int)(Math.random() * (591 - 40 - 100)) ), -2, 0, 40, 40, 30);
        frames = new BufferedImage[7];
        frameTimer = 0;

        String filePath;
        for (int i = 0; i<frames.length; i++) {
            filePath = "Images/spinner-alien_frames/" + i + ".png";
            try {
                frames[i] = ImageIO.read(new File(filePath));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override 
    public void update(int x, int y) {
        yspeed = (int) (3 * Math.sin(this.timefromspawn / 25));
        super.update(x, y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(frames[(frameTimer / frameRate) % frames.length], x, y, null);
        frameTimer++;
    }
}
