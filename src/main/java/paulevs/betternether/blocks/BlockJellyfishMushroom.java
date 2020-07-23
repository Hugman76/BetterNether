package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockJellyfishMushroom extends BlockBaseNotFull
{
	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 15.99, 11);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);
	
	public BlockJellyfishMushroom()
	{
		super(Materials.makeWood(MaterialColor.CYAN).sounds(BlockSoundGroup.FUNGUS).nonOpaque().lightLevel(13));
		this.setRenderLayer(BNRenderLayer.TRANSLUCENT);
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return state.get(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		TripleShape shape = state.get(SHAPE);
		return shape == TripleShape.TOP ? new ItemStack(BlocksRegistry.GIANT_MOLD_SAPLING) : new ItemStack(BlocksRegistry.MUSHROOM_STEM);
	}
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		switch (state.get(SHAPE))
		{
		case BOTTOM:
		case MIDDLE:
			return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) ? state : Blocks.AIR.getDefaultState();
		case TOP:
		default:
			return world.getBlockState(pos.down()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
	}
}