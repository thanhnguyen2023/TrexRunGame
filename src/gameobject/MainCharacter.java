package gameobject;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import util.Animation;
import util.FrameImage;
import util.Resource;

public class MainCharacter {

	public static final int LAND_POSY = 80;
	public static final float GRAVITY = 0.4f;
	
	private static final int NORMAL_RUN = 0;
	private static final int JUMPING = 1;
	private static final int DOWN_RUN = 2;
	private static final int DEATH = 3;
	private static final int SHOOT = 4;
	
	private float posY;
	private float posX;
	private float speedX;
	private float speedY;
	private Rectangle rectBound;
	
	public int score = 0;
	
	private int state = NORMAL_RUN;
	
	private Animation anim;
	private FrameImage down1,down2,nomal1,nomal2,shoot1,shoot2,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8;
	
	private Animation normalRunAnim;
	private Animation downRunAnim;
	private BufferedImage deathImage;
	private Animation jumpingAnim;
	private Animation shootAnim;

	
	private AudioClip jumpSound;
	private AudioClip deadSound;
	private AudioClip scoreUpSound;
	
	public MainCharacter() {
		this.posX = 50;
		posY = LAND_POSY ;
		rectBound = new Rectangle();
		this.score = score;
		
		try {
			BufferedImage nrun1 = ImageIO.read(new File("data/main-character1.png"));
			nomal1 = new FrameImage("frame1",nrun1);
			BufferedImage nrun2 = ImageIO.read(new File("data/main-character2.png"));
			nomal2 = new FrameImage("frame2",nrun2);
			
			normalRunAnim = new Animation();
			normalRunAnim.add(nomal1, 500*1000000);
			normalRunAnim.add(nomal2, 500*1000000);
			
			BufferedImage shoot1Image = ImageIO.read(new File("data/shoot1_1.png"));
			shoot1 = new FrameImage("frame1",shoot1Image);
			BufferedImage shoot2Image = ImageIO.read(new File("data/shoot2_2.png"));
			shoot2 = new FrameImage("frame2",shoot2Image);
			
			shootAnim = new Animation();
			shootAnim.add(shoot1, 500*1000000);
			shootAnim.add(shoot2, 500*1000000);
//			
			BufferedImage jrun1 = ImageIO.read(new File("data/Jumping1.png"));
			frame1 = new FrameImage("frame1",jrun1);
			BufferedImage jrun2 = ImageIO.read(new File("data/Jumping2.png"));
			frame2 = new FrameImage("frame2",jrun2);
			BufferedImage jrun3 = ImageIO.read(new File("data/Jumping3.png"));
			frame3 = new FrameImage("frame3",jrun3);
			BufferedImage jrun4 = ImageIO.read(new File("data/Jumping4.png"));
			frame4 = new FrameImage("frame4",jrun4);
			BufferedImage jrun5 = ImageIO.read(new File("data/Jumping5.png"));
			frame5 = new FrameImage("frame1",jrun5);
			BufferedImage jrun6 = ImageIO.read(new File("data/Jumping6.png"));
			frame6 = new FrameImage("frame2",jrun6);
			BufferedImage jrun7 = ImageIO.read(new File("data/Jumping7.png"));
			frame7 = new FrameImage("frame3",jrun7);
			BufferedImage jrun8 = ImageIO.read(new File("data/Jumping8.png"));
			frame8 = new FrameImage("frame4",jrun8);
			jumpingAnim = new Animation();
			jumpingAnim.add(frame1, 100*1000000);
			jumpingAnim.add(frame2, 100*1000000);
			jumpingAnim.add(frame3, 100*1000000);
			jumpingAnim.add(frame4, 100*1000000);
			jumpingAnim.add(frame5, 100*1000000);
			jumpingAnim.add(frame6, 100*1000000);
			jumpingAnim.add(frame7, 100*1000000);
			jumpingAnim.add(frame8, 100*1000000);
			
			BufferedImage drun1 = ImageIO.read(new File("data/main-character5.png"));
			down1 = new FrameImage("frame1",drun1);
			BufferedImage drun2 = ImageIO.read(new File("data/main-character6.png"));
			down2 = new FrameImage("frame2",drun2);

			downRunAnim = new Animation();
			downRunAnim.add(down1, 500*1000000);
			downRunAnim.add(down2, 500*1000000);
			deathImage = Resource.getResouceImage("data/main-character4.png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			jumpSound =  Applet.newAudioClip(new URL("file","","data/jump.wav"));
			deadSound =  Applet.newAudioClip(new URL("file","","data/dead.wav"));
			scoreUpSound =  Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		switch(state) {
			case NORMAL_RUN:
				normalRunAnim.draw((int) posX,(int)posY + 25 ,g2);
				break;
			case JUMPING:
				jumpingAnim.draw((int) posX,(int)posY + 25 ,g2);
				break;
			case DOWN_RUN:
				downRunAnim.draw((int) posX,(int)posY + 30,g2);	
				break;
			case DEATH:
				g.drawImage(deathImage, (int) posX, (int) posY, null);
				break;
			case SHOOT:
				shootAnim.draw((int) posX,(int)posY + 25 ,g2);


		}
		Rectangle bound = getBound();
		g.setColor(Color.RED);
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}
	
	public void update() {
		normalRunAnim.Update(System.nanoTime());
		downRunAnim.Update(System.nanoTime());
		jumpingAnim.Update(System.nanoTime());
		shootAnim.Update(System.nanoTime());
		if(posY >= LAND_POSY) {
			posY = LAND_POSY;
			if(state != DOWN_RUN) {
				state = NORMAL_RUN;
			}
		} else {
			speedY += GRAVITY;
			posY += speedY;
		}
		if(score == 12) {
			posX += 1;
		}
		if(posX > 270) {
			posX = 270;
		}
		System.out.println(posX);
		shoot(score % 10 == 0 && score > 0);
	}
	
	public void jump() {
		if(posY >= LAND_POSY) {
			if(jumpSound != null) {
				jumpSound.play();
			}
			speedY = -7.5f;
			posY += speedY;
			state = JUMPING;
		}
	}
	
	public void down(boolean isDown) {
		if(state == JUMPING) {
			return;
		}
		if(isDown) {
			state = DOWN_RUN;
		} else {
			state = NORMAL_RUN;
		}
	}
	public void shoot(boolean isShoot) {
	    if (score % 10 == 0 && score != 0) {
	        state = SHOOT;
	    }
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public Rectangle getBound() {
		rectBound = new Rectangle();
		if(state == DOWN_RUN) {
			rectBound.x = (int) posX + 0;
			rectBound.y = (int) posY + 20;
			rectBound.width = down1.getWidthImage() - 10;
			rectBound.height = down1.getHeightImage();
		} else {
			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY;
			rectBound.width = nomal1.getWidthImage() - 10;
			rectBound.height = nomal1.getHeightImage();
		}
		return rectBound;
	}
	
	public void dead(boolean isDeath) {
		if(isDeath) {
			state = DEATH;
		} else {
			state = NORMAL_RUN;
		}
	}
	
	public void reset() {
		posY = LAND_POSY;
	}
	
	public void playDeadSound() {
		deadSound.play();
	}
	
	public void upScore() {
		score += 2;
		if(score % 100 == 0) {
			scoreUpSound.play();
		}
	}
	
}
