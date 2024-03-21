import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class GunnerAlien extends Enemy {
    private static BufferedImage[] frames;
    private static final int frameRate = 10;
    private int frameTimer, velocity;

    public GunnerAlien() {
        super(800, ( (int)(Math.random() * (591 - 64)) ), -1, 0, 62, 64, 100);
        frames = new BufferedImage[4];
        frameTimer = 0;
        velocity = 0;
        
        String filePath;
        for (int i = 0; i<frames.length; i++) {
            filePath = "Images/gunner-alien_frames/" + i + ".png";
            try {
                frames[i] = ImageIO.read(new File(filePath));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(int x, int y) {
        velocity = Math.min(Math.max(velocity+ (int) (Math.random() * 2 - 1), -4), 4);
        yspeed = Math.min(Math.max(((y - this.y) / 20), -1), 1) + velocity;

        super.update(x, y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(frames[(frameTimer / frameRate) % frames.length], x, y, null);
        frameTimer++;
    }
}
