package eu.virtusdevelops.advancedcrops.core.storage

import java.util.concurrent.Executors

class AsyncExecutor {

    companion object {
        val executor = Executors.newFixedThreadPool(2)
    }

}