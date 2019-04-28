package grondag.brocade.connect.api.model;

import java.util.function.Consumer;

import grondag.brocade.connect.impl.helper.BlockCornerHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

public enum BlockCorner {
    UP_NORTH_EAST(Direction.UP, Direction.EAST, Direction.NORTH),
    UP_NORTH_WEST(Direction.UP, Direction.WEST, Direction.NORTH),
    UP_SOUTH_EAST(Direction.UP, Direction.EAST, Direction.SOUTH),
    UP_SOUTH_WEST(Direction.UP, Direction.WEST, Direction.SOUTH),
    DOWN_NORTH_EAST(Direction.DOWN, Direction.EAST, Direction.NORTH),
    DOWN_NORTH_WEST(Direction.DOWN, Direction.WEST, Direction.NORTH),
    DOWN_SOUTH_EAST(Direction.DOWN, Direction.EAST, Direction.SOUTH),
    DOWN_SOUTH_WEST(Direction.DOWN, Direction.WEST, Direction.SOUTH);

    public final Direction face1;
    public final Direction face2;
    public final Direction face3;
    public final Vec3i vector;
    
    /**
     * Ordinal sequence that includes all faces, corner and far corners. Use to
     * index them in a mixed array.
     */
    public final int superOrdinal;
    public final int superOrdinalBit;
    
    private BlockCorner(Direction face1, Direction face2, Direction face3) {
        this.face1 = face1;
        this.face2 = face2;
        this.face3 = face3;
        
        // 6 is number of possible faces
        this.superOrdinal = 6 + this.ordinal() + BlockEdge.values().length;
        this.superOrdinalBit = 1 << superOrdinal;

        Vec3i v1 = face1.getVector();
        Vec3i v2 = face2.getVector();
        Vec3i v3 = face3.getVector();
        this.vector = new Vec3i(v1.getX() + v2.getX() + v3.getX(), v1.getY() + v2.getY() + v3.getY(),
                v1.getZ() + v2.getZ() + v3.getZ());

    }
    
    public static final int COUNT = BlockCornerHelper.COUNT;
    
    public static BlockCorner find(Direction face1, Direction face2, Direction face3) {
        return BlockCornerHelper.find(face1, face2, face3);
    }
    
    public static final BlockCorner fromOrdinal(int ordinal) {
        return BlockCornerHelper.fromOrdinal(ordinal);
    }
    
    public static void forEach(Consumer<BlockCorner> consumer) {
        BlockCornerHelper.forEach(consumer);
    }
}