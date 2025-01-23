package eu.virtusdevelops.advancedcrops.api.crop.configuration

import eu.virtusdevelops.advancedcrops.api.crop.CropType
import eu.virtusdevelops.advancedcrops.api.item.CustomItem
import eu.virtusdevelops.advancedcrops.api.item.DropItem
import org.bukkit.Location
import kotlin.random.Random

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
    val itemDropList: List<DropItem>): CropConfiguration(name, type, seedItem) {

    private var maxChance = 0.0;

    init {
        var currentChance: Double
        for(dropItem in itemDropList){
            currentChance = maxChance
            maxChance += dropItem.getChance()
            dropItem.setAdjustedChance(currentChance)
        }
    }


    override fun processDrops(location: Location) {
        val item = getRandomItemDrop()
        location.world.dropItemNaturally(location, item.getItem())
    }

    /**
     * Retrieves the list of all item drops associated with the crop configuration.
     *
     * @return A list of [DropItem] objects representing the potential items that this crop can drop.
     */
    fun getAllItemDrops(): List<DropItem> = itemDropList

    /**
     * Selects a random [CustomItem] from the configured list of item drops based on their chances.
     *
     * This function iterates through the list of potential item drops and determines which item
     * should be dropped by comparing a randomly generated value to the adjusted chance of each item.
     * The adjusted chance is pre-calculated during the initialization of the crop configuration.
     * If no item matches the generated random value, the last item in the list is returned as a fallback.
     *
     * @return A [CustomItem] representing the selected item for drop.
     */
    private fun getRandomItemDrop(): CustomItem {
        val random = Random.nextDouble(maxChance)
        var previous = 0.0;
        for(dropItem in itemDropList){
            if(dropItem.getAdjustedChance() <= random && dropItem.getAdjustedChance() > previous){
                return dropItem.getDropItem()
            }
            previous = dropItem.getAdjustedChance()
        }
        return itemDropList.last().getDropItem() // fallback
    }
}
