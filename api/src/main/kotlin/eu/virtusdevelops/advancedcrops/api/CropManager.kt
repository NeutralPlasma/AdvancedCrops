package eu.virtusdevelops.advancedcrops.api

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.configuration.CropConfiguration
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import org.bukkit.block.Block
import org.bukkit.entity.Player

interface CropManager {

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
    fun addCrop(crop: Crop): Boolean



    fun deleteCrop(crop: Crop): Boolean

    /**
     * Processes the breaking of a crop by a player, handling any necessary logic such as
     * updates to the crop's state, rewards, and potential side effects.
     *
     * @param crop The crop being broken, containing its state and metadata.
     * @param block The block that is being broken (affects how multi block crops are broken)
     * @param player The player who is breaking the crop.
     * @return A boolean indicating whether the crop break operation was successfully processed.
     */
    fun processCropBreak(crop: Crop, block: Block, player: Player): Boolean

    /**
     * Processes the event of breaking a crop, applying necessary updates and handling the
     * associated logic.
     *
     * @param crop The crop instance being broken. This includes its location, growth stage, and other metadata.
     * @param block The block that is being broken (affects how multi block crops are broken)
     * @return A boolean indicating whether the crop break event was successfully processed.
     */
    fun processCropBreak(crop: Crop, block: Block): Boolean

    /**
     * Updates the state of the specified crop to simulate the passage of time, handling any growth,
     * hydration, or other mechanics defined for the crop.
     *
     * @param crop The crop instance to be updated. Contains metadata such as growth stage,
     * humidity, fertilizer, and other properties necessary for processing its state.
     */
    fun tickCrop(crop: Crop)
}