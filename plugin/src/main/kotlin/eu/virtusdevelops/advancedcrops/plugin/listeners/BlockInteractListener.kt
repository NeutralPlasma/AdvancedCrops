package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.virtuscore.chat.TextUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class BlockInteractListener(private val cropManager: CropManager) : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerClick(event: PlayerInteractEvent){

        if(event.action == Action.LEFT_CLICK_BLOCK) return
        if(event.hand != EquipmentSlot.HAND) return
        if(event.clickedBlock == null) return

        val location = event.clickedBlock!!.location

        val cropLoc = CropPosition.fromLocation(location)

        event.player.sendMessage(TextUtils.parse(
        """
            <gray>---------------</gray>
            <dark_gray>Clicked: <yellow> ${location.chunk.x}<gray>, <yellow>${location.chunk.z}
            <dark_gray>Calculated: <yellow> ${cropLoc.chunkCoordinates.x}<gray>, <yellow>${cropLoc.chunkCoordinates.z}
        """.trimIndent()));


        val crop = cropManager.getCrop(cropLoc)



        if(crop != null){

            event.player.sendMessage(TextUtils.parse(
                """
                <lime>Crop: <yellow> ${crop.id}
                <gray>humdidity: <yellow> ${crop.humidity}
                <gray>bonemeal: <yellow> ${crop.boneMeal}
            """.trimIndent()
            ))


            crop.changed = true
            crop.humidity = 100
            crop.boneMeal = 50


            event.player.sendMessage(TextUtils.parse(
                """
            <gray>Updated crop status
        """.trimIndent()))

        }



    }

}