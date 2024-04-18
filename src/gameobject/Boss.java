package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Resource;

public class Boss {
	private BufferedImage imageBoss;
	private float posX,posY;
	public Boss() {
		//imageBoss = Resource.getResouceImage("data/boss.jpg");
	}
	public void draw(Graphics g) {
		//g.drawImage(imageBoss, 600 - imageBoss.getWidth(), 0, null);
	}
}
