package at.martinthedragon.nucleartech.api

import at.martinthedragon.nucleartech.api.world.ChunkRadiationHandler

public interface NuclearTechRuntime {
    public fun getChunkRadiationHandler(): ChunkRadiationHandler
}
