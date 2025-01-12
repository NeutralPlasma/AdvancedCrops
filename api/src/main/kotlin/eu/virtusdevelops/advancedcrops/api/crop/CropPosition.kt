package eu.virtusdevelops.advancedcrops.api.crop

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkCoordinates
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import org.bukkit.Bukkit
import org.bukkit.Location

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
    /**
     * Lazily initialized Minecraft world instance corresponding to the `worldName`.
     * This property references the server's World object determined by the `worldName` associated
     * with this instance of CropPosition.
     *
     * @throws IllegalArgumentException if the world identified by `worldName` does not exist.
     */
    val world by lazy { Bukkit.getWorld(worldName) }




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

        fun fromCoordinates(
            x: Int,
            y: Int,
            z: Int,
            world: String
        ): CropPosition{

            return CropPosition(
                ChunkPosition(x.rem(16), y, z.rem(16)),
                ChunkCoordinates(x.shr(4), z.shr(4)),
                world
            )
        }
    }
}