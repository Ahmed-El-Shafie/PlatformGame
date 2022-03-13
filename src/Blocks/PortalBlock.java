package Blocks;

import java.awt.Color;

public class PortalBlock extends Block {

	private PortalBlock outPortalBlock;
	public PortalBlock(double levelWidth, double levelHeight, BlockBuilder blockBuilder) throws Exception {
		super(levelWidth, levelHeight, blockBuilder);
		color = new Color(102, 0, 102);
	}
	
	public void setOutPortalBlock(PortalBlock outPortalBlock) {
		this.outPortalBlock = outPortalBlock;
	}
	
	public PortalBlock getOutPortalBlock() {
		return outPortalBlock;
	}

}
