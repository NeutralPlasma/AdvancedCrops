package eu.virtusdevelops.advancedcrops.api.events

import eu.virtusdevelops.advancedcrops.api.crop.Crop

interface CropBreakEvent {


    fun crop(): Crop

}