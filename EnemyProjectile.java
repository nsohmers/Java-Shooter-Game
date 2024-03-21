import java.awt.Graphics;
import java.awt.Color;
public class EnemyProjectile extends Projectile {
    public EnemyProjectile(int x, int y) {
      super(x, y);
      xspeed = -4;
    }

    @Override
    public void draw(Graphics g){
      g.setColor(Color.RED);
      g.fillOval(x, y, w, h);
	}
}
