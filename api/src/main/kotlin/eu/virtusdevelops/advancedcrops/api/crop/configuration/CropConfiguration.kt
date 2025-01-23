package eu.virtusdevelops.advancedcrops.api.crop.configuration

import eu.virtusdevelops.advancedcrops.api.crop.CropType
import eu.virtusdevelops.advancedcrops.api.item.CustomItem
import org.bukkit.Location

/**
 * Represents the base configuration for a crop in the crop management system.
 *
 * CropConfiguration serves as an abstract blueprint for specific crop types and their behaviors.
 * It defines the essential attributes for crop configurations, including the crop's name, type,
 * and the seed item used for planting. Implementations of this class define additional behavior
 * such as how crops produce drops at specific locations.
 *
 * @property name The name of the crop instance.
 * @property type The type of the crop (e.g., ITEM or BLOCK).
 * @property seedItem The custom item used as the seed for planting the crop.
 */
abstract class CropConfiguration(
    val name: String,
    val type: CropType,
    val seedItem: CustomItem
) {
    /**
     * Process the drops that the crop produces based on its location.
     *
     * @param location The world location of the crop whose drops are being processed.
     */
    abstract fun processDrops(location: Location)


    /**
     * Drops the seed item associated with the crop configuration at the specified location naturally
     * within the Minecraft world.
     *
     * @param location The location in the world where the seed item will be dropped. This includes
     * a reference to the world and the specific coordinates for the drop.
     */
    fun dropSeed(location: Location) {
        location.world.dropItemNaturally(location, seedItem.getItem())
    }
}




