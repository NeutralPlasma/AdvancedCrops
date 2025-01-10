package eu.virtusdevelops.advancedcrops.plugin

import eu.virtusdevelops.advancedcrops.api.AdvancedCropsApi
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.api.hoe.HoeStorage
import eu.virtusdevelops.advancedcrops.core.crop.CropStorageImpl
import eu.virtusdevelops.advancedcrops.core.hoe.HoeStorageImpl
import eu.virtusdevelops.advancedcrops.core.storage.AsyncExecutor
import eu.virtusdevelops.advancedcrops.core.storage.SQLStorage
import eu.virtusdevelops.virtuscore.VirtusCore
import eu.virtusdevelops.virtuscore.compatibility.ServerVersion
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.Executors

class AdvancedCrops : JavaPlugin(), AdvancedCropsApi {

    private lateinit var storage: SQLStorage
    private lateinit var cropStorage: CropStorage
    private lateinit var hoeStorage: HoeStorage

    override fun onDisable() {
        logger.info("Successfully disabled AdvancedCrops!")



    }


    override fun onEnable() {
        saveDefaultConfig()

        AdvancedCropsApi.load(this)
        logger.info("VirtusCore version: ${VirtusCore.getCoreVersion()}")
        logger.info("Server version: ${ServerVersion.getServerVersion().name}")

        storage = SQLStorage(this, this.logger)

        cropStorage = CropStorageImpl()
        hoeStorage = HoeStorageImpl()
    }



    override fun getCropStorage(): CropStorage {
        return cropStorage
    }


    override fun getHoeStorage(): HoeStorage {
        return hoeStorage
    }

}