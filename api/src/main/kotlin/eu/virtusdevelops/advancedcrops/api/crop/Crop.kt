package eu.virtusdevelops.advancedcrops.api.crop

import java.util.UUID

data class Crop(
    val id: UUID,
    val configurationName: String,
    val location: CropPosition,
    val boneMeal: Int,
    val growthStage: Int,
    val humidity: Int,
    val fertilizer: Int,

    val placedBy: UUID,
    val placeTime: Long,
    val updateTime: Long
) {



}