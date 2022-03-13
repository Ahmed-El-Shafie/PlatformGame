import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.List;
import java.util.Observable;

import Blocks.Block;
import Blocks.BouncyBlock;
import Blocks.CheckPointBlock;
import Blocks.DeathBlock;
import Blocks.GoalBlock;
import Blocks.PortalBlock;

import static java.util.Objects.isNull;

public class Player extends Observable {
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private double xco;
	private double yco;
	private double size;
	private boolean airborne;
	private boolean risingOrFalling;
	private double jumpStartPos;
	private double jumpHeight;
	private double normalJumpHeight;
	private CollisionDetector collisionDetector;
	private double speed;
	private boolean reachedGoal;
	private boolean dead;
	private boolean doubleJumped;
	private boolean dashingLeft;
	private boolean dashingRight;
	private int checkPointReached;
	private Color color;

	public Player(double levelWidth, double levelHeight, double xco, double yco, List<Block> blocks) {
		this.size = screenSize.getWidth() / levelWidth;
		setStartXco(levelWidth, xco);
		setStartYco(levelHeight, yco);
		this.airborne = true;
		this.jumpStartPos = screenSize.getHeight();
		this.risingOrFalling = false;
		this.normalJumpHeight = size * 3;
		this.jumpHeight = jumpStartPos - getBottomOfPlayer();
		this.collisionDetector = new CollisionDetector(blocks);
		this.speed = 0;
		this.reachedGoal = false;
		this.dead = false;
		this.doubleJumped = false;
		this.dashingLeft = false;
		this.dashingRight = false;
		this.checkPointReached = 0;
		this.color = Color.BLACK;
	}

	public void setJumpHeight(double levelHeight, double jumpHeight) {
		this.normalJumpHeight = jumpHeight * screenSize.getHeight() / levelHeight;
	}
	
	public void setStartXco(double levelWidth, double startXco) {
		this.xco = startXco / levelWidth * screenSize.getWidth();
	}
	
	public void setStartYco(double levelHeight, double startYco) {
		this.yco = startYco / levelHeight * screenSize.getHeight() - (size - screenSize.getHeight() / levelHeight);
		this.jumpHeight = jumpStartPos - getBottomOfPlayer();
	}
	
	public void setXco(double xco) {
		this.xco = xco;
	}
	
	public void setYco(double yco) {
		this.yco = yco;
	}
	
	public void setCheckPointReached(int checkPointReached) {
		this.checkPointReached = checkPointReached;
	}
	
	public void drawPlayer(Graphics2D g2d) {
		if (airborne) {
			airborneMove();
			if (dashingLeft) {
				dashingLeft();
			} else if (dashingRight) {
				dashingRight();
			}
		}
		g2d.setColor(color);
		g2d.fillRoundRect((int) xco, (int) yco, (int) size, (int) size, 5, 5);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRoundRect((int) xco, (int) yco, (int) size, (int) size, 5, 5);
	}

	private void accelerate() {
		if (speed == 0) {
			speed = size / 128;
		}
		else {
			speed = Math.min(speed * 1.03, size / 21);
		}
	}
	
	public void stop() {
		speed = 0;
	}
	
	public void moveLeftIfNotDashing() {
		if (!dashingLeft) {
			moveLeft();
		}
	}
	
	private void moveLeft() {
		boolean onBlock = false;
		if (!isNull(collisionDetector.detectDownCollidingBlock(5))) {
			onBlock = true;
		}
		if (xco >= speed &&
				!collisionDetector.detectLeftBlockCollision()) {
			if (!dashingLeft) {
				accelerate();
			}
			xco -= speed;
			if (onBlock && isNull(collisionDetector.detectDownCollidingBlock(5))) {
				moveOffBlock();
			}
		}
		modelChanged();
	}
	
	public void moveRightIfNotDashing() {
		if (!dashingRight) {
			moveRight();
		}
	}
	
