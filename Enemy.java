import java.awt.Graphics;
import java.awt.Color;

public abstract class Enemy extends GameObject {
	protected int xspeed, yspeed, timefromspawn;
	protected double maxhealth = 1.0, health = 1.0;
	protected boolean alive, firing;
	
	public Enemy(int x, int y, int xspeed, int yspeed, int w, int h, int maxhealth) {
		super(x, y , w, h);
		//800, ( (int)(Math.random() * (591 - h)) )
		this.xspeed = xspeed;
		this.yspeed = yspeed;

		timefromspawn = 0;

		alive = true;
		firing = false;
		
		if (Math.random() < 0.5) {
			yspeed = -yspeed;
		}

		this.maxhealth = maxhealth;
		health = this.maxhealth;
		
		this.updateHitbox();
	}

	public void delete() {
		alive = false;
	}

	public void deleteHealth(int amount) {
		health -= amount;
	}
	
	public boolean isAlive() {
		return alive;
	}

	public boolean isFiring() {
		return firing;
	}
	
	public void stopFiring() {
		firing = false;
	}
	
	//ENEMY MOVEMENT/GAMEPLAY
	public void update(int playerX, int playerY) {
		if (x < 0 - w || health <= 0 || !(alive)) {
			alive = false;
			return;
		} 
	
		if ((playerY - h < y ) && (y < playerY + 50)) {
			firing = true;
		}

		x += xspeed;
		y += yspeed;		

        timefromspawn++;
		this.updateHitbox();
	}

	public void drawHealthBar(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y + h + 4, w, 5);
		g.setColor(Color.RED);
		g.fillRect(x, y + h + 4, (int) (health / maxhealth * w), 5);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, w, h);
    }
}

