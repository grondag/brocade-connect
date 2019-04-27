package grondag.brocade.connect.api.state;

import net.minecraft.util.math.Direction;

public interface SimpleJoinState {

    boolean isJoined(Direction face);

    int getIndex();

}