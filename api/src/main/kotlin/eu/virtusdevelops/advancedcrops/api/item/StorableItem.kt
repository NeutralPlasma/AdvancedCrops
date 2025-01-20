package eu.virtusdevelops.advancedcrops.api.item

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition

interface StorableItem {

    fun getPosition(): ChunkPosition
}