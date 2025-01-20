package eu.virtusdevelops.advancedcrops.api.crop

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.item.StorableItem
import java.util.UUID

data class Crop(
    val id: UUID,
    val configurationName: String,
    val location: CropPosition,
    var boneMeal: Int,
    var growthStage: Int,
    var humidity: Int,
    var fertilizer: Int,

    val placedBy: UUID,
    val placeTime: Long,
    var updateTime: Long,
    var changed: Boolean = false,
) : StorableItem{
    override fun getPosition(): ChunkPosition {
        return location.chunkPos
    }
}