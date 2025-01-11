package eu.virtusdevelops.advancedcrops.core.storage.mysql

import com.zaxxer.hikari.HikariDataSource
import eu.virtusdevelops.advancedcrops.api.crop.Crop
import eu.virtusdevelops.advancedcrops.core.dao.CropDao
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.*
import java.util.logging.Logger

class CropDaoMysqlImpl(private val dataSource: HikariDataSource,
    private val logger: Logger) : CropDao {





    override fun init() {
        try {
            dataSource.getConnection().use { connection ->
                val statement: PreparedStatement = connection.prepareStatement(
                """
                    CREATE TABLE IF NOT EXISTS ac_crop (
                        id UUID PRIMARY KEY,
                        configuration_name VARCHAR(255),
                        bone_meal INTEGER NOT NULL DEFAULT 0,
                        growth_stage INTEGER NOT NULL DEFAULT 0,
                        humidity INTEGER NOT NULL DEFAULT 0,
                        fertility INTEGER NOT NULL DEFAULT 0,
                        
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

    override fun getCropsInChunk(chunkX: Int, chunkZ: Int): List<Crop> {
        TODO("Not yet implemented")
    }



    override fun save(entity: Crop): Boolean {
        TODO("Not yet implemented")
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