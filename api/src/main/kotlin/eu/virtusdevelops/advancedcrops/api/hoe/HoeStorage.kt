package eu.virtusdevelops.advancedcrops.api.hoe


interface HoeStorage {

    fun getHoe(id: String): Hoe?

    fun getHoeConfiguration(id: String): HoeConfiguration?


}