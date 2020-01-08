package paulevs.betternether.registers;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.BlockBaseNotFull;

public class BlockCincinnasitePedestal extends BlockBaseNotFull
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
	
	public BlockCincinnasitePedestal()
	{
		super(FabricBlockSettings.copy(BlocksRegister.BLOCK_CINCINNASITE).nonOpaque().build());
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE;
	}
}
