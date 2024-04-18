package userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameobject.Boss;
import gameobject.Bullet;
import gameobject.Clouds;
import gameobject.Ending;
import gameobject.EnemiesManager;
import gameobject.ItemManager;
import gameobject.Land;
import gameobject.MainCharacter;
import util.Resource;
public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_GAME_STATE = 0;//trạng thái khi bắt đầu game
	private static final int GAME_PLAYING_STATE = 1;//khi chơi game
	private static final int GAME_OVER_STATE = 2;//khi kết thúc trò chơi
	private static final int GAME_END_STATE = 3;//khí dạt được ending game


	
	private Land land;//sàn
	private Bullet bullet;//đạn
	private Clouds clouds;//mây
	private Ending end;//nhân vật ending
	//private Boss boss;
	
	private MainCharacter mainCharacter;//nhân vạt chính
	private EnemiesManager enemiesManager;// các kẻ thù
	private ItemManager itemManager;//các item
	
	private Thread thread;//chạy đa luồng
	private boolean isKeyPressed;//

	private int gameState = START_GAME_STATE;//cho trạng thái ban đầu là State

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	public GameScreen() {
		mainCharacter = new MainCharacter();
		//khởi tạo đạn
		bullet = new Bullet();
		bullet.setPosX(-120);//cho vị trí ban đầu của bullet ngoài màn hình
		bullet.setActive(false);//cho việc xuất hiện đạn là false
		//khởi tạo sàn
		land = new Land(GameWindow.SCREEN_WIDTH, mainCharacter);
		
		end = new Ending();
		mainCharacter.setSpeedX(7);
		replayButtonImage = Resource.getResouceImage("data/replay_button.png");
		gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
		enemiesManager = new EnemiesManager(mainCharacter,bullet);
		itemManager = new ItemManager(mainCharacter);
		clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter);
		//boss = new Boss();
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if (gameState == GAME_PLAYING_STATE ) {
			clouds.update();
			land.update();
			mainCharacter.update();
			enemiesManager.update();
			//chỉ update bullet khi trạng thái true
			if (bullet.isActive()) {
			    bullet.update();
			}
			itemManager.update();
			if (enemiesManager.isCollision()) {
				mainCharacter.playDeadSound();
				gameState = GAME_OVER_STATE;
				mainCharacter.dead(true);
				mainCharacter.setScore(0);
			}
			if (enemiesManager.isCollisionBullet()) {
			    enemiesManager.removeCollidedEnemy();
			    mainCharacter.upScore();
			    bullet.setActive(false); // Đặt trạng thái của viên đạn là false khi va chạm
			}
		}
		if(mainCharacter.getScore() == 12) {
			gameState = GAME_END_STATE;
			end.update();
			clouds.update();
			if(mainCharacter.getPosX() < 270) {
				land.update();
				mainCharacter.update();
			}
		}
		
	}

	public void paint(Graphics g) {
		g.setColor(Color.decode("#f7f7f7"));
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {
		case START_GAME_STATE:
			mainCharacter.draw(g);
			break;
		case GAME_PLAYING_STATE:
		case GAME_OVER_STATE:
			clouds.draw(g);
			land.draw(g);
			enemiesManager.draw(g);
			if (bullet.isActive()) {
			    bullet.draw(g);
			}
			mainCharacter.draw(g);
			//boss.draw(g);
			g.setColor(Color.BLACK);
			g.drawString("HI " + mainCharacter.score, 500, 20);
			if (gameState == GAME_OVER_STATE) {
				g.drawImage(gameOverButtonImage, 200, 30, null);
				g.drawImage(replayButtonImage, 283, 50, null);
				
			}
			break;
		case GAME_END_STATE:
			if(mainCharacter.getScore() == 12) {
				gameState = GAME_END_STATE;
				clouds.draw(g);
				land.draw(g);
				mainCharacter.draw(g);
				end.draw(g);
			}
			break;
		}
	}

	@Override
	public void run() {

		int fps = 100;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		long endProcessGame;
		long lag = 0;

		while (true) {
			gameUpdate();
			repaint();
			endProcessGame = System.nanoTime();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);
			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	    if (!isKeyPressed) {
	        isKeyPressed = true;
	        switch (gameState) {
	            case START_GAME_STATE:
	                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	                    gameState = GAME_PLAYING_STATE;
	                }
	                break;
	            case GAME_PLAYING_STATE:     
	                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	                    mainCharacter.jump();
	                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	                    mainCharacter.down(true);
	                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                    if(mainCharacter.getScore() > 0 && mainCharacter.getScore() % 10 == 0 ) {
	                        bullet.setActive(true);
	                        bullet.setPosX(50);
	                    }
	                }
	                break;
	            case GAME_OVER_STATE:
	                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	                    gameState = GAME_PLAYING_STATE;
	                    resetGame();
	                }
	                break;
	            case GAME_END_STATE:
	                if (e.getKeyCode() == KeyEvent.VK_N) {
	                    gameState = START_GAME_STATE;
	                    resetGame();
	                }
	                break;
	        }
	    }
	}


	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		if (gameState == GAME_PLAYING_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				mainCharacter.down(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void resetGame() {
		enemiesManager.reset();
		mainCharacter.dead(false);
		mainCharacter.reset();
	}

}
