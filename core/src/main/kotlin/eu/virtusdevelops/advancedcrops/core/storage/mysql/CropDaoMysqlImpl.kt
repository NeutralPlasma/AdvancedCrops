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


    /**
     * crop:
     * id
     * placed_by
     * configuration_name
     * location
     * bone meal status
     *
     */


    override fun init() {
        try {
            dataSource.getConnection().use { connection ->
                val statement: PreparedStatement = connection.prepareStatement(
                """
                    CREATE TABLE IF NOT EXISTS ac_crop (
                        id UUID PRIMARY KEY,
                        placed_by UUID,
                        configuration_name UUID,
                        placed TIMESTAMP,
                        created_on TIMESTAMP
                    );
                """.trimIndent()
                )
                statement.execute()
            }
        } catch (e: SQLException) {
            logger.severe("Exception occured while initializing CInvite MYSQL Dao")
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