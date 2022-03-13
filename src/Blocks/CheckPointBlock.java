package Blocks;

import java.awt.Color;

public class CheckPointBlock extends Block {

	private int checkPointNumber;
	
	public CheckPointBlock(double levelWidth, double levelHeight, BlockBuilder blockBuilder, int checkPointNumber) throws Exception {
		super(levelWidth, levelHeight, blockBuilder);
		this.checkPointNumber = checkPointNumber;
		color = Color.BLACK;
	}
	
	public void passed() {
		color = Color.WHITE;
	}

	public int getCheckPointNumber() {
		return checkPointNumber;
	}
}
