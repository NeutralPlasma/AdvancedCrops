package eu.virtusdevelops.advancedcrops.core.crop

import eu.virtusdevelops.advancedcrops.api.CropConfigurationManager
import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.configuration.CropConfiguration
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.virtuscore.crop.CropUtils
import eu.virtusdevelops.virtuscore.item.ItemUtils
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

class CropManagerImpl(private val cropStorage: CropStorage,
                      private val cropConfigurationManager: CropConfigurationManager) : CropManager {


    override fun getCrop(position: CropPosition): Crop? {
        return cropStorage.getCrop(position)
    }

    override fun getCropConfiguration(crop: Crop): CropConfiguration? {
        TODO("Not yet implemented")
    }

    override fun getCropsInChunk(chunkX: Int, chunkZ: Int): Map<ChunkPosition, Crop> {
        TODO("Not yet implemented")
    }

    override fun addCrop(crop: Crop): Boolean {
        return cropStorage.storeCrop(crop)
    }

    override fun deleteCrop(crop: Crop): Boolean {
        return cropStorage.removeCrop(crop)
    }

    override fun processCropBreak(crop: Crop, block: Block, player: Player): Boolean {
        // check if player can break
        if(!player.hasPermission("advancedcrops.breakcrop")) return false
        // check if crop was planted by the same player if not check break others permission
        if(player.uniqueId != crop.placedBy && !player.hasPermission("advancedcrops.breakcrop.others")) return false

        // get all crop block
        val blocks = CropUtils.getCropBlocks(block)
        val configuration = cropConfigurationManager.getCropConfiguration(crop.configurationName)

        // break each block check growth stage and give rewards for each
        blocks.forEach {
            it.setType(org.bukkit.Material.AIR, false)

            configuration?.processDrops(it.location)

            //it.location.world.dropItemNaturally(it.location, ItemUtils.create(Material.STONE, "", emptyList()))
        }

        // check if block == crop location then remove from storage
        if(crop.location == CropPosition.fromLocation(block.location)){

            cropStorage.removeCrop(crop)
            // drop seed

        }

        return true

    }

    override fun processCropBreak(crop: Crop, block: Block): Boolean {
        TODO("Not yet implemented")
    }

    override fun tickCrop(crop: Crop) {
        TODO("Not yet implemented")
    }


}