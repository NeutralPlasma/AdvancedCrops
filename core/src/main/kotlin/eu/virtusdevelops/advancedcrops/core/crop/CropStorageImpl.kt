package eu.virtusdevelops.advancedcrops.core.crop

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkCoordinates
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.core.dao.CropDao
import eu.virtusdevelops.advancedcrops.core.storage.AsyncExecutor
import org.bukkit.Location
import java.util.logging.Logger
import kotlin.math.log

class CropStorageImpl(private val cropDao: CropDao, private val logger: Logger) : CropStorage {


    // hash map with chunks that then have hash map of crops?


    private var crops: HashMap<String, HashMap<ChunkCoordinates, HashMap<ChunkPosition, Crop>>>  = HashMap()



    override fun getCrop(location: org.bukkit.Location): Crop? {

        val cropPosition = CropPosition.fromLocation(location)
        val chunkCoordinates = cropPosition.chunkCoordinates
        val world = location.world.name

        if(crops.containsKey(world)) {
            if (crops[world]?.containsKey(chunkCoordinates) == true) {
                crops[world]?.get(chunkCoordinates)?.let {
                    return it[cropPosition.chunkPos]
                }
            }
        }
        // if the chunk for some reason isn't yet loaded
        AsyncExecutor.executor.submit {
            // load the chunk from storage
            loadChunk(location)
        }
        return null
    }

    override fun storeCrop(crop: Crop) {
        val location = crop.location

        if(!crops.containsKey(location.worldName)){
            crops[location.worldName] = HashMap()
        }

        if(!(crops[location.worldName]?.containsKey(location.chunkCoordinates))!!){
            crops[location.worldName]?.put(location.chunkCoordinates, HashMap())
        }

        // now insert the crop
        crops[location.worldName]?.get(location.chunkCoordinates)?.put(location.chunkPos, crop)

        logger.info("Crop stored: $crop")
    }

    override fun removeCrop(crop: Crop) {
        // remove from crops
        crops[crop.location.worldName]?.get(crop.location.chunkCoordinates)?.remove(crop.location.chunkPos)
        logger.info("Removed crop: $crop")
    }




    override fun loadChunk(location: Location) {
        loadChunk(location.chunk.x, location.chunk.z, location.world?.name!!)
    }

    override fun loadChunk(x: Int, z: Int, world: String) {
        AsyncExecutor.executor.submit {
            val chunkCoordinates = ChunkCoordinates(x, z)
            val data = cropDao.getCropsInChunk(x, z, world)
            val cropsNew = HashMap<ChunkPosition, Crop>()

            for (crop in data) {
                cropsNew[crop.location.chunkPos] = crop
            }

            if(crops.containsKey(world))
                crops[world]?.put(chunkCoordinates, cropsNew)
            else crops[world] = HashMap()

            crops[world]?.set(chunkCoordinates, cropsNew)

            //logger.info("Chunk loaded: $chunkCoordinates")

        }
    }

    override fun unloadChunk(location: Location) {
        unloadChunk(location.chunk.x, location.chunk.z, location.world?.name!!)
    }

    override fun unloadChunk(x: Int, z: Int, world: String) {
        AsyncExecutor.executor.submit {

            // save data to database then unload
            val toSave = crops[world]?.get(ChunkCoordinates(x, z))

            for (crop in toSave?.values!!) {
                cropDao.save(crop)
            }



            if(crops.containsKey(world)) {
                crops[world]?.remove(ChunkCoordinates(x, z))
            }
        }
    }


    override fun getCrop(position: CropPosition): Crop? {
        if(crops.containsKey(position.world?.name)) {
            if(crops[position.world?.name]?.containsKey(position.chunkCoordinates) == true) {
                crops[position.world?.name]?.get(position.chunkCoordinates)?.let {
                    return it[position.chunkPos]
                }
            }
        }
        return null;
    }


    override fun saveAll() {
        logger.info("Saving crops...")
        for (world in crops.keys) {
            logger.info("Saving world: $world")
            for (chunk in crops[world]?.keys!!) {
                val toSave = crops[world]?.get(chunk)
                for (crop in toSave?.values!!) {
                    cropDao.save(crop)
                    logger.info("Saving crop: $crop")
                }
            }
        }
    }

}