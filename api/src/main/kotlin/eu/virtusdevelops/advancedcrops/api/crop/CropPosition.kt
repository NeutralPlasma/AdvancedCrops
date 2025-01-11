package eu.virtusdevelops.advancedcrops.api.crop

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkCoordinates
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

data class CropPosition(
    /**
     * Position within chunk
     */
    var chunkPos: ChunkPosition,
    /**
     * Position of the chunk
     */
    var chunkCoordinates: ChunkCoordinates,

    /**
     * Name of the world
     */
    val worldName: String
){
    private val world by lazy { Bukkit.getWorld(worldName) }


    /**
     * Retrieves the world associated with the stored worldName in this CropPosition.
     *
     * @return The World object corresponding to the worldName, or null if the world does not exist.
     */
    fun getWorld(): World? {
        return world
    }

    /**
     * Converts the stored chunk and position data into a concrete Minecraft world location.
     *
     * @return A Location object representing the position in the world defined by the associated worldName,
     * chunkCoordinates, and chunkPos.
     * @throws IllegalArgumentException if the world defined by worldName does not exist.
     */
    fun getLocation(): Location{
        val world = Bukkit.getWorld(worldName)
            ?: throw IllegalArgumentException("World $worldName does not exist!")

        val x = chunkCoordinates.x.shl(4) + chunkPos.x
        val z = chunkCoordinates.z.shl(4) + chunkPos.z

        return Location(world, x.toDouble(), chunkPos.z.toDouble(),  z.toDouble())
    }

    /**
     * The companion object for the CropPosition class.
     *
     * Provides utility methods related to the creation of CropPosition instances.
     */
    companion object {
        fun fromLocation(location: Location): CropPosition{
            val worldName = location.world.name
            val chunkCoordinates = ChunkCoordinates(location.blockX.shr(4), location.blockZ.shr(4))
            val chunkPos = ChunkPosition(location.blockX.rem(16), location.blockY, location.blockZ.rem(16))

            return CropPosition(chunkPos, chunkCoordinates, worldName)
        }
    }
}