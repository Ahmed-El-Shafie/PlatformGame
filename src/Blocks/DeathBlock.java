package Blocks;

import java.awt.Color;

public class DeathBlock extends Block {

	public DeathBlock(double levelWidth, double levelHeight, BlockBuilder blockBuilder) throws Exception {
		super(levelWidth, levelHeight, blockBuilder);
		color = Color.RED;
	}

}
