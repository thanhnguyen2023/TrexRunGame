package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import util.Resource;

public class EnemiesManager {
	
	private BufferedImage cactus1;
	private BufferedImage cactus2;
	private Random rand;
	private List<Enemy> enemies;
	private MainCharacter mainCharacter;
	private Bullet bullet;
	private ItemManager item;
	
	public EnemiesManager(MainCharacter mainCharacter,Bullet bullet) {
		rand = new Random();
		cactus1 = Resource.getResouceImage("data/cactus1.png");
		cactus2 = Resource.getResouceImage("data/cactus2.png");
		enemies = new ArrayList<Enemy>();
		this.mainCharacter = mainCharacter;
		this.bullet = bullet;
		enemies.add(createEnemy());
	}
	
	public void update() {
		for(Enemy e : enemies) {
			e.update();
		}
		Enemy enemy = enemies.get(0);
		if(enemy.isOutOfScreen()) {
			mainCharacter.upScore();
			enemies.clear();
			enemies.add(createEnemy());
		}
		
	}
	
	public void draw(Graphics g) {
		for(Enemy e : enemies) {
			e.draw(g);
		}
	}
	
	private Enemy createEnemy() {
		// if (enemyType = getRandom)
		int type = rand.nextInt(2);
		if(type == 0) {
			return new Cactus(mainCharacter, 800, cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1);
		} else {
			return new Cactus(mainCharacter, 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
		}
	}
	//Va chạm nhân vật và Enemy
	public boolean isCollision() {
		for(Enemy e : enemies) {
			if (mainCharacter.getBound().intersects(e.getBound())) {
				return true;
			}
		}

		return false;
	}
	//Va chạm đạn và Enemy
	public boolean isCollisionBullet() {
		for(Enemy e : enemies) {
			if (bullet.getBound().intersects(e.getBound())) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		enemies.clear();
		enemies.add(createEnemy());
	}
	//xáo nhân vật khi va cham
	public void removeCollidedEnemy() {
	    for (int i = 0; i < enemies.size(); i++) {
	        Enemy enemy = enemies.get(i);
	        if (bullet.getBound().intersects(enemy.getBound())) {
	            enemies.remove(i); // Xóa enemy tại vị trí i
	            enemies.add(createEnemy()); // Thêm một enemy mới
	            bullet.setPosX(-200);//Khi va chạm sẽ xét luôn bullet ra ngoài màn hình 
	            break; // Dừng vòng lặp sau khi xóa một enemy
	        }
	    }
	}



	
}
