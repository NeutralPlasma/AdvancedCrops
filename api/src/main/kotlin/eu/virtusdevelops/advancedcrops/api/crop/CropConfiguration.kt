package eu.virtusdevelops.advancedcrops.api.crop

import eu.virtusdevelops.advancedcrops.api.item.CustomItem
import org.bukkit.Location

/*
basic:
- Name
- type
- seed item


item type:
- item drop list


block type (pumpkins and melons):
- block spawn list


 */


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
}

/**
 * Represents the configuration for a block crop type in a crop management system.
 *
 * A block crop produces a specific block upon maturity and drop processing. The configuration
 * specifies the name of the crop, its type, the associated seed item, and the list of blocks
 * that can be produced as drops when the crop grows or is harvested.
 *
 * This implementation of `CropConfiguration` is specifically designed for crop configurations where
 * blocks, such as pumpkins or melons, are spawned in the world instead of dropping items.
 *
 * @constructor Initializes a `BlockCropConfiguration` instance with the specified attributes.
 * @param name The name of the crop.
 * @param type The type of the crop, which must correspond to `BLOCK`.
 * @param seedItem The custom seed item used for planting the crop.
 * @param blockDropList A list of block identifiers that this crop can produce during drop processing.
 */
class BlockCropConfiguration(
    name: String,
    type: CropType,
    seedItem: CustomItem,
    val blockDropList: List<String>): CropConfiguration(name, type, seedItem) {

    override fun processDrops(location: Location) {
        // spawn the block?
    }

}


/**
 * Represents a configuration for a special type of crop which yields specific items as drops.
 *
 * This class extends the generic CropConfiguration to provide additional functionality
 * for handling crops that produce custom item drops upon harvesting.
 *
 * @constructor Creates an instance of ItemCropConfiguration.
 * @param name The name of the crop.
 * @param type The type of the crop, either ITEM or BLOCK.
 * @param seedItem The seed item associated with this crop.
 * @param itemDropList The list of custom items that the crop can drop upon harvesting.
 */
class ItemCropConfiguration(
    name: String,
    type: CropType,
    seedItem: CustomItem,
    val itemDropList: List<CustomItem>): CropConfiguration(name, type, seedItem) {

    override fun processDrops(location: Location) {
        TODO("Not yet implemented")
    }
}

