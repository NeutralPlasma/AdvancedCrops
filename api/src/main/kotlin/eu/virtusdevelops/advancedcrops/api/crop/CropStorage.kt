package eu.virtusdevelops.advancedcrops.api.crop

import eu.virtusdevelops.advancedcrops.shared.CropPosition
import org.bukkit.Location

interface CropStorage {


    fun getCrop(location: Location) : Crop?

    fun getCrop(position: CropPosition) : Crop?

    fun storeCrop(crop: Crop)

}