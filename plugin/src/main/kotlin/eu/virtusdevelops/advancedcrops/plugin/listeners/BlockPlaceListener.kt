package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.virtuscore.chat.TextUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import java.util.*
import java.util.logging.Logger

class BlockPlaceListener(private val cropManager: CropManager, private val logger: Logger) : Listener {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockPlace(event: BlockPlaceEvent) {
        val crop = Crop(
            UUID.randomUUID(),
            "test",
            CropPosition.fromLocation(event.block.location),
            0,
            0,
            0,
            0,
            event.player.uniqueId,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        )


        cropManager.addCrop(
            crop
        )

        event.player.sendMessage(TextUtils.parse("<green>Crop added!"))
    }
}