package eu.virtusdevelops.advancedcrops.plugin

import eu.virtusdevelops.advancedcrops.api.AdvancedCropsApi
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.api.hoe.HoeStorage
import eu.virtusdevelops.advancedcrops.core.storage.SQLStorage
import eu.virtusdevelops.virtuscore.VirtusCore
import eu.virtusdevelops.virtuscore.compatibility.ServerVersion
import eu.virtusdevelops.virtuscore.item.ItemUtils
import org.bukkit.plugin.java.JavaPlugin

class AdvancedCrops : JavaPlugin(), AdvancedCropsApi {

    lateinit var storage: SQLStorage

    override fun onDisable() {
        logger.info("Successfully disabled AdvancedCrops!")



    }

    override fun onEnable() {
        saveDefaultConfig()

        AdvancedCropsApi.load(this)
        logger.info("VirtusCore version: ${VirtusCore.getCoreVersion()}")
        logger.info("Server version: ${ServerVersion.getServerVersion().name}")

        storage = SQLStorage(this, this.logger)
    }



    override fun getCropStorage(): CropStorage {
        TODO("Not yet implemented")
    }


    override fun getHoeStorage(): HoeStorage {
        TODO("Not yet implemented")
    }

}