package grondag.brocade.connect.impl.helper;

import java.util.function.Consumer;

import grondag.brocade.connect.api.model.HorizontalCorner;
import grondag.brocade.connect.api.model.HorizontalFace;

public abstract class HorizontalCornerHelper {
    private HorizontalCornerHelper() {}
    
    private static final HorizontalCorner[] VALUES = HorizontalCorner.values();
    public static final int COUNT = VALUES.length;

    private static final HorizontalCorner[][] HORIZONTAL_CORNER_LOOKUP = new HorizontalCorner[4][4];

    static {
        for (HorizontalCorner corner : HorizontalCorner.values()) {
            HORIZONTAL_CORNER_LOOKUP[corner.face1.ordinal()][corner.face2.ordinal()] = corner;
            HORIZONTAL_CORNER_LOOKUP[corner.face2.ordinal()][corner.face1.ordinal()] = corner;
        }
    }
    
    public static HorizontalCorner find(HorizontalFace face1, HorizontalFace face2) {
        return HORIZONTAL_CORNER_LOOKUP[face1.ordinal()][face2.ordinal()];
    }
    
    public static HorizontalCorner fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    public static void forEach(Consumer<HorizontalCorner> consumer) {
        for(HorizontalCorner val: VALUES) {
            consumer.accept(val);
        }
    }
}
