package eu.virtusdevelops.advancedcrops.core.crop

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkCoordinates
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.core.dao.CropDao
import eu.virtusdevelops.advancedcrops.core.storage.AsyncExecutor
import org.bukkit.Location

class CropStorageImpl(private val cropDao: CropDao) : CropStorage {


    // hash map with chunks that then have hash map of crops?


    private var crops: HashMap<ChunkCoordinates, HashMap<ChunkPosition, Crop>> = HashMap()



    override fun getCrop(location: org.bukkit.Location): Crop? {

        val cropPosition = CropPosition.fromLocation(location)
        val chunkCoordinates = cropPosition.chunkCoordinates

        if(crops.containsKey(chunkCoordinates)) return crops[chunkCoordinates]?.get(cropPosition.chunkPos)
        // if the chunk for some reason isn't yet loaded
        AsyncExecutor.executor.submit {
            // load the chunk from storage
            loadChunk(location)
        }
        return null
    }

    override fun storeCrop(crop: Crop) {
        TODO("Not yet implemented")
    }

    override fun loadChunk(location: Location) {

        val cropPosition = CropPosition.fromLocation(location)
        val data = cropDao.getCropsInChunk(location.chunk.x, location.chunk.z)

        val cropsNew = HashMap<ChunkPosition, Crop>()

        for (crop in data) {
            // do the thing
            // get crop cords and add to hashmap

        }

        crops[cropPosition.chunkCoordinates] = cropsNew

    }

    override fun unloadChunk(location: Location) {
        TODO("Not yet implemented")
    }

    override fun getCrop(position: CropPosition): Crop? {
        TODO("Not yet implemented")
    }


}