package eu.virtusdevelops.advancedcrops.api.item

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition

interface StoragableItem {

    fun getPosition(): ChunkPosition
}