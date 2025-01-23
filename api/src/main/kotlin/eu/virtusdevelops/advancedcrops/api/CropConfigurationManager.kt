package eu.virtusdevelops.advancedcrops.api

import eu.virtusdevelops.advancedcrops.api.crop.configuration.CropConfiguration

interface CropConfigurationManager {


    fun getCropConfiguration(name: String): CropConfiguration?


    fun addCropConfiguration(name: String, cropConfiguration: CropConfiguration)


    fun removeCropConfiguration(name: String): Boolean

}