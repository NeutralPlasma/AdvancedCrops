package eu.virtusdevelops.advancedcrops.api.item

interface DropItem {

    fun getDropItem(): CustomItem
    fun getChance(): Double
    fun getAdjustedChance(): Double
    fun setAdjustedChance(chance: Double)
}