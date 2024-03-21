import java.awt.Graphics;
import java.awt.Color;
public class PlayerProjectile extends Projectile {
    private boolean charging;
    private double size;
    private int[] rgb = {99, 172, 187};

    public PlayerProjectile(int x, int y) {
        super(x, y);
        size = 5.0;
        xspeed = 10;
        charging = true;
    }

    public boolean isCharging() {
        return charging;
    }

    public void fire() {
        charging = false;
        xspeed = (int) ( -(0.15 * size) + 12 );
    }

    public int getDamage() {
        return ( (int) (((1.0 / (10.0 + Math.abs(size - 45)))) * 1162 - 15) );
    }

    public void update(int x, int y) {
        if (charging) {
            if (size < 45.0) {
                size += ( (1.0 / (1.0 + size)) * 8.7 ) ;
            }
            w = (int) size;
            h = (int) size;
            this.y = y - ( (int) size / 2);
        } else {
            super.update();
        }

		this.updateHitbox();
	}

    public void decreaseSize(int amount) {
        if (size - amount > 0) {
            size -= amount;
        } else {
            this.delete();
        }
    }

    @Override
    public void draw(Graphics g){
        rgb[0] = 99;
        rgb[1] = 172;
        rgb[2] = 187;
        for (int i = 5; i > 0; i--) {
            Color projectileColor = new Color(rgb[0], rgb[1], rgb[2]);
            g.setColor(projectileColor);
            int innerRadius = (int) (size * i / 5.0);
            int innerDifference = (int) ((size - innerRadius) / 2.0);
            g.fillOval(x + innerDifference, y + innerDifference, innerRadius, innerRadius);

            for (int j = 0; j < rgb.length; j++) {
                if ((rgb[j] + 30) < 255) {
                    rgb[j] += 30;
                } else {
                    rgb[j] = 255;
                }
            }
        }
	}
}