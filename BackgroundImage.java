
import java.awt.Graphics;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class BackgroundImage {
    private BufferedImage image;
    private int x, y, w, h, dimming;
    private int xspeed;
    private boolean visible;
    private Color dimLayer;

    public BackgroundImage(int x, int y, int xspeed, String filePath) {
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.dimming = 0;
		try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.w = image.getWidth();
        this.h = image.getHeight();
        visible = true;
        dimLayer = new Color(0, 0, 0, dimming);
    }

    public BackgroundImage(int x, int y, int xspeed, String filePath, int dimming) {
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.dimming = dimming;
		try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.w = image.getWidth();
        this.h = image.getHeight();
        visible = true;
        dimLayer = new Color(0, 0, 0, dimming);
    }

    public void delete() {
        visible = false;
    }

    public boolean isVisible() {
		return visible;
	}

    public void reset() {
        visible = true; 
        x = 800;
    }

    public void update() {
        if (visible) {
			if (x > (0 - w)) {
				x += xspeed;
			} else {
				this.delete();
			}
		}
    }

    public void draw(Graphics g) {
        g.setColor(dimLayer);
        g.drawImage(image, x, y, null);
        g.fillRect(x, y, w, h);
    }
}
