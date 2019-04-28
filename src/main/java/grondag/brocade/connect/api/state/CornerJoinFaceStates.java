package grondag.brocade.connect.api.state;

import grondag.brocade.connect.impl.CornerJoinFaceStateImpl;

public abstract class CornerJoinFaceStates {
    private CornerJoinFaceStates() {};
    
    public static final int COUNT = CornerJoinFaceStateImpl.values().length;
    
    public static final CornerJoinFaceState NO_FACE = CornerJoinFaceStateImpl.NO_FACE;
    public static final CornerJoinFaceState NONE = CornerJoinFaceStateImpl.NONE;
    
    public static final CornerJoinFaceState TOP = CornerJoinFaceStateImpl.TOP;
    public static final CornerJoinFaceState BOTTOM = CornerJoinFaceStateImpl.BOTTOM;
    public static final CornerJoinFaceState LEFT = CornerJoinFaceStateImpl.LEFT;
    public static final CornerJoinFaceState RIGHT = CornerJoinFaceStateImpl.RIGHT;
    
    public static final CornerJoinFaceState TOP_BOTTOM = CornerJoinFaceStateImpl.TOP_BOTTOM;
    public static final CornerJoinFaceState LEFT_RIGHT = CornerJoinFaceStateImpl.LEFT_RIGHT;

    public static final CornerJoinFaceState TOP_BOTTOM_RIGHT_NO_CORNERS = CornerJoinFaceStateImpl.TOP_BOTTOM_RIGHT_NO_CORNERS;
    public static final CornerJoinFaceState TOP_BOTTOM_RIGHT_TR = CornerJoinFaceStateImpl.TOP_BOTTOM_RIGHT_TR;
    public static final CornerJoinFaceState TOP_BOTTOM_RIGHT_BR = CornerJoinFaceStateImpl.TOP_BOTTOM_RIGHT_BR;
    public static final CornerJoinFaceState TOP_BOTTOM_RIGHT_TR_BR = CornerJoinFaceStateImpl.TOP_BOTTOM_RIGHT_TR_BR;
    
    public static final CornerJoinFaceState TOP_BOTTOM_LEFT_NO_CORNERS = CornerJoinFaceStateImpl.TOP_BOTTOM_LEFT_NO_CORNERS;
    public static final CornerJoinFaceState TOP_BOTTOM_LEFT_TL = CornerJoinFaceStateImpl.TOP_BOTTOM_LEFT_TL;
    public static final CornerJoinFaceState TOP_BOTTOM_LEFT_BL = CornerJoinFaceStateImpl.TOP_BOTTOM_LEFT_BL;
    public static final CornerJoinFaceState TOP_BOTTOM_LEFT_TL_BL = CornerJoinFaceStateImpl.TOP_BOTTOM_LEFT_TL_BL;
    
    public static final CornerJoinFaceState TOP_LEFT_RIGHT_NO_CORNERS = CornerJoinFaceStateImpl.TOP_LEFT_RIGHT_NO_CORNERS;
    public static final CornerJoinFaceState TOP_LEFT_RIGHT_TL = CornerJoinFaceStateImpl.TOP_LEFT_RIGHT_TL;
    public static final CornerJoinFaceState TOP_LEFT_RIGHT_TR = CornerJoinFaceStateImpl.TOP_LEFT_RIGHT_TR;
    public static final CornerJoinFaceState TOP_LEFT_RIGHT_TL_TR = CornerJoinFaceStateImpl.TOP_LEFT_RIGHT_TL_TR;
    
    public static final CornerJoinFaceState BOTTOM_LEFT_RIGHT_NO_CORNERS = CornerJoinFaceStateImpl.BOTTOM_LEFT_RIGHT_NO_CORNERS;
    public static final CornerJoinFaceState BOTTOM_LEFT_RIGHT_BL = CornerJoinFaceStateImpl.BOTTOM_LEFT_RIGHT_BL;
    public static final CornerJoinFaceState BOTTOM_LEFT_RIGHT_BR = CornerJoinFaceStateImpl.BOTTOM_LEFT_RIGHT_BR;
    public static final CornerJoinFaceState BOTTOM_LEFT_RIGHT_BL_BR = CornerJoinFaceStateImpl.BOTTOM_LEFT_RIGHT_BL_BR;
    
    public static final CornerJoinFaceState TOP_LEFT_NO_CORNER = CornerJoinFaceStateImpl.TOP_LEFT_NO_CORNER;
    public static final CornerJoinFaceState TOP_LEFT_TL = CornerJoinFaceStateImpl.TOP_LEFT_TL;
    public static final CornerJoinFaceState TOP_RIGHT_NO_CORNER = CornerJoinFaceStateImpl.TOP_RIGHT_NO_CORNER;
    public static final CornerJoinFaceState TOP_RIGHT_TR = CornerJoinFaceStateImpl.TOP_RIGHT_TR;
    
    public static final CornerJoinFaceState BOTTOM_LEFT_NO_CORNER = CornerJoinFaceStateImpl.BOTTOM_LEFT_NO_CORNER;
    public static final CornerJoinFaceState BOTTOM_LEFT_BL = CornerJoinFaceStateImpl.BOTTOM_LEFT_BL;
    public static final CornerJoinFaceState BOTTOM_RIGHT_NO_CORNER = CornerJoinFaceStateImpl.BOTTOM_RIGHT_NO_CORNER;
    public static final CornerJoinFaceState BOTTOM_RIGHT_BR = CornerJoinFaceStateImpl.BOTTOM_RIGHT_BR;
    
    public static final CornerJoinFaceState ALL_NO_CORNERS = CornerJoinFaceStateImpl.ALL_NO_CORNERS;
    public static final CornerJoinFaceState ALL_TL = CornerJoinFaceStateImpl.ALL_TL;
    public static final CornerJoinFaceState ALL_TR = CornerJoinFaceStateImpl.ALL_TR;
    public static final CornerJoinFaceState ALL_TL_TR = CornerJoinFaceStateImpl.ALL_TL_TR;
    public static final CornerJoinFaceState ALL_BL = CornerJoinFaceStateImpl.ALL_BL;
    public static final CornerJoinFaceState ALL_TL_BL = CornerJoinFaceStateImpl.ALL_TL_BL;
    public static final CornerJoinFaceState ALL_TR_BL = CornerJoinFaceStateImpl.ALL_TR_BL;
    public static final CornerJoinFaceState ALL_TL_TR_BL = CornerJoinFaceStateImpl.ALL_TL_TR_BL;
    public static final CornerJoinFaceState ALL_BR = CornerJoinFaceStateImpl.ALL_BR;
    public static final CornerJoinFaceState ALL_TL_BR = CornerJoinFaceStateImpl.ALL_TL_BR;
    public static final CornerJoinFaceState ALL_TR_BR = CornerJoinFaceStateImpl.ALL_TR_BR;
    public static final CornerJoinFaceState ALL_TL_TR_BR = CornerJoinFaceStateImpl.ALL_TL_TR_BR;
    public static final CornerJoinFaceState ALL_BL_BR = CornerJoinFaceStateImpl.ALL_BL_BR;
    public static final CornerJoinFaceState ALL_TL_BL_BR = CornerJoinFaceStateImpl.ALL_TL_BL_BR;
    public static final CornerJoinFaceState ALL_TR_BL_BR = CornerJoinFaceStateImpl.ALL_TR_BL_BR;
    public static final CornerJoinFaceState ALL_TL_TR_BL_BR = CornerJoinFaceStateImpl.ALL_TL_TR_BL_BR;
}
