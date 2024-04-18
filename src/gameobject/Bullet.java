package gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import util.Resource;


public class Bullet extends Items{
	private BufferedImage bullet;
	private Rectangle rectBound;
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	
	private float speedX;
	private boolean active;//xét trạng thái của đạn
	private MainCharacter mainCharacter;
	public Bullet() { 
		posY = 135;
		this.speedX = 4.f;
		this.posX = posX;
		rectBound = new Rectangle();
		mainCharacter = new MainCharacter();
		bullet = Resource.getResouceImage("data/Explosion2.png");
        width = bullet.getWidth();
        height = bullet.getHeight();

	}
	public BufferedImage getBulletImage() {
		return bullet;
	}
	public void setBulletImage(BufferedImage bulletImage) {
		this.bullet = bullet;
	}
	public void draw(Graphics g) {
		g.drawImage(bullet, posX, 125 - bullet.getHeight(), null);
		g.setColor(Color.red);
		Rectangle bound = getBound();
		g.drawRect(bound.x, bound.y, bound.width, bound.height);	
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}
	public void update() {
		posX += speedX;
		System.out.println(posX);  
	}
	public float getSpeedX() {
		return speedX;
	}
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public Rectangle getBound() {
		rectBound = new Rectangle();
		rectBound.x = (int) posX + (bullet.getWidth() - width)/2;
		rectBound.y = 125 - bullet.getHeight() + (bullet.getHeight() - height)/2;
		rectBound.width = width;
		rectBound.height = height;
		return rectBound;
	}
	//xét trạng thái nếu mà bắn thì trả về true
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
