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

package grondag.brocade.connect.api.world;

import grondag.brocade.connect.api.model.BlockEdge;

import static org.apiguardian.api.API.Status.STABLE;

import org.apiguardian.api.API;

import grondag.brocade.connect.api.model.BlockCorner;
import grondag.brocade.connect.api.model.HorizontalCorner;
import grondag.brocade.connect.api.model.HorizontalFace;
import grondag.brocade.connect.impl.NeighborBlocksImpl;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

@API(status = STABLE)
public interface BlockNeighbors {
    void release();
    
    BlockNeighbors withTest(BlockTest blockTest);
    
    BlockState blockState();
    
    BlockState blockState(Direction face);

    BlockState blockState(BlockEdge corner);

    BlockState blockState(BlockCorner corner);
    
    default BlockState blockState(HorizontalFace face) {
        return blockState(face.face);
    }

    default BlockState blockState(Direction face1, Direction face2) {
        return blockState(BlockEdge.find(face1, face2));
    }

    default BlockState blockState(HorizontalCorner corner) {
        return blockState(corner.face1.face, corner.face2.face);
    }
    
    default BlockState blockState(Direction face1, Direction face2, Direction face3) {
        return blockState(BlockCorner.find(face1, face2, face3));
    }

    Object modelState();
    
    Object modelState(Direction face);

    Object modelState(BlockEdge corner);

    Object modelState(BlockCorner corner);

    default Object modelState(Direction face1, Direction face2, Direction face3) {
        return modelState(BlockCorner.find(face1, face2, face3));
    }

    default Object modelState(HorizontalFace face) {
        return modelState(face.face);
    }

    default Object modelState(Direction face1, Direction face2) {
        return modelState(BlockEdge.find(face1, face2));
    }

    default Object modelState(HorizontalCorner corner) {
        return modelState(corner.face1.face, corner.face2.face);
    }
    
    /** use this to override world results */
    void override(Direction face, boolean override);
    
    boolean result(Direction face);

    boolean result(BlockEdge corner);

    boolean result(BlockCorner corner);
    
    public default boolean result(Direction face1, Direction face2) {
        return result(BlockEdge.find(face1, face2));
    }
    
    public default boolean result(HorizontalFace face) {
        return result(face.face);
    }

    public default boolean result(HorizontalCorner corner) {
        return result(corner.face1.face, corner.face2.face);
    }

    public default boolean result(Direction face1, Direction face2, Direction face3) {
        return result(BlockCorner.find(face1, face2, face3));
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, stateFunc, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc) {
        return threadLocal(world, x, y, z, stateFunc, null);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z, BlockTest test) {
        return threadLocal(world, x, y, z, null, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z) {
        return threadLocal(world, x, y, z, null, null);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos, ModelStateFunction stateFunc, BlockTest test) {
        return threadLocal(world, pos.getX(), pos.getY(), pos.getZ(), stateFunc, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos, ModelStateFunction stateFunc) {
        return threadLocal(world, pos, stateFunc, null);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos, BlockTest test) {
        return threadLocal(world, pos, null, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos) {
        return threadLocal(world, pos, null, null);
    }
}
