package eu.virtusdevelops.advancedcrops.core.dao

interface DaoCrud<T, TK> {

    fun init()

    fun save(entity: T): Boolean

    fun load(uuid: TK): T?

    fun delete(uuid: TK): Boolean

    fun getAll(): List<T>

}