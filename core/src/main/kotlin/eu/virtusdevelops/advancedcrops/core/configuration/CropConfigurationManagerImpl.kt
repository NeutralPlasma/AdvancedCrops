package eu.virtusdevelops.advancedcrops.core.configuration

import eu.virtusdevelops.advancedcrops.api.CropConfigurationManager
import eu.virtusdevelops.advancedcrops.api.crop.CropType
import eu.virtusdevelops.advancedcrops.api.crop.configuration.CropConfiguration
import eu.virtusdevelops.advancedcrops.api.crop.configuration.ItemCropConfiguration
import eu.virtusdevelops.advancedcrops.core.item.BasicItem
import org.bukkit.plugin.java.JavaPlugin

class CropConfigurationManagerImpl (private val plugin: JavaPlugin) : CropConfigurationManager {

    private val cropConfigurations: MutableMap<String, CropConfiguration> = hashMapOf()

    init {


        // temporary
        cropConfigurations["diamondCrop"] = ItemCropConfiguration(
            "diamondCrop",
            CropType.ITEM,
            BasicItem(),
            listOf(
                BasicItem()
            )
        )
    }


    override fun getCropConfiguration(name: String): CropConfiguration? {
        if(cropConfigurations.containsKey(name)) return cropConfigurations[name]

        plugin.logger.warning("Crop configuration for $name does not exist!")
        return null
    }

    override fun addCropConfiguration(name: String, cropConfiguration: CropConfiguration) {
        TODO("Not yet implemented")
    }

    override fun removeCropConfiguration(name: String): Boolean {
        TODO("Not yet implemented")
    }
}