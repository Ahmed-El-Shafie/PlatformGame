import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Blocks.Block;
import Blocks.BlockBuilder;
import Blocks.BouncyBlock;
import Blocks.CheckPointBlock;
import Blocks.DeathBlock;
import Blocks.GoalBlock;
import Blocks.PlatformBlock;
import Blocks.PortalBlock;

public class Level extends JPanel implements Observer {
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private double levelWidth;
	private double levelHeight;
	private List<Block> blocks;
	private Map<Integer, CheckPointBlock> checkPoints;
	private Player player;
	private boolean leftKeyPressed = false;
	private boolean rightKeyPressed = false;
	private boolean levelCleared = false;
	private boolean playerDead = false;
	private Color backgroundColor;
	private double playerJumpHeight;
	private double playerStartXco;
	private double playerStartYco;
	private boolean doubleJumpEnabled;
	private boolean jumpDashEnabled;
	private int deathCount;
	private boolean deathCounted;
	
	public Level(double levelWidth, double levelHeight) throws Exception {
		this(levelWidth, levelHeight, new ArrayList<>());
	}
	
	public Level(double levelWidth, double levelHeight, List<BlockBuilder> blockBuilders) throws Exception {
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
		checkPoints = new HashMap<>();
		placeBlocks(blockBuilders);
		playerStartXco = 0;
		playerStartYco = levelHeight - 1;
		this.player = new Player(levelWidth, levelHeight, playerStartXco, playerStartYco, blocks);
		player.addObserver(this);
		backgroundColor = Color.BLUE;
		doubleJumpEnabled = false;
		jumpDashEnabled = false;
		deathCount = 0;
		deathCounted = false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(backgroundColor);
		this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 20));
		g2d.drawString("Deaths: " + deathCount,
				25, (int) (screenSize.getHeight() / 25));
		if (doubleJumpEnabled) {
			g2d.drawString("Double Jump Enabled: \u25B2 \u25B2", (int) (screenSize.getWidth() - 260), (int) (screenSize.getHeight() / 25));
		}
		if (jumpDashEnabled) {
			g2d.drawString("Jump Dash Enabled: (\u25C0/\u25B6) + SPACE (in air)", (int) (screenSize.getWidth() - 400), (int) (screenSize.getHeight() / 12));
		}
		blocks.stream()
			.forEach(block -> block.display(g2d));
		player.drawPlayer(g2d);
		if (player.isReachedGoal()) {
			clearLevel(g2d);
		}
		else if (player.isDead()) {
			loseLevel(g2d);
		}
		else if (rightKeyPressed) {
			player.moveRightIfNotDashing();
		}
		else if (leftKeyPressed) {
			player.moveLeftIfNotDashing();
		}
		g2d.dispose();
	}
	
	private void placeBlocks(List<BlockBuilder> blockBuilders) throws Exception {
		blocks = new ArrayList<>();
		for (BlockBuilder blockBuilder : blockBuilders) {
			List<Block> newBlocks = new BlockFactory().makeBlockFromBuilder(blockBuilder);
			blocks.addAll(newBlocks);
			if (newBlocks.size() == 1 && newBlocks.get(0) instanceof CheckPointBlock) {
				CheckPointBlock checkPointBlock = (CheckPointBlock) newBlocks.get(0);
				checkPoints.put(checkPointBlock.getCheckPointNumber(), checkPointBlock);
			}
		}
	}
	
	public Level setBackgroundColor(Color color) {
		backgroundColor = color;
		return this;
	}
	
	public Level setPlayerJumpHeight(double jumpHeight) {
		player.setJumpHeight(levelHeight, jumpHeight);
		playerJumpHeight = jumpHeight;
		return this;
	}
	
	public Level setPlayerStartXco(double startXco) {
		this.playerStartXco = startXco;
		player.setStartXco(levelWidth, startXco);
		return this;
	}
	
	public Level setPlayerStartYco(double startYco) {
		this.playerStartYco = startYco;
		player.setStartYco(levelHeight, startYco);
		return this;
	}
	
	public Level enableDoubleJump() {
		doubleJumpEnabled = true;
		return this;
	}
	
	public Level enableJumpDash() {
		jumpDashEnabled = true;
		return this;
	}
	
	public void applyKeyPressToMove(int keyCode) {
		if (player.isReachedGoal() || player.isDead()) {
			return;
		}
		switch(keyCode) {
	        case VK_UP:
	        	player.upPress(doubleJumpEnabled);;
	            break;
	        case VK_LEFT:
	        	leftKeyPressed = true;
	            player.moveLeftIfNotDashing();
	            break;
	        case VK_RIGHT:
	        	rightKeyPressed = true;
	        	player.moveRightIfNotDashing();
	            break;
	        case VK_SPACE:
	        	if (jumpDashEnabled) {
	        		if (leftKeyPressed) {
	        			player.jumpDashLeft();
	        		} else if (rightKeyPressed) {
	        			player.jumpDashRight();
	        		}
	        	}
	        	break;
	        default:
	        	break;
		}
	}
	
	public void releaseDirectionKey(int keyCode) {
		if (keyCode == VK_RIGHT) {
			rightKeyPressed = false;
			player.stop();
		}
		else if (keyCode == VK_LEFT) {
			leftKeyPressed = false;
			player.stop();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
	
	public boolean isLevelCleared() {
		return levelCleared;
	}
	
	public boolean isPlayerDead() {
		return playerDead;
	}
	
	public void clearLevel(Graphics2D g2d) {
		levelCleared = true;
		g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 20));
		g2d.drawString("Press any key for next level", (int) (screenSize.getWidth() / 2 - 100), (int) (screenSize.getHeight() / 20));
	}
	
	public void loseLevel(Graphics2D g2d) {
		playerDead = true;
		if (!deathCounted) {
			deathCount++;
			deathCounted = true;
		}
		g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 20));
		g2d.drawString("YOU ARE DEAD!",
				(int) (screenSize.getWidth() / 2 - 100), (int) (screenSize.getHeight() / 20));
		g2d.drawString("Press 'Esc' to Quit'",
				(int) (screenSize.getWidth() / 2 - 100), (int) (screenSize.getHeight() / 13));
		g2d.drawString("Press any other key to Restart",
				(int) (screenSize.getWidth() / 2 - 100), (int) (screenSize.getHeight() / 9));
	}
	
	public void restartLevelFromLastCheckPoint() {
		int checkPointReached = player.getCheckPointReached();
		double newXco = 0;
		double newYco = 0;
		if (checkPointReached > 0) {
			CheckPointBlock checkPointBlock = checkPoints.get(player.getCheckPointReached());
			newXco = checkPointBlock.getXco();
			newYco = checkPointBlock.getYco() - player.getSize() - 5;
		}
		this.player = new Player(levelWidth, levelHeight, playerStartXco, playerStartYco, blocks);
		if (playerJumpHeight != 0) {
			player.setJumpHeight(levelHeight, playerJumpHeight);
		}
		if (checkPointReached > 0) {
			player.setXco(newXco);
			player.setYco(newYco);
			player.setCheckPointReached(checkPointReached);
		}
		playerDead = false;
		deathCounted = false;
		player.addObserver(this);
		repaint();
	}
	
	private class BlockFactory {
		public List<Block> makeBlockFromBuilder(BlockBuilder builder) throws Exception {
			if (builder.isGoal()) {
				return Collections.singletonList(new GoalBlock(levelWidth, levelHeight, builder));
			}
			else if (builder.isDeadly()) {
				return Collections.singletonList(new DeathBlock(levelWidth, levelHeight, builder));
			}
			else if (builder.isBouncy()) {
				return Collections.singletonList(new BouncyBlock(levelWidth, levelHeight, builder));
			}
			else if (builder.getOutPortalBlock() != null) {
				PortalBlock portalBlock1 = new PortalBlock(levelWidth, levelHeight, builder);
				PortalBlock portalBlock2 = new PortalBlock(levelWidth, levelHeight, builder.getOutPortalBlock());
				portalBlock1.setOutPortalBlock(portalBlock2);
				portalBlock2.setOutPortalBlock(portalBlock1);
				return Arrays.asList(portalBlock1, portalBlock2);
			}
			else if (builder.getCheckPointNumber() > 0) {
				return Collections.singletonList(new CheckPointBlock(levelWidth, levelHeight, builder, builder.getCheckPointNumber()));
			}
			else {
				return Collections.singletonList(new PlatformBlock(levelWidth, levelHeight, builder));
			}
		}
	}
}
