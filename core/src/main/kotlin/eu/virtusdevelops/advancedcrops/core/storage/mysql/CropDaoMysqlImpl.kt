package eu.virtusdevelops.advancedcrops.core.storage.mysql

import com.zaxxer.hikari.HikariDataSource
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.api.crop.CropPosition
import eu.virtusdevelops.advancedcrops.core.dao.CropDao
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*
import java.util.logging.Logger

class CropDaoMysqlImpl(private val dataSource: HikariDataSource,
    private val logger: Logger) : CropDao {





    override fun init() {
        try {
            dataSource.connection.use { connection ->
                val statement: PreparedStatement = connection.prepareStatement(
                """
                    CREATE TABLE IF NOT EXISTS ac_crop (
                        id UUID PRIMARY KEY,
                        configuration_name VARCHAR(255),
                        bone_meal INTEGER NOT NULL DEFAULT 0,
                        growth_stage INTEGER NOT NULL DEFAULT 0,
                        humidity INTEGER NOT NULL DEFAULT 0,
                        fertilizer INTEGER NOT NULL DEFAULT 0,
                        
                        chunk_x INTEGER NOT NULL DEFAULT 0,
                        chunk_z INTEGER NOT NULL DEFAULT 0,
                        
                        position_x INTEGER NOT NULL DEFAULT 0,
                        position_y INTEGER NOT NULL DEFAULT 0,
                        position_z INTEGER NOT NULL DEFAULT 0,
                        
                        world VARCHAR(255) NOT NULL,
                        
                        placedby UUID,
                        placed TIMESTAMP,
                        updated TIMESTAMP
                        
                    );
                """.trimIndent()
                )
                statement.execute()
            }
        } catch (e: SQLException) {
            logger.severe("Exception occured while initializing Crops table")
            e.printStackTrace()
        }
    }


    override fun getCropsByPlayer(playerUUID: UUID): List<Crop> {
        TODO("Not yet implemented")
    }

    override fun getCropsInChunk(chunkX: Int, chunkZ: Int, world: String): List<Crop> {
        val crops = mutableListOf<Crop>()
        try {
            dataSource.connection.use { connection ->
                val statement: PreparedStatement = connection.prepareStatement(
                    """
                    SELECT * 
                    FROM ac_crop 
                    WHERE chunk_x = ? 
                    AND chunk_z = ?
                    AND world = ?;
                """.trimIndent()
                )
                statement.setInt(1, chunkX)
                statement.setInt(2, chunkZ)
                statement.setString(3, world)


                val resultSet = statement.executeQuery()
                while(resultSet.next()){
                    val location = CropPosition.fromCoordinates(
                        resultSet.getInt("position_x"),
                        resultSet.getInt("position_y"),
                        resultSet.getInt("position_z"),
                        resultSet.getString("world")
                    )


                    val crop = Crop(
                        resultSet.getObject("id", UUID::class.java),
                        resultSet.getString("configuration_name"),
                        location,
                        resultSet.getInt("bone_meal"),
                        resultSet.getInt("growth_stage"),
                        resultSet.getInt("humidity"),
                        resultSet.getInt("fertilizer"),
                        resultSet.getObject("placedby", UUID::class.java),
                        resultSet.getTimestamp("placed").time,
                        resultSet.getTimestamp("updated").time
                    )

                    crops.add(crop)
                }
            }
        } catch (e: SQLException) {
            logger.severe("Exception occured while fetching crops in chunk $chunkX, $chunkZ")
            e.printStackTrace()
        }
        return crops
    }



    override fun save(entity: Crop): Boolean {
        try{
            dataSource.connection.use { connection ->


                val preparedStatementGet = connection.prepareStatement("""
                    SELECT * FROM ac_crop WHERE id = ?;
                """.trimIndent())
                preparedStatementGet.setObject(1, entity.id)

                val resultSet = preparedStatementGet.executeQuery()
                if(!resultSet.next()){
                    // do update
                    val preparedStatement = connection.prepareStatement("""
                        INSERT INTO ac_crop VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """.trimIndent())

                    preparedStatement.setObject(1, entity.id)
                    preparedStatement.setString(2, entity.configurationName)
                    preparedStatement.setInt(3, entity.boneMeal)
                    preparedStatement.setInt(4, entity.growthStage)
                    preparedStatement.setInt(5, entity.humidity)
                    preparedStatement.setInt(6, entity.fertilizer)
                    preparedStatement.setInt(7, entity.location.chunkCoordinates.x)
                    preparedStatement.setInt(8, entity.location.chunkCoordinates.z)
                    preparedStatement.setInt(9, entity.location.chunkPos.x)
                    preparedStatement.setInt(10, entity.location.chunkPos.y)
                    preparedStatement.setInt(11, entity.location.chunkPos.z)
                    preparedStatement.setString(12, entity.location.worldName)
                    preparedStatement.setObject(13, entity.placedBy)
                    preparedStatement.setTimestamp(14, Timestamp(entity.placeTime))
                    preparedStatement.setTimestamp(15, Timestamp(entity.updateTime))

                    preparedStatement.execute()

                    return preparedStatement.updateCount != 0

                }else{
                    // save new
                    val preparedStatement = connection.prepareStatement("""
                        UPDATE ac_crop
                        SET bone_meal = ?,
                        growth_stage = ?,
                        humidity = ?,
                        fertilizer = ?,
                        updated = ?
                        WHERE id = ?;
                    """.trimIndent())

                    preparedStatement.setObject(6, entity.id)
                    preparedStatement.setInt(1, entity.boneMeal)
                    preparedStatement.setInt(2, entity.growthStage)
                    preparedStatement.setInt(3, entity.humidity)
                    preparedStatement.setInt(4, entity.fertilizer)
                    preparedStatement.setTimestamp(5, Timestamp(entity.updateTime))

                    preparedStatement.execute()

                    return preparedStatement.updateCount != 0
                }
            }
        } catch (e: SQLException) {
            logger.severe("Failed saving crop")
            e.printStackTrace()
        }
        return false
    }

    override fun load(uuid: UUID): Crop? {
        TODO("Not yet implemented")
    }

    override fun delete(uuid: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Crop> {
        TODO("Not yet implemented")
    }
}