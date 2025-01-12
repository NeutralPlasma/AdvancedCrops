package eu.virtusdevelops.advancedcrops.plugin.listeners

import eu.virtusdevelops.advancedcrops.api.CropManager
import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import org.bukkit.event.world.WorldSaveEvent
import java.util.logging.Logger

class ChunkListener(private val cropManager: CropManager,
                    private val cropStorage: CropStorage,
                    private val logger: Logger) : Listener {



    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun onChunkLoad(event: ChunkLoadEvent) {
        val chunk = event.chunk
        val x = chunk.x
        val z = chunk.z

        cropStorage.loadChunk(x, z, event.chunk.world.name)


    }



    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun onChunkUnload(event: ChunkUnloadEvent) {
        val chunk = event.chunk
        val x = chunk.x
        val z = chunk.z

        cropStorage.unloadChunk(x, z, event.chunk.world.name)
    }


}