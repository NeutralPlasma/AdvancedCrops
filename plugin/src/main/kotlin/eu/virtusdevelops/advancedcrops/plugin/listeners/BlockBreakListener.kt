package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.virtuscore.crop.CropUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener(
    private val cropStorage: CropStorage
) : Listener {




    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent){
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










