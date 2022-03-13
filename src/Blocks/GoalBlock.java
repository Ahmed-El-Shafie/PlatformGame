package Blocks;
import java.awt.Color;

public class GoalBlock extends Block {

	public GoalBlock(double levelWidth, double levelHeight, BlockBuilder blockBuilder) throws Exception {
		super(levelWidth, levelHeight, blockBuilder);
		color = Color.GREEN;
	}
}
