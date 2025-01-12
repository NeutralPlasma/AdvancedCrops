package eu.virtusdevelops.advancedcrops.core.crop

import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropConfiguration
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage

class CropManagerImpl(private val cropStorage: CropStorage) : CropManager {


    override fun getCrop(position: CropPosition): Crop? {
        TODO("Not yet implemented")
    }

    override fun getCropConfiguration(crop: Crop): CropConfiguration? {
        TODO("Not yet implemented")
    }

    override fun getCropsInChunk(chunkX: Int, chunkZ: Int): Map<ChunkPosition, Crop> {
        TODO("Not yet implemented")
    }

    override fun addCrop(crop: Crop): Boolean {
        return cropStorage.storeCrop(crop)
    }

    override fun deleteCrop(crop: Crop): Boolean {
        return cropStorage.removeCrop(crop)
    }

    override fun loadCrops() {
        TODO("Not yet implemented")
    }

    override fun saveCrops() {

        cropStorage.saveAll()

    }

    override fun reloadCrops() {
        TODO("Not yet implemented")
    }


}