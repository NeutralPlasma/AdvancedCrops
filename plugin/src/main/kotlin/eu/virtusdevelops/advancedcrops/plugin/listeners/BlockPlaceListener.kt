package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.plugin.util.NBTUtil
import eu.virtusdevelops.virtuscore.chat.TextUtils
import eu.virtusdevelops.virtuscore.crop.CropUtils
import eu.virtusdevelops.virtuscore.item.ItemUtils
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.persistence.PersistentDataType
import java.util.*
import java.util.logging.Logger

class BlockPlaceListener(private val cropManager: CropManager, private val logger: Logger) : Listener {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val cropLocation = CropPosition.fromLocation(event.block.location)
        val item = event.itemInHand
        val block = event.block


        if(CropUtils.isCrop(block.getRelative(0,1,0))
            || CropUtils.isCrop(block.getRelative(0, -1, 0))){

            val base = CropUtils.getBaseBlock(block)
            cropManager.getCrop(CropPosition.fromLocation(base.location))?.let {
                if(!player.hasPermission("advancedcrops.place.bypass")) {
                    event.isCancelled = true
                }
                return
            }
        }

        val cropID = NBTUtil.getNBTTag(item, NBTUtil.CROP_KEY, PersistentDataType.STRING)

        if(cropID != null){

            val crop = Crop(
                UUID.randomUUID(),
                cropID,
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
}