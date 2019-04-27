package grondag.brocade.connect.api.state;

import java.util.ArrayList;

import grondag.brocade.connect.api.block.CornerJoinTestProvider;
import grondag.brocade.connect.api.model.BlockCorner;
import grondag.brocade.connect.api.model.FaceCorner;
import grondag.brocade.connect.api.model.FaceSide;
import grondag.brocade.connect.impl.SimpleJoin;
import net.minecraft.util.math.Direction;

/**
 * Corner bits indicate that a corner is needed, not that the corner is present.
 * (These are normally inverse.)
 */
public enum CornerJoinFaceState {
    NO_FACE(0, 0), 
    NONE(0, 0), // must be after NO_FACE, overwrites NO_FACE in lookup table, should never be
                               // checked by lookup
    TOP(FaceSide.TOP.ordinalBit, 0), 
    BOTTOM(FaceSide.BOTTOM.ordinalBit, 0), 
    LEFT(FaceSide.LEFT.ordinalBit, 0),
    RIGHT(FaceSide.RIGHT.ordinalBit, 0),
    
    TOP_BOTTOM(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit, 0),
    LEFT_RIGHT(FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit, 0),

    TOP_BOTTOM_RIGHT_NO_CORNERS(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.RIGHT.ordinalBit, 0,
            FaceCorner.TOP_RIGHT, FaceCorner.BOTTOM_RIGHT),
    TOP_BOTTOM_RIGHT_TR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_RIGHT.ordinalBit),
    TOP_BOTTOM_RIGHT_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit),
    TOP_BOTTOM_RIGHT_TR_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_RIGHT.ordinalBit | FaceCorner.BOTTOM_RIGHT.ordinalBit),

    TOP_BOTTOM_LEFT_NO_CORNERS(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit, 0,
            FaceCorner.TOP_LEFT, FaceCorner.BOTTOM_LEFT),
    TOP_BOTTOM_LEFT_TL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit,
            FaceCorner.TOP_LEFT.ordinalBit),
    TOP_BOTTOM_LEFT_BL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit,
            FaceCorner.BOTTOM_LEFT.ordinalBit),
    TOP_BOTTOM_LEFT_TL_BL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit,
            FaceCorner.TOP_LEFT.ordinalBit | FaceCorner.BOTTOM_LEFT.ordinalBit),

    TOP_LEFT_RIGHT_NO_CORNERS(FaceSide.TOP.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit, 0,
            FaceCorner.TOP_LEFT, FaceCorner.TOP_RIGHT),
    TOP_LEFT_RIGHT_TL(FaceSide.TOP.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_LEFT.ordinalBit),
    TOP_LEFT_RIGHT_TR(FaceSide.TOP.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_RIGHT.ordinalBit),
    TOP_LEFT_RIGHT_TL_TR(FaceSide.TOP.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_LEFT.ordinalBit | FaceCorner.TOP_RIGHT.ordinalBit),

    BOTTOM_LEFT_RIGHT_NO_CORNERS(FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit, 0,
            FaceCorner.BOTTOM_LEFT, FaceCorner.BOTTOM_RIGHT),
    BOTTOM_LEFT_RIGHT_BL(FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_LEFT.ordinalBit),
    BOTTOM_LEFT_RIGHT_BR(FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit),
    BOTTOM_LEFT_RIGHT_BL_BR(FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_LEFT.ordinalBit | FaceCorner.BOTTOM_RIGHT.ordinalBit),

    TOP_LEFT_NO_CORNER(FaceSide.TOP.ordinalBit | FaceSide.LEFT.ordinalBit, 0, FaceCorner.TOP_LEFT),
    TOP_LEFT_TL(FaceSide.TOP.ordinalBit | FaceSide.LEFT.ordinalBit, FaceCorner.TOP_LEFT.ordinalBit),

    TOP_RIGHT_NO_CORNER(FaceSide.TOP.ordinalBit | FaceSide.RIGHT.ordinalBit, 0, FaceCorner.TOP_RIGHT),
    TOP_RIGHT_TR(FaceSide.TOP.ordinalBit | FaceSide.RIGHT.ordinalBit, FaceCorner.TOP_RIGHT.ordinalBit),

    BOTTOM_LEFT_NO_CORNER(FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit, 0, FaceCorner.BOTTOM_LEFT),
    BOTTOM_LEFT_BL(FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit, FaceCorner.BOTTOM_LEFT.ordinalBit),

    BOTTOM_RIGHT_NO_CORNER(FaceSide.BOTTOM.ordinalBit | FaceSide.RIGHT.ordinalBit, 0, FaceCorner.BOTTOM_RIGHT),
    BOTTOM_RIGHT_BR(FaceSide.BOTTOM.ordinalBit | FaceSide.RIGHT.ordinalBit, FaceCorner.BOTTOM_RIGHT.ordinalBit),

    ALL_NO_CORNERS(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit, 0,
            FaceCorner.TOP_LEFT, FaceCorner.TOP_RIGHT, FaceCorner.BOTTOM_LEFT, FaceCorner.BOTTOM_RIGHT),
    ALL_TL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_LEFT.ordinalBit),
    ALL_TR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_RIGHT.ordinalBit),
    ALL_TL_TR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.TOP_RIGHT.ordinalBit | FaceCorner.TOP_LEFT.ordinalBit),
    ALL_BL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_LEFT.ordinalBit),
    ALL_TL_BL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_LEFT.ordinalBit | FaceCorner.TOP_LEFT.ordinalBit),
    ALL_TR_BL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_LEFT.ordinalBit | FaceCorner.TOP_RIGHT.ordinalBit),
    ALL_TL_TR_BL(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_LEFT.ordinalBit | FaceCorner.TOP_RIGHT.ordinalBit | FaceCorner.TOP_LEFT.ordinalBit),
    ALL_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit),
    ALL_TL_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit | FaceCorner.TOP_LEFT.ordinalBit),
    ALL_TR_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit | FaceCorner.TOP_RIGHT.ordinalBit),
    ALL_TL_TR_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit | FaceCorner.TOP_RIGHT.ordinalBit | FaceCorner.TOP_LEFT.ordinalBit),
    ALL_BL_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit | FaceCorner.BOTTOM_LEFT.ordinalBit),
    ALL_TL_BL_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit | FaceCorner.BOTTOM_LEFT.ordinalBit | FaceCorner.TOP_LEFT.ordinalBit),
    ALL_TR_BL_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit | FaceCorner.BOTTOM_LEFT.ordinalBit | FaceCorner.TOP_RIGHT.ordinalBit),
    ALL_TL_TR_BL_BR(FaceSide.TOP.ordinalBit | FaceSide.BOTTOM.ordinalBit | FaceSide.LEFT.ordinalBit | FaceSide.RIGHT.ordinalBit,
            FaceCorner.BOTTOM_RIGHT.ordinalBit | FaceCorner.BOTTOM_LEFT.ordinalBit | FaceCorner.TOP_RIGHT.ordinalBit
                    | FaceCorner.TOP_LEFT.ordinalBit);

    /**
     * Sparsely populated - only meaningful states are non-null. For example, cannot
     * also have corners on side with a border.
     */
    private static final CornerJoinFaceState[] LOOKUP = new CornerJoinFaceState[256];

    private final int bitFlags;
    private final FaceCorner[] cornerTests;
    private CornerJoinFaceState[] subStates;

    static {
        for (CornerJoinFaceState state : CornerJoinFaceState.values()) {
            LOOKUP[state.bitFlags] = state;

            ArrayList<CornerJoinFaceState> subStateList = new ArrayList<CornerJoinFaceState>();

            if (state == NO_FACE) {
                subStateList.add(NO_FACE);
            } else {
                for (CornerJoinFaceState subState : CornerJoinFaceState.values()) {
                    if (subState != NO_FACE && (subState.bitFlags & state.bitFlags & 15) == (subState.bitFlags & 15)) {
                        subStateList.add(subState);
                    }
                }
            }
            state.subStates = subStateList.toArray(new CornerJoinFaceState[subStateList.size()]);
        }
    }

    private CornerJoinFaceState(int faceBits, int cornerBits, FaceCorner... cornerTests) {
        this.bitFlags = faceBits | (cornerBits << 4);
        this.cornerTests = cornerTests;
    }

    private static CornerJoinFaceState find(int faceBits, int cornerBits) {
        return LOOKUP[(faceBits & 15) | ((cornerBits & 15) << 4)];
    }

    public static CornerJoinFaceState find(Direction face, SimpleJoin join) {
        int faceFlags = 0;

        CornerJoinFaceState fjs;

        if (join.isJoined(face)) {
            fjs = CornerJoinFaceState.NO_FACE;
        } else {
            for (FaceSide fside : FaceSide.values()) {
                if (join.isJoined(fside.toWorld(face))) {
                    faceFlags |= fside.ordinalBit;
                }
            }

            fjs = CornerJoinFaceState.find(faceFlags, 0);
        }
        return fjs;
    }

    public static CornerJoinFaceState find(Direction face, CornerJoinTestProvider tests) {
        int faceFlags = 0;
        int cornerFlags = 0;

        CornerJoinFaceState fjs;

        if (tests.result(face)) {
            fjs = CornerJoinFaceState.NO_FACE;
        } else {
            for (FaceSide fside : FaceSide.values()) {
                Direction joinFace = fside.toWorld(face);
                if (tests.result(joinFace) && !tests.result(BlockCorner.find(face, joinFace))) {
                    faceFlags |= fside.ordinalBit;
                }
            }

            fjs = CornerJoinFaceState.find(faceFlags, cornerFlags);

            if (fjs.hasCornerTests()) {
                for (FaceCorner corner : fjs.getCornerTests()) {
                    if (!tests.result(corner.leftSide.toWorld(face), corner.rightSide.toWorld(face))
                            || tests.result(corner.leftSide.toWorld(face),
                                    corner.rightSide.toWorld(face), face)) {
                        cornerFlags |= corner.ordinalBit;
                    }
                }

                fjs = CornerJoinFaceState.find(faceFlags, cornerFlags);
            }
        }
        return fjs;
    }

    private boolean hasCornerTests() {
        return (cornerTests != null && cornerTests.length > 0);
    }

    private FaceCorner[] getCornerTests() {
        return cornerTests;
    }

    public CornerJoinFaceState[] getSubStates() {
        return subStates;
    }

    public boolean isJoined(FaceSide side) {
        return (this.bitFlags & side.ordinalBit) == side.ordinalBit;
    }

    public boolean isJoined(Direction toFace, Direction onFace) {
        FaceSide side = FaceSide.fromWorld(toFace, onFace);
        return side == null ? false : this.isJoined(side);
    }

    /**
     * True if connected-texture/shape blocks need to render corner due to
     * missing/covered block in adjacent corner.
     */
    public boolean needsCorner(FaceCorner corner) {
        return ((this.bitFlags >> 4) & corner.ordinalBit) == corner.ordinalBit;
    }

    public boolean needsCorner(Direction face1, Direction face2, Direction onFace) {
        FaceSide side1 = FaceSide.fromWorld(face1, onFace);
        FaceSide side2 = FaceSide.fromWorld(face2, onFace);
        return side1 == null || side2 == null ? false : this.needsCorner(FaceCorner.find(side1, side2));
    }
}
