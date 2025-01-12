package eu.virtusdevelops.advancedcrops.plugin

import eu.virtusdevelops.advancedcrops.api.AdvancedCropsApi
import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.api.hoe.HoeStorage
import eu.virtusdevelops.advancedcrops.core.crop.CropManagerImpl
import eu.virtusdevelops.advancedcrops.core.crop.CropStorageImpl
import eu.virtusdevelops.advancedcrops.core.hoe.HoeStorageImpl
import eu.virtusdevelops.advancedcrops.core.storage.AsyncExecutor
import eu.virtusdevelops.advancedcrops.core.storage.SQLStorage
import eu.virtusdevelops.advancedcrops.plugin.listeners.BlockBreakListener
import eu.virtusdevelops.advancedcrops.plugin.listeners.BlockInteractListener
import eu.virtusdevelops.advancedcrops.plugin.listeners.BlockPlaceListener
import eu.virtusdevelops.advancedcrops.plugin.listeners.ChunkListener
import eu.virtusdevelops.virtuscore.VirtusCore
import eu.virtusdevelops.virtuscore.compatibility.ServerVersion
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.Executors

class AdvancedCrops : JavaPlugin(), AdvancedCropsApi {

    private lateinit var storage: SQLStorage
    private lateinit var cropStorage: CropStorage
    private lateinit var hoeStorage: HoeStorage
    private lateinit var cropManager: CropManager

    override fun onDisable() {
        cropManager.saveCrops()
        logger.info("Successfully disabled AdvancedCrops!")
    }


    override fun onEnable() {
        saveDefaultConfig()

        AdvancedCropsApi.load(this)
        logger.info("VirtusCore version: ${VirtusCore.getCoreVersion()}")
        logger.info("Server version: ${ServerVersion.getServerVersion().name}")

        storage = SQLStorage(this, this.logger)

        cropStorage = CropStorageImpl(storage.cropDao, logger)
        hoeStorage = HoeStorageImpl()
        cropManager = CropManagerImpl(cropStorage)

        registerEvents()
    }


    private fun registerEvents(){
        val manager = VirtusCore.plugins()
        manager.registerEvents(BlockInteractListener(), this)
        manager.registerEvents(ChunkListener(cropManager, cropStorage, logger), this)
        manager.registerEvents(BlockPlaceListener(cropManager, logger), this)
        manager.registerEvents(BlockBreakListener(cropStorage, cropManager), this)
    }


    override fun getCropStorage(): CropStorage {
        return cropStorage
    }


    override fun getHoeStorage(): HoeStorage {
        return hoeStorage
    }

    override fun getCropManager(): CropManager {
        return cropManager
    }
}