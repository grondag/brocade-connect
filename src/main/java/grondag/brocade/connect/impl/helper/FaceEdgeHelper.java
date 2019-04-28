package grondag.brocade.connect.impl.helper;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import grondag.brocade.connect.api.model.FaceEdge;
import net.minecraft.util.math.Direction;

public abstract class FaceEdgeHelper {
    private FaceEdgeHelper() {}
    
    private static final FaceEdge[] VALUES = FaceEdge.values();
    
    public static final int COUNT = VALUES.length;
    
    public static final FaceEdge fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    // find the side for a given face orthogonal to a face
    private final static FaceEdge FACE_LOOKUP[][] = new FaceEdge[6][6];

    static {
        for (Direction onFace : Direction.values()) {
            for (Direction edgeFace : Direction.values()) {
                FaceEdge match = null;

                for (FaceEdge side : FaceEdge.values()) {
                    if (side.toWorld(onFace) == edgeFace) {
                        match = side;
                    }
                }
                FACE_LOOKUP[onFace.ordinal()][edgeFace.ordinal()] = match;
            }
        }
    }

    /**
     * Determines if the given sideFace is TOP, BOTTOM, DEFAULT_LEFT or
     * DEFAULT_RIGHT of onFace. If none (sideFace on same orthogonalAxis as onFace),
     * return null;
     */
    @Nullable
    public static FaceEdge fromWorld(Direction edgeFace, Direction onFace) {
        return FACE_LOOKUP[onFace.ordinal()][edgeFace.ordinal()];
    }
    
    public static void forEach(Consumer<FaceEdge> consumer) {
        for(FaceEdge val: VALUES) {
            consumer.accept(val);
        }
    }
}
