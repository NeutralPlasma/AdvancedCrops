package eu.virtusdevelops.advancedcrops.core.item

import eu.virtusdevelops.advancedcrops.api.item.CustomItem
import eu.virtusdevelops.advancedcrops.api.item.DropItem
import eu.virtusdevelops.virtuscore.item.ItemUtils
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

class BasicItem(
    private val id: UUID = UUID.randomUUID(),
    private val nameTemplate: String = "",
    private var loreTemplate: List<String> = emptyList(),
    material: Material = Material.DIRT
    ) : CustomItem, DropItem {

    private val item = ItemStack(material)

    override fun getId(): UUID {
        return id
    }

    override fun getItem(): ItemStack {
        val tempItem = item.clone()
        if(nameTemplate.isNotBlank()){
            ItemUtils.setName(tempItem, nameTemplate)
        }

        if(loreTemplate.isNotEmpty()){
            ItemUtils.setLore(tempItem, loreTemplate)
        }

        return tempItem
    }

    override fun getDropItem(): CustomItem {
        return this
    }

    override fun getChance(): Double {
        return 100.0
    }

    override fun getAdjustedChance(): Double {
        return 100.0
    }

    override fun setAdjustedChance(chance: Double) {

    }
}