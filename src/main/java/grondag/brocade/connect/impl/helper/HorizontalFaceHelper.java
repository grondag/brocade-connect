package grondag.brocade.connect.impl.helper;

import java.util.function.Consumer;

import grondag.brocade.connect.api.model.HorizontalFace;
import net.minecraft.util.math.Direction;

public abstract class HorizontalFaceHelper {
    private HorizontalFaceHelper() {}
    
    private static final HorizontalFace[] VALUES = HorizontalFace.values();
    public static final int COUNT = VALUES.length;
    
    private static final HorizontalFace HORIZONTAL_FACE_LOOKUP[] = new HorizontalFace[6];

    static {
        for (HorizontalFace hFace : HorizontalFace.values()) {
            HORIZONTAL_FACE_LOOKUP[hFace.face.ordinal()] = hFace;
        }
    }
    
    public static HorizontalFace find(Direction face) {
        return HORIZONTAL_FACE_LOOKUP[face.ordinal()];
    }
    
    public static final HorizontalFace fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    public static void forEach(Consumer<HorizontalFace> consumer) {
        for(HorizontalFace val: VALUES) {
            consumer.accept(val);
        }
    }
}
