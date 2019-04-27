package grondag.brocade.connect.api.model;

import static org.apiguardian.api.API.Status.STABLE;

import org.apiguardian.api.API;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

@API(status = STABLE)
public enum HorizontalFace {
    NORTH(Direction.NORTH),
    EAST(Direction.EAST),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST);

    public final Direction face;

    public final Vec3i directionVector;

    private HorizontalFace(Direction face) {
        this.face = face;
        this.directionVector = face.getVector();
    }

    public HorizontalFace left() {
        if (this.ordinal() == 0) {
            return HorizontalFace.values()[3];
        } else {
            return HorizontalFace.values()[this.ordinal() - 1];
        }
    }

    public HorizontalFace right() {
        if (this.ordinal() == 3) {
            return HorizontalFace.values()[0];
        } else {
            return HorizontalFace.values()[this.ordinal() + 1];
        }
    }
    
    public static HorizontalFace find(Direction face) {
        return HorizontalFace.HORIZONTAL_FACE_LOOKUP[face.ordinal()];
    }
    
    private static final HorizontalFace[] VALUES = HorizontalFace.values();
    public static final int COUNT = VALUES.length;
    public static final HorizontalFace fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    private static final HorizontalFace HORIZONTAL_FACE_LOOKUP[] = new HorizontalFace[6];

    static {
        for (HorizontalFace hFace : HorizontalFace.values()) {
            HORIZONTAL_FACE_LOOKUP[hFace.face.ordinal()] = hFace;
        }
    }
}