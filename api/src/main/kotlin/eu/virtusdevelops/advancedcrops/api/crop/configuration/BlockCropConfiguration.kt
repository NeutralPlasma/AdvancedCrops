package eu.virtusdevelops.advancedcrops.api.crop.configuration

import eu.virtusdevelops.advancedcrops.api.crop.CropType
import eu.virtusdevelops.advancedcrops.api.item.CustomItem
import org.bukkit.Location
import org.bukkit.Material

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
        location.block.type = getRandomBlock()
    }


    private fun getRandomBlock(): Material {
        TODO("Not yet implemented")
    }

}
