package grondag.brocade.connect.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * Used to implement visitor pattern for block-state dependent conditional logic.
 */
@FunctionalInterface
public interface ModelStateFunction<V> {
    public V get(BlockView world, BlockState blockState, BlockPos pos);
}