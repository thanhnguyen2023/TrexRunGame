package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import util.Resource;

public class ItemManager {
	private List<Items> listBullet;
	private MainCharacter mainCharacter;
	private Cactus enemies;
	//private Random rand;
	
	public ItemManager(MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;		
		//rand = new Random();
		listBullet = new ArrayList<Items>();
		listBullet.add(createBullet());
		listBullet.remove(0);

	

	}
	public Bullet createBullet() {
		return new Bullet();
	}
	public void draw(Graphics g) {
		for(Items it : listBullet) {
			it.draw(g);
		}
	}
	public void update() {
		for(Items it : listBullet) {
			it.update();
		}
	}
}
