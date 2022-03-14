import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import Blocks.BlockBuilder;

public class Game extends JFrame implements KeyListener {
	private int currentLevelNumber;
	private List<Level> levels;
	
	public Game() throws Exception {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Level level1 = new Level(22, 10, Arrays.asList(
				new BlockBuilder(10, 9, 5, 1),
				new BlockBuilder(13, 8, 4, 1),
				new BlockBuilder(11, 7, 3, 1),
				new BlockBuilder(5, 5, 3, 1),
				new BlockBuilder(11, 3, 4, 1),
				new BlockBuilder(12, 4, 1, 3),
				new BlockBuilder(18, 5, 2, 1),
				new BlockBuilder(3, 9, 3, 1),
				new BlockBuilder(0, 7, 2, 1),
				new BlockBuilder(21, 9, 1, 1).goal()));
		
		List<BlockBuilder> level2Blocks = new ArrayList<>();
		for (int i = 2; i < 14; i++) {
			level2Blocks.add(new BlockBuilder(i - 0.5, 19 - i, 1, 2));
		}
		level2Blocks.add(new BlockBuilder(7, 17.63, 1, 2.37).goal());
		Level level2 = new Level(15, 20, level2Blocks).setBackgroundColor(Color.PINK);
		
		Level level3 = new Level(20, 10, Arrays.asList(
				new BlockBuilder(19, 9, 1, 1).goal(),
				new BlockBuilder(10, 4, 1, 6),
				new BlockBuilder(8, 9, 1, 1).bouncy()))
				.setBackgroundColor(Color.LIGHT_GRAY);
		
		Level level4 = new Level(20, 10, Arrays.asList(
				new BlockBuilder(8, 9, 1, 1).portal(12, 9, 1, 1),
				new BlockBuilder(10, 4, 1, 6),
				new BlockBuilder(19, 9, 1, 1).goal()));
		
		List<BlockBuilder> level5Blocks = new ArrayList<>();
		for (int i = 6; i < 16; i++) {
			level5Blocks.add(new BlockBuilder(i, 25 - i, 1, i - 5));
		}
		for (int i = 16; i < 24; i++) {
			level5Blocks.add(new BlockBuilder(i, 19, 1, 1).deadly());
		}
		level5Blocks.add(new BlockBuilder(29, 19, 1, 1).goal());
		Level level5 = new Level(30, 20, level5Blocks).setBackgroundColor(Color.WHITE);
		
		Level level6ByLayla = new Level(40, 19, Arrays.asList(
				new BlockBuilder(1, 17, 2, 0.85),
				new BlockBuilder(4, 14, 2, 0.85),
				new BlockBuilder(7, 11, 2, 0.85),
				new BlockBuilder(10, 8, 2, 0.85),
				new BlockBuilder(13, 5, 2, 0.85),
				new BlockBuilder(15, 5, 2, 0.85),
				new BlockBuilder(20, 12, 1, 4).deadly(),
				new BlockBuilder(21, 16, 2, 0.5).bouncy(14),
				new BlockBuilder(23, 12, 4, 1).deadly(),
				new BlockBuilder(27, 5, 2, 0.85),
				new BlockBuilder(29, 5, 2, 0.85).bouncy(5),
				new BlockBuilder(38, 5, 1, 0.85).goal()))
				.setPlayerJumpHeight(3);
		
		Level level7 = new Level(35, 20, Arrays.asList(
				new BlockBuilder(3, 19, 32, 1).deadly(),
				new BlockBuilder(4, 18, 1, 1),
				new BlockBuilder(7, 15, 10, 1),
				new BlockBuilder(12, 11, 12, 2).deadly(),
				new BlockBuilder(24, 10, 7, 1),
				new BlockBuilder(31, 10, 1, 1).checkPoint(1),
				new BlockBuilder(25, 15, 6.5, 1),
				new BlockBuilder(10, 9, 2, 2),
				new BlockBuilder(0, 5, 1, 0.5).bouncy(),
				new BlockBuilder(9, 3, 5, 1),
				new BlockBuilder(27, 3, 5, 1).goal())).setBackgroundColor(Color.MAGENTA).enableDoubleJump();
		
		Level level8 = new Level(40, 50, Arrays.asList(
				new BlockBuilder(5, 9, 30, 2),
				new BlockBuilder(10, 1, 2, 8).deadly(),
				new BlockBuilder(30, 2, 2, 7).deadly(),
				new BlockBuilder(33, 17, 7, 2).deadly(),
				new BlockBuilder(0, 17, 6, 2).deadly(),
				new BlockBuilder(6, 19, 13.5, 2),
				new BlockBuilder(18.5, 15.5, 1, 3.5).deadly(),
				new BlockBuilder(21.5, 19, 12, 2),
				new BlockBuilder(21.5, 15.5, 1, 3.5).deadly(),
				new BlockBuilder(20, 27, 1, 12).checkPoint(1),
				new BlockBuilder(21, 29, 5, 2).deadly(),
				new BlockBuilder(26, 31, 2, 1),
				new BlockBuilder(28, 32, 10.75, 2).deadly(),
				new BlockBuilder(21, 38, 8, 2),
				new BlockBuilder(34, 38, 2, 2),
				new BlockBuilder(38, 38, 2, 2),
				new BlockBuilder(26, 45.5, 2, 2.5),
				new BlockBuilder(21.5, 36, 1, 2).portal(27, 44, 1, 1.5),
				new BlockBuilder(36, 45.5, 2, 2.5),
				new BlockBuilder(23, 48, 17, 2).deadly(),
				new BlockBuilder(16, 39, 4, 2),
				new BlockBuilder(6, 37, 10, 2).deadly(),
				new BlockBuilder(6, 35, 2, 2),
				new BlockBuilder(4.5, 28, 2, 7).deadly(),
				new BlockBuilder(4.5, 27, 1, 1),
				new BlockBuilder(0, 48, 1, 2).checkPoint(2),
				new BlockBuilder(2, 24, 2, 2).deadly(),
				new BlockBuilder(5, 47, 1, 3).deadly(),
				new BlockBuilder(5, 40, 1, 3),
				new BlockBuilder(9, 48, 1, 2).checkPoint(3),
				new BlockBuilder(10, 43.5, 1, 6.5).deadly(),
				new BlockBuilder(14, 47, 1, 3).deadly(),
				new BlockBuilder(17, 47, 1.5, 3).deadly(),
				new BlockBuilder(20, 49, 1, 1).goal()))
				.setPlayerJumpHeight(5)
				.setPlayerStartXco(20)
				.setPlayerStartYco(0)
				.enableDoubleJump()
				.setBackgroundColor(Color.YELLOW);
				
		Level level9 = new Level(30, 40, Arrays.asList(
				new BlockBuilder(12, 12, 2, 28),
				new BlockBuilder(10, 39, 2, 1).bouncy(),
				new BlockBuilder(0, 26, 2, 1),
				new BlockBuilder(10, 21, 2, 1).bouncy(),
				new BlockBuilder(12, 38, 18, 2).deadly(),
				new BlockBuilder(27, 36, 2, 2).goal()))
				.enableJumpDash()
				.setBackgroundColor(new Color(50, 168, 82));
		
		List<BlockBuilder> blockBuilders = new ArrayList<>();
		int goalBlockNumber = new Random().nextInt(3);
		int[] xPositions = {19, 24, 29};
		for (int i = 0; i < 3; i++) {
			if (i == goalBlockNumber) {
				blockBuilders.add(new BlockBuilder(xPositions[i], 7, 2, 2).goal());
			} else {
				blockBuilders.add(new BlockBuilder(xPositions[i], 7, 2, 2).deadly().color(Color.GREEN));
			}
		}
		blockBuilders.addAll(Arrays.asList(
			new BlockBuilder(0, 31, 40, 2),
			new BlockBuilder(10, 38, 1, 2).deadly(),
			new BlockBuilder(20, 37, 1, 3).deadly(),
			new BlockBuilder(30, 39, 10, 1).deadly(),
			new BlockBuilder(36, 37, 1, 1).portal(39, 30, 1, 1),
			new BlockBuilder(15, 0, 1, 16),
			new BlockBuilder(5, 30, 30, 1).deadly(),
			new BlockBuilder(34, 25, 2, 1).bouncy(2),
			new BlockBuilder(24, 24, 2, 1).bouncy(2),
			new BlockBuilder(16, 23, 2, 1).bouncy(2),
			new BlockBuilder(7, 22, 2, 1).bouncy(2),
			new BlockBuilder(2, 30, 1, 1).portal(0, 2, 1, 1),
			new BlockBuilder(14, 15, 1, 1).bouncy(8),
			new BlockBuilder(20, 28, 2, 2).portal(39, 14, 1, 1),
			new BlockBuilder(39, 6, 1, 1),
			new BlockBuilder(36.5, 0.5, 1, 15.5).deadly(),
			new BlockBuilder(33.5, 0.5, 1, 15.5).deadly(),
			new BlockBuilder(35, 14, 1, 1).bouncy(8),
			new BlockBuilder(37.5, 11, 1, 4).deadly(),
			new BlockBuilder(16, 0, 1, 16),
			new BlockBuilder(16, 15, 25, 1)));
		
		Level level10 = new Level(40, 40, blockBuilders)
				.enableDoubleJump()
				.setBackgroundColor(new Color(102, 153, 204));
		
		levels = Arrays.asList(level1, level2, level3, level4, level5, level6ByLayla, level7, level8, level9, level10);
		this.setVisible(true);
		addKeyListener(this);
		currentLevelNumber = 0;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Level currentLevel = levels.get(currentLevelNumber);
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (keyCode == KeyEvent.VK_R) {
			currentLevel.restartLevelFromLastCheckPoint();
		}
	    currentLevel.applyKeyPressToMove(e.getKeyCode());
	    if (currentLevel.isLevelCleared()) {
	    	goToNextLevel();
	    } else if (currentLevel.isPlayerDead()) {
	    	currentLevel.restartLevelFromLastCheckPoint();
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		levels.get(currentLevelNumber).releaseDirectionKey(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void start() {
		this.add(levels.get(0));
	}
	
	public void goToNextLevel() {
		this.remove(levels.get(currentLevelNumber));
		currentLevelNumber += 1;
		if (currentLevelNumber == levels.size()) {
			System.exit(0);
		}
		this.add(levels.get(currentLevelNumber));
		revalidate();
	}
}
