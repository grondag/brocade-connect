package grondag.brocade.connect.api.model;

import static org.apiguardian.api.API.Status.STABLE;

import java.util.function.Consumer;

import org.apiguardian.api.API;

import grondag.brocade.connect.impl.helper.HorizontalFaceHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

@API(status = STABLE)
public enum HorizontalFace {
    NORTH(Direction.NORTH),
    EAST(Direction.EAST),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST);

    public final Direction face;

    public final Vec3i vector;

    private HorizontalFace(Direction face) {
        this.face = face;
        this.vector = face.getVector();
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
    
    public static final int COUNT = HorizontalFaceHelper.COUNT;

    public static HorizontalFace find(Direction face) {
        return HorizontalFaceHelper.find(face);
    }
    
    public static final HorizontalFace fromOrdinal(int ordinal) {
        return HorizontalFaceHelper.fromOrdinal(ordinal);
    }
    
    public static void forEach(Consumer<HorizontalFace> consumer) {
        HorizontalFaceHelper.forEach(consumer);
    }
}