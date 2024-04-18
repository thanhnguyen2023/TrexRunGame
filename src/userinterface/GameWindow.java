package userinterface;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	
	public static final int SCREEN_WIDTH = 600;
	private GameScreen gameScreen;
	
	public GameWindow() {
		super("Java T-Rex game");//đặt tên cửa sổ
		setSize(SCREEN_WIDTH, 175);//đặt kích thước cửa sổ
		setLocation(400, 200);//vị trris cửa sổ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//tạo lối thoát chhuongw trình
		setResizable(false);
		
		gameScreen = new GameScreen();
		addKeyListener(gameScreen);
		add(gameScreen);
	}
	
	public void startGame() {
		setVisible(true);//hiện cửa sổ lên màn hình
		gameScreen.startGame();
	}
	
	public static void main(String args[]) {
		(new GameWindow()).startGame();
	}
	
}
