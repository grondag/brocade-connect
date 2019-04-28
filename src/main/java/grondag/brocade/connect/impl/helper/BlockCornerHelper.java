package grondag.brocade.connect.impl.helper;

import java.util.function.Consumer;

import grondag.brocade.connect.api.model.BlockCorner;
import net.minecraft.util.math.Direction;

public abstract class BlockCornerHelper {
    private BlockCornerHelper() {}

    private static final BlockCorner[] VALUES = BlockCorner.values();
    public static final int COUNT = VALUES.length;
    private static final BlockCorner[][][] FAR_CORNER_LOOKUP = new BlockCorner[6][6][6];

    static {
        for (BlockCorner corner : VALUES) {
            FAR_CORNER_LOOKUP[corner.face1.ordinal()][corner.face2.ordinal()][corner.face3.ordinal()] = corner;
            FAR_CORNER_LOOKUP[corner.face1.ordinal()][corner.face3.ordinal()][corner.face2.ordinal()] = corner;
            FAR_CORNER_LOOKUP[corner.face2.ordinal()][corner.face1.ordinal()][corner.face3.ordinal()] = corner;
            FAR_CORNER_LOOKUP[corner.face2.ordinal()][corner.face3.ordinal()][corner.face1.ordinal()] = corner;
            FAR_CORNER_LOOKUP[corner.face3.ordinal()][corner.face2.ordinal()][corner.face1.ordinal()] = corner;
            FAR_CORNER_LOOKUP[corner.face3.ordinal()][corner.face1.ordinal()][corner.face2.ordinal()] = corner;
        }
    }
    
    public static BlockCorner find(Direction face1, Direction face2, Direction face3) {
        return FAR_CORNER_LOOKUP[face1.ordinal()][face2.ordinal()][face3.ordinal()];
    }
    
    public static final BlockCorner fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    public static void forEach(Consumer<BlockCorner> consumer) {
        for(BlockCorner val: VALUES) {
            consumer.accept(val);
        }
    }
}
