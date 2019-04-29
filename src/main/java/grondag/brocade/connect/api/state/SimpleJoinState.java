/*******************************************************************************
 * Copyright 2019 grondag
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.brocade.connect.api.state;

import static org.apiguardian.api.API.Status.STABLE;

import org.apiguardian.api.API;

import grondag.brocade.connect.api.world.BlockNeighbors;
import grondag.brocade.connect.impl.SimpleJoinStateImpl;
import net.minecraft.util.math.Direction;

/**
 * Describes the state of a block model with connected textures/shapes that
 * depend on only on six directly adjacent neighbors.<p>
 */
@API(status = STABLE)
public interface SimpleJoinState {

    boolean isJoined(Direction face);

    int ordinal();

    public static SimpleJoinState fromOrdinal(int ordinal) {
        return SimpleJoinStateImpl.fromOrdinal(ordinal);
    }
    
    public static SimpleJoinState fromWorld(BlockNeighbors neighbors) {
        return SimpleJoinStateImpl.fromWorld(neighbors);
    }
    
    public static int ordinalFromWorld(BlockNeighbors neighbors) {
        return SimpleJoinStateImpl.ordinalFromWorld(neighbors);
    }
}
