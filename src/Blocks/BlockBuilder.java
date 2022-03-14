package Blocks;
import java.awt.Color;

public class BlockBuilder {
	private double xco;
	private double yco;
	private double width;
	private double height;
	private Color color;
	private boolean isGoal;
	private boolean isDeadly;
	private boolean isBouncy;
	private double bouncyMultiplier;
	private int checkPointNumber;
	private BlockBuilder outPortalBlock;
	
	public BlockBuilder(double xco, double yco, double width, double height) {
		this.xco = xco;
		this.yco = yco;
		this.width = width;
		this.height = height;
		this.color = null;
		isGoal = false;
		isDeadly = false;
		isBouncy = false;
		checkPointNumber = 0;
		bouncyMultiplier = 6;
	}
	
	public BlockBuilder goal() {
		isGoal = true;
		return this;
	}
	
	public BlockBuilder deadly() {
		isDeadly = true;
		return this;
	}
	
	public BlockBuilder bouncy() {
		isBouncy = true;
		return this;
	}
	
	public BlockBuilder color(Color color) {
		this.color = color;
		return this;
	}
	
	public BlockBuilder portal(double outPortalXco, double outPortalYco, double outPortalWidth, double outPortalHeight) {
		outPortalBlock = new BlockBuilder(outPortalXco, outPortalYco, outPortalWidth, outPortalHeight);
		return this;
	}
	
	public BlockBuilder bouncy(double bouncyMultiplier) {
		this.bouncyMultiplier = bouncyMultiplier;
		isBouncy = true;
		return this;
	}
	
	public BlockBuilder checkPoint(int checkPointNumber) {
		this.checkPointNumber = checkPointNumber;
		return this;
	}
	
	public double getXco() {
		return xco;
	}

	public double getYco() {
		return yco;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	public Color getColor() {
		return color;
	}

	public boolean isGoal() {
		return isGoal;
	}
	
	public boolean isDeadly() {
		return isDeadly;
	}
	
	public boolean isBouncy() {
		return isBouncy;
	}
	
	public double getBouncyMultiplier() {
		return bouncyMultiplier;
	}
	
	public BlockBuilder getOutPortalBlock() {
		return outPortalBlock;
	}
	
	public int getCheckPointNumber() {
		return checkPointNumber;
	}
}