	private void moveRight() {
		boolean onBlock = false;
		Block blockOn = collisionDetector.detectDownCollidingBlock(5);
		if (!isNull(blockOn)) {
			if (!(blockOn instanceof PortalBlock)) {
				handleSpecialBlockCollisions(blockOn);
			}
			onBlock = true;
		}
		if (xco <= screenSize.getWidth() - Math.floor(speed + size) &&
				!collisionDetector.detectRightBlockCollision()) {
			if (!dashingRight) {
				accelerate();
			}
			xco += speed;
			if (onBlock && isNull(collisionDetector.detectDownCollidingBlock(5))) {
				moveOffBlock();
			}
		}
		modelChanged();
	}

	private void moveOffBlock() {
		airborne = true;
		jumpStartPos = screenSize.getHeight();
		jumpHeight = jumpStartPos - getBottomOfPlayer();
		risingOrFalling = false;
	}
	
	public void upPress(boolean doubleJumpEnabled) {
		if (airborne && doubleJumpEnabled) {
			doubleJump();
		} else {
			jump();
		}
	}
	
	public void jump() {
		if (!airborne) {
			risingOrFalling = true;
			jumpStartPos = getBottomOfPlayer();
			yco -= getHeightDecrement();
			airborne = true;
			modelChanged();
		}
	}
	
	public void doubleJump() {
		if (!doubleJumped) {
			risingOrFalling = true;
			jumpStartPos = getBottomOfPlayer();
			jumpHeight = normalJumpHeight;
			yco -= getHeightDecrement();
			doubleJumped = true;
			modelChanged();
		}
	}
	
	private void dashingLeft() {
		moveLeft();
		speed *= 0.99;
		if (speed <= size / 20) {
			speed = 0;
			dashingLeft = false;
		}
	}
	
	public void jumpDashLeft() {
		if (airborne && !dashingLeft) {
			speed += size / 15;
			dashingLeft = true;
		}
	}
	
	private void dashingRight() {
		moveRight();
		speed *= 0.99;
		if (speed <= size / 20) {
			speed = 0;
			dashingRight = false;
		}
	}
	
	public void jumpDashRight() {
		if (airborne && !dashingRight) {
			speed += size / 15;
			dashingRight = true;
		}
	}

	private void handleSpecialBlockCollisions(Block block) {
		if (block instanceof GoalBlock) {
			reachedGoal = true;
		}
		else if (block instanceof DeathBlock) {
			dead = true;
			color = Color.RED;
		}
		else if (block instanceof BouncyBlock) {
			jumpHeight = size * ((BouncyBlock) block).getBouncyMultiplier();
			jump();
		}
		else if (block instanceof PortalBlock) {
			PortalBlock outPortalBlock = ((PortalBlock) block).getOutPortalBlock();
			xco = outPortalBlock.getXco();
			yco = outPortalBlock.getYco() - size - 5;
		}
		else if (block instanceof CheckPointBlock) {
			CheckPointBlock checkPointBlock = (CheckPointBlock) block;
			if (checkPointReached < checkPointBlock.getCheckPointNumber()) {
				checkPointReached = checkPointBlock.getCheckPointNumber();
			}
			checkPointBlock.passed();
		}
	}
	
	private void airborneMove() {
		if (Math.abs(jumpStartPos - getBottomOfPlayer()) < 1e-4) {
			Block collidedBlock = collisionDetector.detectDownCollidingBlock(jumpStartPos - getBottomOfPlayer() - 5);
			if (jumpStartPos == screenSize.getHeight() || !isNull(collidedBlock)) {
				airborne = false;
				doubleJumped = false;
				dashingLeft = false;
				dashingRight = false;
				jumpHeight = normalJumpHeight;
				handleSpecialBlockCollisions(collidedBlock);
			}
			else {
				jumpStartPos = screenSize.getHeight();
				jumpHeight = screenSize.getHeight() - getBottomOfPlayer() + jumpHeight;
			}
		}
		else if (getBottomOfPlayer() <= jumpStartPos) {
			yco -= getHeightDecrement();
		}
		modelChanged();
	}

