package eu.virtusdevelops.advancedcrops.core.crop

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkCoordinates
import eu.virtusdevelops.advancedcrops.core.storage.ChunkData
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.core.dao.CropDao
import eu.virtusdevelops.advancedcrops.core.storage.AsyncExecutor
import org.bukkit.Location
import java.util.logging.Logger

class CropStorageImpl(private val cropDao: CropDao, private val logger: Logger) : CropStorage {


    // hash map with chunks that then have hash map of crops?


    private var chunkData: HashMap<String, HashMap<ChunkCoordinates, ChunkData<Crop>>> = HashMap()

    //private var crops: HashMap<String, HashMap<ChunkCoordinates, HashMap<ChunkPosition, Crop>>>  = HashMap()



    override fun getCrop(location: Location): Crop? {

        val cropPosition = CropPosition.fromLocation(location)
        val chunkCoordinates = cropPosition.chunkCoordinates
        val world = location.world.name

        if(chunkData.containsKey(world)){
            if(chunkData[world]!!.containsKey(chunkCoordinates)){
                return chunkData[world]!!.get(chunkCoordinates)?.getItem(cropPosition.chunkPos)

            }
        }

        /*if(crops.containsKey(world)) {
            if (crops[world]?.containsKey(chunkCoordinates) == true) {
                crops[world]?.get(chunkCoordinates)?.let {
                    return it[cropPosition.chunkPos]
                }
            }
        }*/
        // if the chunk for some reason isn't yet loaded
        AsyncExecutor.executor.submit {
            // load the chunk from storage
            loadChunk(location)
        }
        return null
    }

    override fun storeCrop(crop: Crop): Boolean {
        val location = crop.location

        if(!chunkData.containsKey(location.worldName)){
            chunkData[location.worldName] = HashMap()
        }

        if(!(chunkData[location.worldName]?.containsKey(location.chunkCoordinates))!!){
            chunkData[location.worldName]?.put(location.chunkCoordinates, ChunkData(location.chunkCoordinates))
        }

        // now insert the crop
        chunkData[location.worldName]?.get(location.chunkCoordinates)?.addItem(crop)

        logger.info("Crop stored: $crop")
        return true
    }

    override fun removeCrop(crop: Crop): Boolean {
        // remove from crops

        if(chunkData[crop.location.worldName] != null) {

            if(chunkData[crop.location.worldName]!!.containsKey(crop.location.chunkCoordinates)){
                if(chunkData[crop.location.worldName]!!.get(crop.location.chunkCoordinates)?.removeItem(crop) == true)
                    return true
            }
            return false
        }
        return false
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

            val newChunkData = ChunkData(chunkCoordinates, cropsNew)

            if(!chunkData.containsKey(world)){
                chunkData[world] = HashMap()
            }

            chunkData[world]!!.set(chunkCoordinates, newChunkData)

            //logger.info("Chunk loaded: $chunkCoordinates")

        }
    }

    override fun unloadChunk(location: Location) {
        unloadChunk(location.chunk.x, location.chunk.z, location.world?.name!!)
    }

    override fun unloadChunk(x: Int, z: Int, world: String) {

        val removedChunk = chunkData[world]?.remove(ChunkCoordinates(x, z)) ?: return

        AsyncExecutor.executor.submit {
            // first process removed
            for(crop in removedChunk.removedItems.values) {
                cropDao.delete(crop.id)
            }

            // process added and existing items
            for(crop in removedChunk.addedItems.values) {
                cropDao.save(crop)
            }

            // update any crops that were updated
            for(crop in removedChunk.items.values) {
                if(crop.changed)
                    cropDao.save(crop)
            }
        }
    }



    override fun getCrop(position: CropPosition): Crop? {

        if(!chunkData.containsKey(position.worldName))
            return null


        if(!chunkData[position.worldName]!!.containsKey(position.chunkCoordinates))
            return null


        return chunkData[position.worldName]?.get(position.chunkCoordinates)?.getItem(position.chunkPos)

    }


    override fun saveAll() {

        //
        for (world in chunkData.keys) {

            for (chunk in chunkData[world]!!.values) {


                for (crop in chunk.removedItems.values) {
                    cropDao.delete(crop.id)
                }

                // process added and existing items
                for (crop in chunk.addedItems.values) {
                    cropDao.save(crop)
                }

                // update any crops that were updated
                for (crop in chunk.items.values) {
                    if (crop.changed)
                        cropDao.save(crop)
                }


            }
        }
    }
}