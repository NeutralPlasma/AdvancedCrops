package eu.virtusdevelops.advancedcrops.core.dao

import eu.virtusdevelops.advancedcrops.api.crop.Crop
import java.util.UUID

interface CropDao : DaoCrud<Crop, UUID> {

    fun getCropsByPlayer(playerUUID: UUID): List<Crop>

    fun getCropsInChunk(chunkX: Int, chunkZ: Int, world: String): List<Crop>

}