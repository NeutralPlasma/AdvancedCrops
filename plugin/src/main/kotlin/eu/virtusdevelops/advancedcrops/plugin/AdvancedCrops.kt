package eu.virtusdevelops.advancedcrops.plugin

import eu.virtusdevelops.advancedcrops.api.AdvancedCropsApi
import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.api.hoe.HoeStorage
import eu.virtusdevelops.advancedcrops.core.crop.CropManagerImpl
import eu.virtusdevelops.advancedcrops.core.crop.CropStorageImpl
import eu.virtusdevelops.advancedcrops.core.hoe.HoeStorageImpl
import eu.virtusdevelops.advancedcrops.core.storage.SQLStorage
import eu.virtusdevelops.advancedcrops.plugin.commands.CommandRegistry
import eu.virtusdevelops.advancedcrops.plugin.listeners.BlockBreakListener
import eu.virtusdevelops.advancedcrops.plugin.listeners.BlockInteractListener
import eu.virtusdevelops.advancedcrops.plugin.listeners.BlockPlaceListener
import eu.virtusdevelops.advancedcrops.plugin.listeners.ChunkListener
import eu.virtusdevelops.advancedcrops.plugin.util.NBTUtil
import eu.virtusdevelops.virtuscore.VirtusCore
import eu.virtusdevelops.virtuscore.compatibility.ServerVersion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.minecraft.extras.AudienceProvider
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler
import org.incendo.cloud.minecraft.extras.MinecraftHelp
import org.incendo.cloud.minecraft.extras.caption.ComponentCaptionFormatter
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper
import org.incendo.cloud.paper.util.sender.Source
import org.incendo.cloud.setting.ManagerSetting

class AdvancedCrops : JavaPlugin(), AdvancedCropsApi {

    private lateinit var storage: SQLStorage
    private lateinit var cropStorage: CropStorage
    private lateinit var hoeStorage: HoeStorage
    private lateinit var cropManager: CropManager

    private lateinit var minecraftHelp: MinecraftHelp<Source>

    override fun onDisable() {
        cropStorage.saveAll()
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

        NBTUtil.load(this)
        registerEvents()
        registerCommands()
    }


    private fun registerEvents(){
        val manager = VirtusCore.plugins()
        manager.registerEvents(BlockInteractListener(cropManager), this)
        manager.registerEvents(ChunkListener(cropManager, cropStorage, logger), this)
        manager.registerEvents(BlockPlaceListener(cropManager, logger), this)
        manager.registerEvents(BlockBreakListener(cropStorage, cropManager), this)
    }


    private fun registerCommands(){

        val manager : PaperCommandManager<Source> = PaperCommandManager
            .builder(PaperSimpleSenderMapper.simpleSenderMapper())
            .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
            .buildOnEnable(this)

        manager.settings().set(ManagerSetting.ALLOW_UNSAFE_REGISTRATION, true)

        manager.exceptionController().clearHandlers()


        val audienceProvider = AudienceProvider<Source> { source: Source -> source.source()  }

        registerHelpCommand(audienceProvider, manager)


        MinecraftExceptionHandler.create(audienceProvider)
            .defaultHandlers()
            .decorator { component: Component? ->
                Component.text()
                    .append(Component.text("[", NamedTextColor.DARK_GRAY))
                    .append(Component.text("Crops", NamedTextColor.GOLD))
                    .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                    .append(component!!)
                    .build()
            }
            .registerTo(manager)

         manager.captionRegistry().registerProvider(MinecraftHelp.defaultCaptionsProvider())

        CommandRegistry(this, manager)
    }

    private fun registerHelpCommand(audienceProvider: AudienceProvider<Source>, commandManager: PaperCommandManager<Source>){
        minecraftHelp = MinecraftHelp.builder<Source>()
            .commandManager(commandManager)
            .audienceProvider(audienceProvider)
            .commandPrefix("/crops help")
            .messageProvider(MinecraftHelp.captionMessageProvider(
                commandManager.captionRegistry(),
                ComponentCaptionFormatter.miniMessage()
            ))
            .build()
    }

    fun getMinecraftHelp(): MinecraftHelp<Source> {
        return minecraftHelp
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