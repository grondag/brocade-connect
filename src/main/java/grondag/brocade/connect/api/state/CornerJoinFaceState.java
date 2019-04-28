package grondag.brocade.connect.api.state;

import grondag.brocade.connect.api.model.FaceCorner;
import grondag.brocade.connect.api.model.FaceEdge;
import net.minecraft.util.math.Direction;

public interface CornerJoinFaceState {

    int ordinal();
    
    boolean isJoined(FaceEdge side);

    boolean isJoined(Direction toFace, Direction onFace);

    /**
     * True if connected-texture/shape blocks need to render corner due to
     * missing/covered block in adjacent corner.
     */
    boolean needsCorner(FaceCorner corner);

    boolean needsCorner(Direction face1, Direction face2, Direction onFace);

}