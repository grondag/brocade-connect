package grondag.brocade.connect.api.model;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

import java.util.function.Consumer;

import org.apiguardian.api.API;

import grondag.brocade.connect.impl.helper.FaceCornerHelper;

@API(status = STABLE)
public enum FaceCorner {
    TOP_LEFT(FaceEdge.LEFT_EDGE, FaceEdge.TOP_EDGE),
    TOP_RIGHT(FaceEdge.TOP_EDGE, FaceEdge.RIGHT_EDGE),
    BOTTOM_LEFT(FaceEdge.BOTTOM_EDGE, FaceEdge.LEFT_EDGE),
    BOTTOM_RIGHT(FaceEdge.RIGHT_EDGE, FaceEdge.BOTTOM_EDGE);

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

    public static final int COUNT = FaceCornerHelper.COUNT;
    
    public static FaceCorner find(FaceEdge side1, FaceEdge side2) {
        return FaceCornerHelper.find(side1, side2);
    }
    
    public static final FaceCorner fromOrdinal(int ordinal) {
        return FaceCornerHelper.fromOrdinal(ordinal);
    }
    
    public static void forEach(Consumer<FaceCorner> consumer) {
        FaceCornerHelper.forEach(consumer);
    }
}
