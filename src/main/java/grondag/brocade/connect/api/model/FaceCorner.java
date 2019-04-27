package grondag.brocade.connect.api.model;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

import org.apiguardian.api.API;

@API(status = STABLE)
public enum FaceCorner {
    TOP_LEFT(FaceSide.LEFT, FaceSide.TOP), TOP_RIGHT(FaceSide.TOP, FaceSide.RIGHT),
    BOTTOM_LEFT(FaceSide.BOTTOM, FaceSide.LEFT), BOTTOM_RIGHT(FaceSide.RIGHT, FaceSide.BOTTOM);

    /**
     * Side that is counterclockwise from the corner.
     */
    public final FaceSide leftSide;

    /**
     * Side that is clockwise from the corner.
     */
    public final FaceSide rightSide;

    @API(status = INTERNAL)
    public final int ordinalBit;

    private FaceCorner(FaceSide leftSide, FaceSide rightSide) {
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
    
    public static FaceCorner find(FaceSide side1, FaceSide side2) {
        return LOOKUP[side1.ordinal()][side2.ordinal()];
    }
}
