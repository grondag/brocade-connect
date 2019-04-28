package grondag.brocade.connect.api.model;

import static net.minecraft.util.math.Direction.DOWN;
import static net.minecraft.util.math.Direction.EAST;
import static net.minecraft.util.math.Direction.NORTH;
import static net.minecraft.util.math.Direction.SOUTH;
import static net.minecraft.util.math.Direction.UP;
import static net.minecraft.util.math.Direction.WEST;
import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

import javax.annotation.Nullable;

import org.apiguardian.api.API;

import net.minecraft.util.math.Direction;

@API(status = STABLE)
public enum FaceEdge {
    TOP(SOUTH, SOUTH, UP, UP, UP, UP),
    BOTTOM(NORTH, NORTH, DOWN, DOWN, DOWN, DOWN),
    LEFT(EAST, WEST, SOUTH, NORTH, EAST, WEST),
    RIGHT(WEST, EAST, NORTH, SOUTH, WEST, EAST);

    // for a given face, which face is at the position identified by this enum?
    private final Direction relativeLookup[];

    private FaceEdge(Direction... relativeLookup) {
        this.relativeLookup = relativeLookup;
        this.ordinalBit = 1 << this.ordinal();
    }

    @API(status = INTERNAL)
    public final int ordinalBit;

    public FaceEdge clockwise() {
        switch (this) {
        case BOTTOM:
            return LEFT;
        case LEFT:
            return TOP;
        case RIGHT:
            return BOTTOM;
        case TOP:
            return RIGHT;
        default:
            return null;
        }
    }

    public FaceEdge counterClockwise() {
        switch (this) {
        case BOTTOM:
            return RIGHT;
        case LEFT:
            return BOTTOM;
        case RIGHT:
            return TOP;
        case TOP:
            return LEFT;
        default:
            return null;
        }
    }

    /**
     * Returns the block face next to this FaceSide on the given block face.
     */
    public Direction toWorld(Direction face) {
        return relativeLookup[face.ordinal()];
    }
    
    private static final FaceEdge[] VALUES = FaceEdge.values();
    
    public static final int COUNT = VALUES.length;
    public static final FaceEdge fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    // find the side for a given face orthogonal to a face
    private final static FaceEdge FACE_LOOKUP[][] = new FaceEdge[6][6];

    static {
        for (Direction onFace : Direction.values()) {
            for (Direction sideFace : Direction.values()) {
                FaceEdge match = null;

                for (FaceEdge side : FaceEdge.values()) {
                    if (side.toWorld(onFace) == sideFace) {
                        match = side;
                    }
                }
                FACE_LOOKUP[onFace.ordinal()][sideFace.ordinal()] = match;
            }
        }
    }

    /**
     * Determines if the given sideFace is TOP, BOTTOM, DEFAULT_LEFT or
     * DEFAULT_RIGHT of onFace. If none (sideFace on same orthogonalAxis as onFace),
     * return null;
     */
    @Nullable
    public static FaceEdge fromWorld(Direction sideFace, Direction onFace) {
        return FACE_LOOKUP[onFace.ordinal()][sideFace.ordinal()];
    }
}
