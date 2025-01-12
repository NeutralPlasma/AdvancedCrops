package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.virtuscore.chat.TextUtils
import eu.virtusdevelops.virtuscore.crop.CropUtils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener(
    private val cropStorage: CropStorage,
    private val cropManager: CropManager
) : Listener {




    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent){
        // debug:
        val cropPosition = CropPosition.fromLocation(event.block.location)

        val test = cropStorage.getCrop(cropPosition)

        if(test != null){
            event.isCancelled = true


            if(cropManager.deleteCrop(test)){

                event.block.type = if (event.block.type == Material.DIAMOND_BLOCK) Material.COAL_BLOCK else Material.DIAMOND_BLOCK
                event.player.sendMessage(TextUtils.parse("<green>Broke a crop!"))

            }else{
                event.player.sendMessage(TextUtils.parse("<red>Failed breaking!"))
            }
            return
        }



        // handle grass / shortgrass?

        var cropBlock = event.block
        if(!CropUtils.isCrop(cropBlock)){
            val blockAbove = cropBlock.getRelative(0,1,0)
            if(!CropUtils.isCrop(blockAbove)) {
                return
            }
            cropBlock = blockAbove
        }

        val baseBlock = CropUtils.getBaseBlock(cropBlock) ?: return
        val crop = cropStorage.getCrop(baseBlock.location) ?: return

        event.isCancelled = true // cancel default breaking
        // do the crop processing
    }

}