	private double getHeightDecrement() {
		double currentHeight = jumpStartPos - getBottomOfPlayer();
		if (Math.abs(currentHeight - jumpHeight) < 1e-4) {
			risingOrFalling = false;
			return -1;
		}
		double heightDecrement = Math.max((jumpHeight - currentHeight) / 20, 0.5); // 0.5 is so that the player doesn't hang in air for too long
		if (!risingOrFalling) {
			heightDecrement = - Math.min(Math.min(heightDecrement, currentHeight), size / 10); // size / 10 is to limit dropping speed
			Block collidedBlock = collisionDetector.detectDownCollidingBlock(heightDecrement);
			if (!isNull(collidedBlock)) {
				jumpStartPos = collidedBlock.getYco() - 5;
				return getBottomOfPlayer() - jumpStartPos;
			}
		} else {
			heightDecrement = Math.min(heightDecrement, jumpHeight - currentHeight);
			Block collidedBlock = collisionDetector.detectUpCollidingBlock(heightDecrement);
			if (!isNull(collidedBlock)) {
				double blockBottomYco = collidedBlock.getYco() + collidedBlock.getHeight();
				jumpHeight = jumpStartPos - blockBottomYco - size;
				return yco - blockBottomYco;
			}
		}	
		return heightDecrement;
	}

	private double getBottomOfPlayer() {
		return yco + size;
	}
	
	public boolean isAirborne() {
		return airborne;
	}

	public double getXco() {
		return xco;
	}

	public double getYco() {
		return yco;
	}

	public double getSize() {
		return size;
	}

	public double getNormalJumpHeight() {
		return normalJumpHeight;
	}
	
	public boolean isReachedGoal() {
		return reachedGoal;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public int getCheckPointReached() {
		return checkPointReached;
	}
	
	private void modelChanged() {
		this.setChanged();
		this.notifyObservers();
	}
	
	private class CollisionDetector {
		private List<Block> blocks;
		
		public CollisionDetector(List<Block> blocks) {
			this.blocks = blocks;
		}
		
		public boolean detectRightBlockCollision() {
			return blocks.stream()
					.anyMatch(this::detectRightBlockCollision);
		}
		
		private boolean detectRightBlockCollision(Block block) {
			return detectHeightPossibleCollision(block) &&
					getXco() + getSize() <= block.getXco() &&
					getXco() + getSize() >= block.getXco() - 5 - speed;
		}
		
		public boolean detectLeftBlockCollision() {
			return blocks.stream()
					.anyMatch(this::detectLeftBlockCollision);
		}
		
		private boolean detectLeftBlockCollision(Block block) {
			return detectHeightPossibleCollision(block) &&
					getXco() >= block.getXco() + block.getWidth() &&
					getXco() <= block.getXco() + block.getWidth() + 5 + speed;
		}
		
		public Block detectUpCollidingBlock(double heightDecrement) {
			return blocks.stream()
					.filter(block -> !isNull(detectUpCollidingBlock(block, heightDecrement)))
					.findFirst()
					.orElse(null);
		}
		
		private Block detectUpCollidingBlock(Block block, double heightDecrement) {
			if (detectPositionPossibleCollision(block) &&
					getYco() > block.getYco() + block.getHeight() &&
					getYco() < block.getYco() + block.getHeight() + heightDecrement + 5) {
				return block;
			}
			return null;
		}
		
		public Block detectDownCollidingBlock(double heightDecrement) {
			return blocks.stream()
					.filter(block -> !isNull(detectDownCollidingBlock(block, Math.abs(heightDecrement))))
					.findFirst()
					.orElse(null);
		}
		
		private Block detectDownCollidingBlock(Block block, double heightDecrement) {
			if (detectPositionPossibleCollision(block) &&
					getBottomOfPlayer() < block.getYco() &&
					getBottomOfPlayer() > block.getYco() - heightDecrement - 5) {
				return block;
			}
			return null;
		}
		
		private boolean detectHeightPossibleCollision(Block block) {
			return getYco() < block.getYco() + block.getHeight() &&
					getBottomOfPlayer() > block.getYco();
		}
		
		private boolean detectPositionPossibleCollision(Block block) {
			return getXco() < block.getXco() + block.getWidth() &&
					getXco() + getSize() > block.getXco();
		}
	}
}
