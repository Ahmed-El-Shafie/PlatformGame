package Blocks;

import java.awt.Color;

public class PlatformBlock extends Block {

	public PlatformBlock(double levelWidth, double levelHeight, BlockBuilder blockBuilder) throws Exception {
		super(levelWidth, levelHeight, blockBuilder);
		this.color = Color.ORANGE;
	}

}
