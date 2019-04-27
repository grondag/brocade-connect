package grondag.brocade.connect.api.model;

import static org.apiguardian.api.API.Status.*;

import javax.annotation.Nullable;

import org.apiguardian.api.API;

import net.minecraft.util.math.Direction;

@API(status = STABLE)
public enum FaceSide {
    TOP(Direction.SOUTH, Direction.SOUTH, Direction.UP, Direction.UP, Direction.UP, Direction.UP),
    BOTTOM(Direction.NORTH, Direction.NORTH, Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN),
    LEFT(Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST),
    RIGHT(Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

    // for a given face, which face is at the position identified by this enum?
    private final Direction RELATIVE_LOOKUP[] = new Direction[6];

    private FaceSide(Direction up, Direction down, Direction east, Direction west, Direction north, Direction south) {
        RELATIVE_LOOKUP[Direction.UP.ordinal()] = up;
        RELATIVE_LOOKUP[Direction.DOWN.ordinal()] = down;
        RELATIVE_LOOKUP[Direction.EAST.ordinal()] = east;
        RELATIVE_LOOKUP[Direction.WEST.ordinal()] = west;
        RELATIVE_LOOKUP[Direction.NORTH.ordinal()] = north;
        RELATIVE_LOOKUP[Direction.SOUTH.ordinal()] = south;

        this.ordinalBit = 1 << this.ordinal();
    }

    @API(status = INTERNAL)
    public final int ordinalBit;

    public FaceSide clockwise() {
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

    public FaceSide counterClockwise() {
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
        return RELATIVE_LOOKUP[face.ordinal()];
    }
    
    private static final FaceSide[] VALUES = FaceSide.values();
    
    public static final int COUNT = VALUES.length;
    public static final FaceSide fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    // find the side for a given face orthogonal to a face
    private final static FaceSide FACE_LOOKUP[][] = new FaceSide[6][6];

    static {
        for (Direction onFace : Direction.values()) {
            for (Direction sideFace : Direction.values()) {
                FaceSide match = null;

                for (FaceSide side : FaceSide.values()) {
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
    public static FaceSide fromWorld(Direction sideFace, Direction onFace) {
        return FACE_LOOKUP[onFace.ordinal()][sideFace.ordinal()];
    }
}
