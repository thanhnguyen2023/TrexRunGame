package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Resource;

public class Ending {
	private MainCharacter mainCharacter;
	private BufferedImage end1;
	private int posX,posY;
	private boolean isEnd;
	
	public Ending() {
		end1 = Resource.getResouceImage("data/end3.png");
		posX = 600 - end1.getWidth();
		posY = 175 - end1.getHeight() - 40;
		mainCharacter = new MainCharacter();
	}
	public void update() {
		
	}
	public void draw(Graphics g) {
		g.drawImage(end1, posX, posY, null);
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void isEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	

}
