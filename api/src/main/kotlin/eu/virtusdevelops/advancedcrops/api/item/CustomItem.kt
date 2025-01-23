package eu.virtusdevelops.advancedcrops.api.item

import org.bukkit.inventory.ItemStack
import java.util.UUID

interface CustomItem{

    fun getId(): UUID

    fun getItem(): ItemStack

}