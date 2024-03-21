import java.awt.Graphics;
import java.awt.Color;

public abstract class GameObject 
{	
	protected int x, y, w, h;
	private int[] hitbox = new int[4];
	
	public GameObject(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getW(){
		return w;
	}
	
	public int getH(){
		return h;
	}

	public void addX(int amount){
		x += amount;
		this.updateHitbox();
	}

	public void addY(int amount){
		y += amount;
		this.updateHitbox();
	}
	
	public void setX(int _x){
		x = _x;
	}
	
	public void setY(int _y){
		y = _y;
	}
	
	public void setW(int _w){
		w = _w;
	}
	
	public void setH(int _h){
		h = _h;
	}

	public void updateHitbox() {
		hitbox[0] = x;
		hitbox[1] = y;
		hitbox[2] = x + w;
		hitbox[3] = y + h;
	}
	
	public int[] getHitbox() {
		return hitbox;
	}

	public void drawHitbox(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(x, y, w, h);
	}
	
	public boolean collides(int[] otherhitbox){
		if ((otherhitbox[0]- this.w < this.x ) &&

			(this.x < otherhitbox[2]) && 

			(otherhitbox[1] - this.h < this.y ) && 

			( this.y < otherhitbox[3] ))
		{
			return true;
		} 
		return false;
	}
}