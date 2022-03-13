package Blocks;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public abstract class Block {
	protected static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	protected double xco;
	protected double yco;
	protected double width;
	protected double height;
	protected Color color;
	
	public Block(double levelWidth, double levelHeight, BlockBuilder blockBuilder) {
		double xScale = screenSize.getWidth() / levelWidth;
		double yScale = screenSize.getHeight() / levelHeight;
		this.xco = blockBuilder.getXco() * xScale;
		this.yco = blockBuilder.getYco() * yScale;
		this.width = blockBuilder.getWidth() * xScale;
		this.height = blockBuilder.getHeight() * yScale;
	}
	
	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getXco() {
		return xco;
	}
	
	public double getYco() {
		return yco;
	}
	
	public void display(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRoundRect((int) xco, (int) yco,
				(int) width, (int) height,
				5, 5);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRoundRect((int) xco, (int) yco,
				(int) width, (int) height,
				5, 5);
	}
}
