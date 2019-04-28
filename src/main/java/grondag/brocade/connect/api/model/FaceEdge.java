package grondag.brocade.connect.api.model;

import static net.minecraft.util.math.Direction.DOWN;
import static net.minecraft.util.math.Direction.EAST;
import static net.minecraft.util.math.Direction.NORTH;
import static net.minecraft.util.math.Direction.SOUTH;
import static net.minecraft.util.math.Direction.UP;
import static net.minecraft.util.math.Direction.WEST;
import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apiguardian.api.API;

import grondag.brocade.connect.impl.helper.FaceEdgeHelper;
import net.minecraft.util.math.Direction;

@API(status = STABLE)
public enum FaceEdge {
    TOP_EDGE(SOUTH, SOUTH, UP, UP, UP, UP),
    BOTTOM_EDGE(NORTH, NORTH, DOWN, DOWN, DOWN, DOWN),
    LEFT_EDGE(EAST, WEST, SOUTH, NORTH, EAST, WEST),
    RIGHT_EDGE(WEST, EAST, NORTH, SOUTH, WEST, EAST);

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
        case BOTTOM_EDGE:
            return LEFT_EDGE;
        case LEFT_EDGE:
            return TOP_EDGE;
        case RIGHT_EDGE:
            return BOTTOM_EDGE;
        case TOP_EDGE:
            return RIGHT_EDGE;
        default:
            return null;
        }
    }

    public FaceEdge counterClockwise() {
        switch (this) {
        case BOTTOM_EDGE:
            return RIGHT_EDGE;
        case LEFT_EDGE:
            return BOTTOM_EDGE;
        case RIGHT_EDGE:
            return TOP_EDGE;
        case TOP_EDGE:
            return LEFT_EDGE;
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
    
    /**
     * Determines if the given sideFace is TOP, BOTTOM, DEFAULT_LEFT or
     * DEFAULT_RIGHT of onFace. If none (sideFace on same orthogonalAxis as onFace),
     */
    @Nullable
    public static FaceEdge fromWorld(Direction edgeFace, Direction onFace) {
        return FaceEdgeHelper.fromWorld(edgeFace, onFace);
    }
    
    public static final int COUNT = FaceEdgeHelper.COUNT;
    
    public static final FaceEdge fromOrdinal(int ordinal) {
        return FaceEdgeHelper.fromOrdinal(ordinal);
    }
    
    public static void forEach(Consumer<FaceEdge> consumer) {
        FaceEdgeHelper.forEach(consumer);
    }
}
