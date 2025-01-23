package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.plugin.util.NBTUtil
import eu.virtusdevelops.virtuscore.chat.TextUtils
import eu.virtusdevelops.virtuscore.crop.CropUtils
import eu.virtusdevelops.virtuscore.item.ItemUtils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class BlockBreakListener(
    private val cropStorage: CropStorage,
    private val cropManager: CropManager
) : Listener {




    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent){

        // handle grass / shortgrass?

        // temp
        if(event.block.type == Material.DIAMOND_BLOCK) {
            var item = ItemStack(Material.WHEAT_SEEDS)
            item = ItemUtils.rename(item, "<green>Diamond Seeds")
            NBTUtil.setNBTTag(item, NBTUtil.CROP_KEY, PersistentDataType.STRING, "diamondCrop")

            ItemUtils.give(event.player, item)

            return
        }


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

        if(cropManager.processCropBreak(crop, event.block, event.player)){
            event.player.sendMessage(TextUtils.parse("<green>Broke a crop!"))
        }

        return

    }

}










