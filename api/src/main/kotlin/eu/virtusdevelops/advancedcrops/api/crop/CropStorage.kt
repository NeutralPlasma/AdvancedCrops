package eu.virtusdevelops.advancedcrops.api.crop

import org.bukkit.Location

/**
 * Interface defining a storage mechanism for crops in a Minecraft plugin environment. The CropStorage
 * interface provides methods to retrieve, store, and manage crop data, as well as loading and unloading
 * crop-related resources for specific chunks in the world.
 *
 * You shouldn't manage crops directly through this class
 * You should use [eu.virtusdevelops.advancedcrops.api.CropManager]
 */
interface CropStorage {


    /**
     * Retrieves a crop from the storage system located at the given world location.
     *
     * @param location The location in the world where the crop is expected to be found.
     * @return The Crop instance at the specified location, or null if no crop is present.
     */
    fun getCrop(location: Location) : Crop?

    /**
     * Retrieves a crop from the storage system using the given crop position.
     *
     * @param position The position of the crop within the chunk and world, specified by a CropPosition object.
     * @return The Crop instance at the given position, or null if no crop is found at the specified position.
     */
    fun getCrop(position: CropPosition) : Crop?

    /**
     * Stores the provided crop data into the storage system.
     *
     * @param crop The crop instance to be stored.
     */
    fun storeCrop(crop: Crop)

    /**
     * Asynchronously loads and initializes all crop data within a specific chunk.
     *
     * @param location The location within the chunk to load. This determines which chunk's crops are
     *                 initialized and ready for operations.
     */
    fun loadChunk(location: Location)

    /**
     * Unloads crop data within a specific chunk, releasing its resources and saving any changes if necessary.
     *
     * @param location The location within the chunk to unload. This determines which chunk's crops are
     *                 de-initialized and their resources freed.
     */
    fun unloadChunk(location: Location)
}