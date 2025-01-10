package eu.virtusdevelops.advancedcrops.api.hoe

import eu.virtusdevelops.advancedcrops.shared.hoe.HoeConfiguration

interface HoeStorage {

    fun getHoe(id: String): Hoe?

    fun getHoeConfiguration(id: String): HoeConfiguration?


}