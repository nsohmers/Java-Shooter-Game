import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
public class Screen extends JPanel implements KeyListener, MouseListener  {
    private Ship player;
    private Star[] starArray;
    private Enemy[] enemyArray;
    private HealthIndicator healthIndicator;

    private ArrayList<Projectile> projectilesFired;
    private ArrayList<BackgroundImage> loadedBackgroundImages;
    private ArrayList<GameText> gameTexts;
    private ArrayList<Explosion> explosions;

    private static int TIMER, LEVEL;
    private int transparency, enemiesRemaining;

    private boolean[] keys = new boolean[5];
    private boolean fading, levelingup;

    private BackgroundImage title;
    private GameText levelGameText, enemiesRemaingGameText, gameOverGameText, winGameText, startGameText, continueGameText;

    public Screen() {
        player = new Ship(5, 300);
        enemyArray = new Enemy[25];
        starArray = new Star[50];
        healthIndicator = new HealthIndicator(670, 15);
        title = new BackgroundImage(138, 50, 0, "Images/title.png");

        projectilesFired = new ArrayList<>();
        loadedBackgroundImages = new ArrayList<>();
        gameTexts = new ArrayList<>();
        explosions = new ArrayList<>();

        for (int i = 0; i < 5; i++) keys[i] = false;

        for (int i = 0; i < starArray.length; i++) {
            starArray[i] = new Star();
        }

        enemiesRemaingGameText = new GameText("ENEMIES REMAINING", 490, 580, 28f, 10, new Color(146, 19, 17));
        levelGameText = new GameText("LEVEL", 500, 37, 38f, 20);
        startGameText = new GameText("PRESS SPACE OR CLICK TO START", 70, 500, 45f, 15);
        continueGameText = new GameText("PRESS SPACE OR CLICK TO CONTINUE", 25, 500, 45f, 15);
        gameOverGameText = new GameText("GAME OVER", 150, 280, 108f, 30, Color.RED);
        winGameText = new GameText("YOU WON", 200, 280, 108f, 30, Color.YELLOW);

        transparency = 0;
        enemiesRemaining = 0;
        fading = false;

        TIMER = 0;
        LEVEL = 0;
        setFocusable(true);
        addKeyListener(this);
		addMouseListener(this);
    }

    @Override
	public Dimension getPreferredSize() {
		return new Dimension(800,600);
	}

    @Override public void keyTyped( KeyEvent e ){} // Override keyTyped because it is not used
	@Override public void mouseEntered( MouseEvent e ) {} // Override mouse functions not used
	@Override public void mouseExited( MouseEvent e ) {}
	@Override public void mouseClicked( MouseEvent e ){}

    public void mousePressed(MouseEvent e) { //Left-Click
		keys[0] = true;
	}

    public void mouseReleased(MouseEvent e) { //Left-Click
		keys[0] = false;
	}
	
