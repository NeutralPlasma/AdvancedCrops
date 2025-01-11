package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.virtuscore.chat.TextUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class BlockInteractListener() : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerClick(event: PlayerInteractEvent){
        val location = event.player.location

        val xChunk = location.blockX.shr(4)
        val zChunk = location.blockZ.shr(4)

        val cropLoc = CropPosition.fromLocation(location)

        event.player.sendMessage(TextUtils.parse(
        """
            <gray>---------------</gray>
            <dark_gray>Clicked: <yellow> ${location.chunk.x}<gray>, <yellow>${location.chunk.z}
            <dark_gray>Calculated: <yellow> ${cropLoc.chunkCoordinates.x}<gray>, <yellow>${cropLoc.chunkCoordinates.z}
        """.trimIndent()));
    }

}