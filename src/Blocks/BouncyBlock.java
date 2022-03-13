package Blocks;

import java.awt.Color;

public class BouncyBlock extends Block {

	private double bouncyMultiplier;
	
	public BouncyBlock(double levelWidth, double levelHeight, BlockBuilder blockBuilder) throws Exception {
		super(levelWidth, levelHeight, blockBuilder);
		color = Color.CYAN;
		bouncyMultiplier = blockBuilder.getBouncyMultiplier();
	}

	public double getBouncyMultiplier() {
		return bouncyMultiplier;
	}
}
