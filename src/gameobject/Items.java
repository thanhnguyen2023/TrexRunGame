package gameobject;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Items {
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract Rectangle getBound();
}
