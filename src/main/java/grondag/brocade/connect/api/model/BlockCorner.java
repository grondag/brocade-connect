package grondag.brocade.connect.api.model;

import static grondag.brocade.connect.api.model.FaceRotation.*;
import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

import javax.annotation.Nullable;

import org.apiguardian.api.API;

import grondag.brocade.connect.impl.NeighborBlocksImpl;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Vec3i;

@API(status = STABLE)
public enum BlockCorner {
    UP_EAST(Direction.UP, Direction.EAST, ROTATE_NONE), // Z AXIS
    UP_WEST(Direction.UP, Direction.WEST, ROTATE_270), // Z AXIS
    UP_NORTH(Direction.UP, Direction.NORTH, ROTATE_180), // X AXIS
    UP_SOUTH(Direction.UP, Direction.SOUTH, ROTATE_90), // X AXIS
    NORTH_EAST(Direction.NORTH, Direction.EAST, ROTATE_NONE), // Y AXIS
    NORTH_WEST(Direction.NORTH, Direction.WEST, ROTATE_270), // Y AXIS
    SOUTH_EAST(Direction.SOUTH, Direction.EAST, ROTATE_90), // Y AXIS
    SOUTH_WEST(Direction.SOUTH, Direction.WEST, ROTATE_180), // Y AXIS
    DOWN_EAST(Direction.DOWN, Direction.EAST, ROTATE_90), // Z AXIS
    DOWN_WEST(Direction.DOWN, Direction.WEST, ROTATE_180), // Z AXIS
    DOWN_NORTH(Direction.DOWN, Direction.NORTH, ROTATE_270), // X AXIS
    DOWN_SOUTH(Direction.DOWN, Direction.SOUTH, ROTATE_NONE); // X AXIS

    public final Direction face1;
    public final Direction face2;

    /**
     * Axis that is orthogonal to both faces.
     */
    public final Direction.Axis orthogonalAxis;

    /**
     * Used to position models like stairs/wedges. Representation rotation around
     * the orthogonal axis such that face1 and face2 are most occluded. Based on
     * "default" model having Y axis and occluding north and east faces.
     */
    public final FaceRotation modelRotation;

    @API(status = INTERNAL)
    public final int ordinalBit;
    
    public final Vec3i directionVector;
    
    /**
     * Ordinal sequence that includes all faces, corner and far corners. 
     * Used to index them in a mixed array.
     */
    public final int superOrdinal;

    /**
     * Will be null if not a horizontal corner.
     */
    @Nullable
    public final HorizontalCorner horizonal;
    
    private static final BlockCorner[][] CORNER_LOOKUP = new BlockCorner[6][6];

    /** used to look up by axis and rotation */
    private static final BlockCorner[][] MODEL_LOOKUP = new BlockCorner[3][4];

    static {
        for (BlockCorner corner : BlockCorner.values()) {
            CORNER_LOOKUP[corner.face1.ordinal()][corner.face2.ordinal()] = corner;
            CORNER_LOOKUP[corner.face2.ordinal()][corner.face1.ordinal()] = corner;
            MODEL_LOOKUP[corner.orthogonalAxis.ordinal()][corner.modelRotation.ordinal()] = corner;
        }
    }

    private BlockCorner(Direction face1, Direction face2, FaceRotation modelRotation) {
        this.face1 = face1;
        this.face2 = face2;
        this.modelRotation = modelRotation;
        this.ordinalBit = 1 << (NeighborBlocksImpl.FACE_FLAGS.length + this.ordinal());
        this.superOrdinal = 6 + this.ordinal();
        boolean hasX = (face1.getAxis() == Direction.Axis.X || face2.getAxis() == Direction.Axis.X);
        boolean hasY = (face1.getAxis() == Direction.Axis.Y || face2.getAxis() == Direction.Axis.Y);
        this.orthogonalAxis = hasX && hasY ? Direction.Axis.Z : hasX ? Direction.Axis.Y : Direction.Axis.X;

        Vec3i v1 = face1.getVector();
        Vec3i v2 = face2.getVector();
        this.directionVector = new Vec3i(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());

        if (face1.getAxis() == Axis.Y || face2.getAxis() == Axis.Y)
            this.horizonal = null;
        else
            this.horizonal = HorizontalCorner.find(HorizontalFace.find(face1), HorizontalFace.find(face2));
    }

    public static BlockCorner find(Direction face1, Direction face2) {
        return BlockCorner.CORNER_LOOKUP[face1.ordinal()][face2.ordinal()];
    }

    public static BlockCorner find(Direction.Axis axis, FaceRotation modelRotation) {
        return BlockCorner.MODEL_LOOKUP[axis.ordinal()][modelRotation.ordinal()];
    }
}