package eu.virtusdevelops.advancedcrops.core.storage

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import eu.virtusdevelops.advancedcrops.core.dao.CropDao
import eu.virtusdevelops.advancedcrops.core.storage.mysql.CropDaoMysqlImpl
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.net.URI
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Logger

class SQLStorage(private val plugin: JavaPlugin, private val logger: Logger) {

    lateinit var dataSource: HikariDataSource
    var isMysql: Boolean


    lateinit var cropDao: CropDao

    init {
        val section = plugin.config.getConfigurationSection("db")

        if(section == null) {
            logger.warning("No database section found in config. Using local storage.")
            // disable plugin
        }
        isMysql = plugin.config.getBoolean("db.mysql")

        setupDataSource()
        setupDAOs()
        initDAOs()
    }


    private fun setupDAOs(){
        cropDao = CropDaoMysqlImpl(dataSource, logger)
    }


    private fun initDAOs(){
        cropDao.init()
    }




    private fun setupDataSource() {
        val config = if (isMysql) {
            setupMysqlDataSource()
        } else {
            setupH2DataSource()
        }
        config!!.poolName = "AdvancedCrop"

        this.dataSource = HikariDataSource(config)
    }


    private fun setupMysqlDataSource(): HikariConfig {
        val username = plugin.config.getString("username")!!
        val password = plugin.config.getString("password")!!
        val hostname = plugin.config.getString("ip")!!
        val port = plugin.config.getInt("port")
        val database = plugin.config.getString("database")!!
        val SSL = plugin.config.getBoolean("ssl")
        val path = plugin.dataFolder.path

        val hikariConfig = HikariConfig()
        hikariConfig.dataSourceClassName = "com.mysql.cj.jdbc.MysqlDataSource"
        hikariConfig.addDataSourceProperty("user", username)
        hikariConfig.addDataSourceProperty("password", password)
        hikariConfig.addDataSourceProperty("useSSL", SSL)
        hikariConfig.addDataSourceProperty("databaseName", database)
        hikariConfig.addDataSourceProperty("serverName", hostname)
        hikariConfig.addDataSourceProperty("port", port)

        hikariConfig.maxLifetime = 60000
        hikariConfig.maximumPoolSize = 10

        return hikariConfig
    }


    private fun setupH2DataSource(): HikariConfig? {
        val classLoader = loadH2Library()
        try {
            Thread.currentThread().contextClassLoader = classLoader
            val h2Driver = classLoader!!.loadClass("org.h2.Driver")
                ?: throw ClassNotFoundException("org.h2.Driver")
            val hikariConfig = HikariConfig()
            hikariConfig.maxLifetime = 60000
            hikariConfig.maximumPoolSize = 15
            hikariConfig.driverClassName = h2Driver.name

            val path = plugin.dataFolder.absolutePath
            createDBFile()

            hikariConfig.jdbcUrl = "jdbc:h2:$path/db.h2"
            return hikariConfig
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            return null
        }
    }

    private fun createDBFile(){
        val path = plugin.dataFolder.toPath()
        val dbPath = path.resolve("db.h2")
        if(!Files.exists(dbPath)){
            try {
                Files.createFile(dbPath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }




    private fun loadH2Library(): IsolatedClassLoader? {
        val jarUrl = URI.create("https://repo1.maven.org/maven2/com/h2database/h2/2.3.232/h2-2.3.232.jar")


        // Get plugin folder and ensure the 'libs' subfolder exists
        val pluginFolder = plugin.dataFolder.toPath()
        val libsFolder = pluginFolder.resolve("libs")
        if (!Files.exists(libsFolder)) {
            try {
                Files.createDirectories(libsFolder)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        // Check if the JAR file exists
        val jarPath = libsFolder.resolve("h2-2.3.232.jar")
        if (!Files.exists(jarPath)) {
            // If not, download it
            try {
                jarUrl.toURL().openStream().use { `in` ->
                    Files.copy(`in`, jarPath)
                    logger.info("Downloaded H2 Library")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        } else {
            logger.info("H2 library already exists")
        }

        // Load the jar into the classloader dynamically
        return loadJarFile(jarPath.toString())
    }

    private fun loadJarFile(jarFilePath: String): IsolatedClassLoader? {
        try {
            val path = Paths.get(jarFilePath)
            val jarUrl = path.toUri().toURL()
            val classLoader = IsolatedClassLoader(
                arrayOf(jarUrl)
            )
            return classLoader
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}