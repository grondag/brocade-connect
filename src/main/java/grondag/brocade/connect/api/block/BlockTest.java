package grondag.brocade.connect.api.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;

@FunctionalInterface
public interface BlockTest {
    boolean apply(BlockState fromBlockState, @Nullable Object fromModelState, BlockState toBlockState, @Nullable Object toModelState);
}
