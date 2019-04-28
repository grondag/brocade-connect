package grondag.brocade.connect.api.block;

import net.minecraft.block.BlockState;

@FunctionalInterface
public interface BlockTest {
    boolean apply(BlockState fromBlockState, Object fromModelState, BlockState toBlockState, Object toModelState);
}
