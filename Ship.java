import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Ship extends GameObject {	
	private static BufferedImage[] frames;
	private int frameIteration, frameTimer, lives; 
	private static final int frameRate = 7;
	private boolean existingChargingProjectile;
	private MovementState state;

	public Ship(int x, int y) {
		super(x, y, 94, 50);
		frames = new BufferedImage[5];
		frameIteration = 2;
        frameTimer = 0;

        String filePath;
        for (int i = 0; i<frames.length; i++) {
            filePath = "Images/ship_frames/" + i + ".png";
            try {
                frames[i] = ImageIO.read(new File(filePath));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

		lives = 3;
		state = MovementState.IDLE;
		existingChargingProjectile = false;
	}
	
	public void moveUp() {
		y -= 4;
		state = MovementState.MOVING_UP;
		this.updateHitbox();
	}
	
	public void moveDown() {
		y += 4;
		state = MovementState.MOVING_DOWN;
		this.updateHitbox();
	}

	public void deleteLife() {
		lives--;
	}

	public int getLives() {
		return lives;
	}

	public void resetLives() {
		lives = 3;
	}

	public void releaseChargingProjectile() {
		existingChargingProjectile = false;
	}

	public void createChargingProjectile() {
		existingChargingProjectile = true;
	}

	public boolean hasChargingProjectile() {
		return existingChargingProjectile;
	}
	
	public void draw(Graphics g){
		switch (state) {
			case IDLE:
				frameIteration = 2;
				break;
			case MOVING_UP:
				if (frameIteration < 4 && frameTimer % frameRate == 0) {
					frameIteration++;
				}
				break;
			case MOVING_DOWN:
				if (frameIteration > 0 && frameTimer % frameRate == 0) {
					frameIteration--;
				}
				break;
		}

		state = MovementState.IDLE;
		g.drawImage(frames[frameIteration], x, y, null, null);
		frameTimer++;
	}

	private enum MovementState {
		IDLE,
		MOVING_UP,
		MOVING_DOWN,
	}
	
}
