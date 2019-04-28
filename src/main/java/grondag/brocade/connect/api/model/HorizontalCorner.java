package grondag.brocade.connect.api.model;

import java.util.function.Consumer;

import grondag.brocade.connect.impl.helper.HorizontalCornerHelper;
import net.minecraft.util.math.Vec3i;

public enum HorizontalCorner {
    NORTH_EAST(HorizontalFace.NORTH, HorizontalFace.EAST), 
    NORTH_WEST(HorizontalFace.NORTH, HorizontalFace.WEST),
    SOUTH_EAST(HorizontalFace.SOUTH, HorizontalFace.EAST), 
    SOUTH_WEST(HorizontalFace.SOUTH, HorizontalFace.WEST);

    public final HorizontalFace face1;
    public final HorizontalFace face2;

    public final Vec3i vector;

    private HorizontalCorner(HorizontalFace face1, HorizontalFace face2) {
        this.face1 = face1;
        this.face2 = face2;
        this.vector = new Vec3i(face1.face.getVector().getX() + face2.face.getVector().getX(), 0,
                face1.face.getVector().getZ() + face2.face.getVector().getZ());
    }

    public static final int COUNT = HorizontalCornerHelper.COUNT;
    
    public static HorizontalCorner find(HorizontalFace face1, HorizontalFace face2) {
        return HorizontalCornerHelper.find(face1, face2);
    }
    
    public static HorizontalCorner fromOrdinal(int ordinal) {
        return HorizontalCornerHelper.fromOrdinal(ordinal);
    }
    
    public static void forEach(Consumer<HorizontalCorner> consumer) {
        HorizontalCornerHelper.forEach(consumer);
    }
}