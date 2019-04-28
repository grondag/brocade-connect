package grondag.brocade.connect.api.model;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

import org.apiguardian.api.API;

@API(status = STABLE)
public enum FaceCorner {
    TOP_LEFT(FaceEdge.LEFT, FaceEdge.TOP), TOP_RIGHT(FaceEdge.TOP, FaceEdge.RIGHT),
    BOTTOM_LEFT(FaceEdge.BOTTOM, FaceEdge.LEFT), BOTTOM_RIGHT(FaceEdge.RIGHT, FaceEdge.BOTTOM);

    /**
     * Side that is counterclockwise from the corner.
     */
    public final FaceEdge leftSide;

    /**
     * Side that is clockwise from the corner.
     */
    public final FaceEdge rightSide;

    @API(status = INTERNAL)
    public final int ordinalBit;

    private FaceCorner(FaceEdge leftSide, FaceEdge rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.ordinalBit = 1 << this.ordinal();
    }

    private static FaceCorner[][] LOOKUP = new FaceCorner[4][4];

    static {
        for (FaceCorner corner : FaceCorner.values()) {
            LOOKUP[corner.leftSide.ordinal()][corner.rightSide.ordinal()] = corner;
            LOOKUP[corner.rightSide.ordinal()][corner.leftSide.ordinal()] = corner;
        }
    }
    
    public static FaceCorner find(FaceEdge side1, FaceEdge side2) {
        return LOOKUP[side1.ordinal()][side2.ordinal()];
    }
}
