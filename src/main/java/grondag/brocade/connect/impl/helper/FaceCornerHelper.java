package grondag.brocade.connect.impl.helper;

import java.util.function.Consumer;

import grondag.brocade.connect.api.model.FaceCorner;
import grondag.brocade.connect.api.model.FaceEdge;

public abstract class FaceCornerHelper {
    private FaceCornerHelper() {}
    
    private static final FaceCorner[] VALUES = FaceCorner.values();
    public static final int COUNT = VALUES.length;
    private static FaceCorner[][] LOOKUP = new FaceCorner[4][4];

    static {
        for (FaceCorner corner : VALUES) {
            LOOKUP[corner.leftSide.ordinal()][corner.rightSide.ordinal()] = corner;
            LOOKUP[corner.rightSide.ordinal()][corner.leftSide.ordinal()] = corner;
        }
    }
    
    public static FaceCorner find(FaceEdge side1, FaceEdge side2) {
        return LOOKUP[side1.ordinal()][side2.ordinal()];
    }
    
    public static final FaceCorner fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    public static void forEach(Consumer<FaceCorner> consumer) {
        for(FaceCorner val: VALUES) {
            consumer.accept(val);
        }
    }
}