	public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 32: //Space bar
                keys[1] = true;
                break;
            case 38: //Up Arrow
                keys[2] = true;
                break;
            case 40: //Down Arrow
                keys[3] = true;
                break;
            case 79: //"o" Key
                keys[4] = true;
        }
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
            case 32: //Space bar
                keys[1] = false;
                break;
            case 38: //Up Arrow
                keys[2] = false;
                break;
            case 40: //Down Arrow
                keys[3] = false;
                break;
            case 79: //"o" Key
                keys[4] = false;
        }
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,800,600);
        g.setFont(getFont());


        for (BackgroundImage image : loadedBackgroundImages) {
            image.draw(g);
        }

        if (LEVEL >= 0 && LEVEL < 3) {
            for (Star star: starArray) {
                star.draw(g);
            }
    
            for (Projectile projectile : projectilesFired) {
                projectile.draw(g);
                //projectile.drawHitbox(g);
            }
    
            for (Enemy enemy: enemyArray) {
                if (enemy != null) {
                    enemy.draw(g);
                    enemy.drawHealthBar(g);
                    //enemy.drawHitbox(g);
                }
            }

            for (Explosion explosion: explosions) {
                explosion.draw(g);
            }
            
            player.draw(g);
            //player.drawHitbox(g);
        }

        if (LEVEL > 0 && LEVEL < 3) {
            gameTexts.clear();
            gameTexts.add(levelGameText);
            gameTexts.add(enemiesRemaingGameText);

            healthIndicator.draw(player.getLives(), g);

            levelGameText.updateText("LEVEL" + LEVEL);
            enemiesRemaingGameText.updateText(enemiesRemaining + " ENEMIES REMAINING");

            g.setColor(new Color(0, 0, 0, transparency));
            g.fillRect(0,0,800,600);

        } else {
            switch (LEVEL) {
                case 0:
                    gameTexts.clear();
                    gameTexts.add(startGameText);
                    if ((TIMER / 100) % 2 == 0) {
                        startGameText.updateText("PRESS SPACE OR CLICK TO START");
                    } else {
                        startGameText.updateText("");
                    }
                    break;
                case -1:
                    gameTexts.clear();
                    gameTexts.add(gameOverGameText);
                    gameOverGameText.updateText("");

                    if (TIMER < 50) {
                        String gameOverText = "GAME OVER";

                        for (int i = 0; i < ((TIMER - 15) / 5) % 10; i++) {
                            gameOverGameText.addText(Character.toString(gameOverText.charAt(i)));
                            
                        }
                    } else {
                        gameOverGameText.updateText("GAME OVER");

                        if ((TIMER / 100) % 2 == 0) {
                            gameTexts.add(continueGameText);
                        } else {
                            gameTexts.remove(continueGameText);
                        }
                    }
                    break;
                case 3:
                    gameTexts.clear();
                    gameTexts.add(winGameText);
                    winGameText.updateText("");

                    if (TIMER < 50) {
                        String winText = "YOU WON";

                        for (int i = 0; i < ((TIMER - 15) / 5) % 10; i++) {
                            winGameText.addText(Character.toString(winText.charAt(i)));
                            
                        }
                    } else {
                        winGameText.updateText("YOU WON");

                        if ((TIMER / 100) % 2 == 0) {
                            gameTexts.add(continueGameText);
                        } else {
                            gameTexts.remove(continueGameText);
                        }
                    }
                    break;
                
            }
        }

        for (GameText text : gameTexts) {
            text.draw(g);
        }
	}

    private void addMeteorite() {
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] == null) {
                enemyArray[i] = new Meteorite(((int)(Math.random() * 3) + 1));
                break;
            }
        }
    }

    private void addSpinnerEnemy() {
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] == null) {
                enemyArray[i] = new SpinnerAlien();
                break;
            }
        }
    }

    private void addGunnerEnemy() {
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] == null) {
                enemyArray[i] = new GunnerAlien();
                break;
            }
        }
    }

    private void resetEnemies() {
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] != null) {
                enemyArray[i].setX(enemyArray[i].getX() + 800);
            }
        }
    }
    private void clearEnemies() {
        explosions.clear();
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] != null) {
                enemyArray[i] = null;
            }
        }
    }

    private int enemiesAmount() {
        int counter = 0;
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    private boolean areEnemiesLeft() {
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] != null) {
                return true;
            }
        }
        return false;
    }

    private void levelReset() {
        fading = true;
        this.resetEnemies();
        projectilesFired.clear();
    }

    private void levelUp() {
        levelingup = true;
        this.levelReset();
    }

    private void updateStars() { //For updating Stars
        for (Star star: starArray) {
                star.move();
        }
    }

    private void updateBackgroundImages() { //For updating and deleting background images
        for (int i = 0; i < loadedBackgroundImages.size(); i++) { 
            if (!(loadedBackgroundImages.get(i).isVisible())) {
                loadedBackgroundImages.remove(loadedBackgroundImages.get(i));
                i--;
                continue;
            }
            loadedBackgroundImages.get(i).update();
        }
    }

    private void updatePlayer() { //For updating player movement and projectiles
        if (!((player.getY() > 0 && keys[2]) && (player.getY() < 600 - player.getW() && keys[3]))) {
            if (player.getY() > 0 && keys[2]) { //Up Arrow Pressed
                player.moveUp();
            } 

            if (player.getY() < 600 - player.getH() && keys[3]) { //Down Arrow Pressed
                player.moveDown();
            }
        }

        if ((keys[0] || keys[1]) && !(player.hasChargingProjectile()))  { //Space Bar or Mouse Pressed
            projectilesFired.add(new PlayerProjectile(95, player.getY() + 24)); //Add proj to screen and let program know there is charging projectile
            player.createChargingProjectile();

        } else if (player.hasChargingProjectile() && !(keys[0] || keys[1])) { // Fire charging projectile if keys are released and let program know there is no charging projectile
            for (Projectile projectile : projectilesFired) {
                if (projectile instanceof PlayerProjectile && ((PlayerProjectile)projectile).isCharging()) {
                    ((PlayerProjectile)projectile).fire();
                }
            }
            player.releaseChargingProjectile();
        }
    }

    private void updateProjectiles() {
        for (int i = 0; i < projectilesFired.size(); i++) { //For updating and deleting projectiles
            Projectile projectile = projectilesFired.get(i);
            if (!(projectile.isVisible())) {
                projectilesFired.remove(projectile);
                i--;
                continue;
            }

            if (projectile instanceof PlayerProjectile) {
                for (Projectile otherprojectile : projectilesFired) {
                    if (otherprojectile instanceof EnemyProjectile && otherprojectile.collides(projectile.getHitbox())) {
                        ((PlayerProjectile)projectile).decreaseSize(15);
                        otherprojectile.delete();
                    }
                }
                ((PlayerProjectile)projectile).update(95, player.getY() + 24);
                continue;
            }

            if ((projectile instanceof EnemyProjectile) && player.collides(projectile.getHitbox())) {
                projectilesFired.remove(projectile);
                player.deleteLife();
                this.levelReset();
                continue;
            }

            projectile.update();
        }
    }

    private void updateEnemies() {
        for (int i = 0; i < enemyArray.length; i++) {
            if (enemyArray[i] != null) {
                for (Projectile projectile : projectilesFired) {
                    if ((projectile instanceof PlayerProjectile) && enemyArray[i].collides(projectile.getHitbox())) {
                        projectile.delete();
                        enemyArray[i].deleteHealth(((PlayerProjectile)projectile).getDamage());
                    } 
                }

                if (enemyArray[i] instanceof GunnerAlien && enemyArray[i].isFiring() && (TIMER % 60) == 0) {
                    projectilesFired.add(new EnemyProjectile(enemyArray[i].getX(), enemyArray[i].getY() + 25));
                    enemyArray[i].stopFiring();
                }

                if (enemyArray[i] instanceof SpinnerAlien && enemyArray[i].isFiring() && (TIMER % 80) == 0) {
                    projectilesFired.add(new EnemyProjectile(enemyArray[i].getX(), enemyArray[i].getY() + 25));
                    enemyArray[i].stopFiring();
                }
                

                enemyArray[i].update(player.getX(), player.getY());

                if (enemyArray[i].collides(player.getHitbox()) || (enemyArray[i].getX() < 0 - enemyArray[i].getH())) {
                    player.deleteLife();
                    this.levelReset();
                    continue;
                }
                
                if (!(enemyArray[i].isAlive())) {
                    if (enemyArray[i] instanceof Meteorite) {
                        switch (((Meteorite)enemyArray[i]).getSize()) {
                            case 1:
                                explosions.add(new Explosion(enemyArray[i].getX() - 5, enemyArray[i].getY() - 5, 1));
                                break;
                            case 2:
                                explosions.add(new Explosion(enemyArray[i].getX() + 10, enemyArray[i].getY(), 2));
                                break;
                            case 3:
                                explosions.add(new Explosion(enemyArray[i].getX() + 15, enemyArray[i].getY(), 2));
                                break;
                        }
                    } else if (enemyArray[i] instanceof GunnerAlien) {
                        explosions.add(new Explosion(enemyArray[i].getX() + 5, enemyArray[i].getY(), 2));
                    } else {
                        explosions.add(new Explosion(enemyArray[i].getX() - 5, enemyArray[i].getY() - 5, 1));
                    }
                    
                    enemyArray[i] = null;
                    if (enemiesRemaining > 0) {
                        enemiesRemaining--;
                    }
                    i--;
                    break;
                }
            }
        }
    }

    private void updateExplosions() {
        for (int i = 0; i < explosions.size(); i++) { 
            if (!(explosions.get(i).isVisible())) {
                explosions.remove(explosions.get(i));
                i--;
                continue;
            }
        }
    }

    public void animate() {
        while ( true ) {
            try {
                Thread.sleep(10);
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
            
            if (keys[4] && (0 < LEVEL && LEVEL < 3)) { //Cheat Key
                keys[4] = false;
                this.clearEnemies();
                this.levelUp();
            }

            if (fading) { // Code for fading in and out
                if ((transparency + 15)< 255) {
                    transparency += 15;
                } else {
                    fading = false;
                    player.setY(300);
                    loadedBackgroundImages.clear();
                    if (levelingup) {
                        switch (LEVEL) {
                            case 0:
                                enemiesRemaining = 3;
                                break;
                            case 1:
                                enemiesRemaining = 10;
                                break;
                            case 2:
                                enemiesRemaining = 1;
                                break;
                        }
                        LEVEL++;
                        levelingup = false;
                    }
                    TIMER = 0;
                }
            } else if ((transparency - 3) > 0) {
                transparency -= 3;
            }

            switch (LEVEL) {
                case -1: //Lost
                    if (TIMER >= 50 && (keys[0] || keys[1])) {
                        keys[0] = false;
                        keys[1] = false;
                        this.levelUp();
                    }
                    
                    break;
                case 0: //Title Screen
                    loadedBackgroundImages.remove(title);
                    
                    if (keys[0] || keys[1]) {
                        keys[0] = false;
                        keys[1] = false;
                        player.resetLives();
                        this.levelUp();
                    }

                    loadedBackgroundImages.add(title);

                    updateBackgroundImages();

                    updateStars();
                    break;
                case 1: //Level One
                    switch (TIMER) {
                        case 0:
                            loadedBackgroundImages.add(new BackgroundImage(300, 50, -1, "Images/space_images/sun.png", 150));
                            loadedBackgroundImages.add(new BackgroundImage(600, 200, -2, "Images/space_images/moon.png"));
                            break;
                        case 100:
                            loadedBackgroundImages.add(new BackgroundImage(800, 350, -2, "Images/space_images/planet4.png"));
                            break;
                        case 200:
                            loadedBackgroundImages.add(new BackgroundImage(800, 200, -1, "Images/space_images/planet6.png", 75));
                            break;
                        case 350:
                            loadedBackgroundImages.add(new BackgroundImage(800, 75, -2, "Images/space_images/planet3.png"));
                            break;
                        case 550:
                            loadedBackgroundImages.add(new BackgroundImage(800, 400, -1, "Images/space_images/planet1.png", 75));
                            break;
                        case 750:
                            loadedBackgroundImages.add(new BackgroundImage(800, 150, -2, "Images/space_images/planet5.png"));
                            break;
                        case 825:
                            loadedBackgroundImages.add(new BackgroundImage(800, 300, -1, "Images/space_images/planet0.png", 100));
                            break;
                        case 950:
                            loadedBackgroundImages.add(new BackgroundImage(800, 250, -2, "Images/space_images/planet2.png"));
                            break;
                    }
                        
                    if (TIMER % 400 == 0 && TIMER != 0) {
                        switch (enemiesRemaining - enemiesAmount()) {
                            case 3:
                                addMeteorite();
                                break;
                            case 2:
                                addSpinnerEnemy();
                                break;
                            case 1:
                                addMeteorite();
                                break;
                        }
                    }

                    if ((!(this.areEnemiesLeft()) && enemiesRemaining == 0) && TIMER != 0) {
                        this.levelUp();
                    }

                    updateBackgroundImages();

                    updateStars();

                    updatePlayer();

                    updateProjectiles();

                    updateEnemies();

                    updateExplosions();
                    
                    break;
                case 2: //Level Two
                    switch (TIMER) {
                        case 0:
                            loadedBackgroundImages.add(new BackgroundImage(300, 50, -1, "Images/space_images/spacestation.png", 150));
                            break;
                    }
                    if (TIMER % 400 == 0 && TIMER != 0) {
                        loadedBackgroundImages.add(new BackgroundImage(800, ((int) (Math.random() * 543)), -3, "Images/space_images/spaceship.png"));
                    }
                    if (TIMER % 1200 == 0&& TIMER != 0) {
                        loadedBackgroundImages.add(new BackgroundImage(800, ((int) (Math.random() * 327)), -2, "Images/space_images/bigger-spaceship.png"));
                    }
                    if (TIMER % 300 == 0 && TIMER != 0) {
                        switch (((enemiesRemaining - 1) - enemiesAmount()) % 3) {
                            case 0:
                                addMeteorite();
                                break;
                            case 1:
                                addSpinnerEnemy();
                                break;
                            case 2:
                                addGunnerEnemy();
                                break;
                        }
                    }
                    
                    if ((!(this.areEnemiesLeft()) && enemiesRemaining == 0)  && TIMER != 0) {
                        this.levelUp();
                    }

                    updateBackgroundImages();

                    updateStars();

                    updatePlayer();

                    updateProjectiles();

                    updateEnemies();

                    updateExplosions();

                    break;
                case 3: //Win
                    if (TIMER == 1) this.clearEnemies();
                    if (TIMER >= 50 && (keys[0] || keys[1])) {
                        keys[0] = false;
                        keys[1] = false;
                        TIMER = 0;
                        LEVEL = 0;
                        fading = true;
                    }
                    break;
                
            }

            TIMER++;
            repaint();

            if (player.getLives() == 0) {
                this.clearEnemies();
                player.deleteLife();
                TIMER = 0;
                LEVEL = -1;
                fading = true;
            }
        }   
    }
}
