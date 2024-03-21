import java.awt.Graphics;

public abstract class Projectile extends GameObject 
{
	protected boolean visible;
	protected int xspeed, yspeed;

    public Projectile(int x, int y) {
		super(x, y, 10, 10);
		xspeed = 1;
		yspeed = 0;
		visible = true;
		this.updateHitbox();
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void delete(){
		visible = false;
	}

	public void update() {
		if (visible) {
			if (x > 0 && x < 800) {
				x += xspeed;
			} else {
				this.delete();
			}
		}
		this.updateHitbox();
	}

	public void draw(Graphics g) {}
}