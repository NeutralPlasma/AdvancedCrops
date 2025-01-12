package eu.virtusdevelops.advancedcrops.api

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropConfiguration
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition

public interface CropManager {

    /**
     * Retrieves a crop based on the specified position within the world and chunk.
     *
     * @param position The position of the crop specified by a CropPosition object.
     * @return The Crop instance at the specified position, or null if no crop exists at the given position.
     */
    fun getCrop(position: CropPosition): Crop?


    /**
     * Retrieves the configuration settings associated with the specified crop.
     *
     * @param crop The crop whose configuration is being retrieved.
     * @return The CropConfiguration object associated with the given crop, or null if no configuration exists.
     */
    fun getCropConfiguration(crop: Crop): CropConfiguration?


    /**
     * Retrieves a map of crops located within the specified chunk.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     * @return A map where each key represents a ChunkPosition within the chunk and its associated value
     *         is the Crop located at that position.
     */
    fun getCropsInChunk(chunkX: Int, chunkZ: Int): Map<ChunkPosition, Crop>

    /**
     * Adds a Crop instance to the crop management system.
     *
     * @param crop The Crop instance to be added, which contains details such as position, growth stage,
     *             and metadata required for managing the crop.
     */
    fun addCrop(crop: Crop)



    fun deleteCrop(crop: Crop)

    /**
     * Loads all crop data into the crop management system.
     *
     * This function is responsible for initializing and retrieving crop data
     * from relevant data sources or persistent storage and making it available
     * for use in the crop management system. It ensures that the system is prepared
     * to manage and operate on crop instances during runtime.
     *
     * Usage of this method typically occurs during the initialization phase
     * or when a full reload of crop data is required.
     */
    fun loadCrops()

    /**
     * Persists all crop data managed by the crop management system.
     *
     * This method is responsible for saving the state of all crops, ensuring that their
     * information is retained for future use. It writes crop data to the appropriate
     * storage or persistence layer, allowing it to be reloaded later. This is typically
     * called during server shutdown, manual saves, or other significant lifecycle events
     * where data integrity is critical. Proper implementation prevents data loss
     * and ensures consistent crop management across sessions.
     */
    fun saveCrops()

    /**
     * Reloads all crop data managed by the system.
     *
     * This method is used to refresh the internal state of the crop management system by unloading
     * all current crop data and reloading it from the relevant data sources. It is typically called
     * when an external event necessitates the reinitialization of crops, ensuring that the system
     * operates on the latest and most accurate data.
     *
     * It invokes the unloading of previously managed crops, clears associated resources, and
     * reloads crop configurations along with their current states from persistent storage or
     * data sources.
     *
     * Calling this method ensures synchronization between the crop management system and its
     * underlying data sources.
     */
    fun reloadCrops()


}