package eu.virtusdevelops.advancedcrops.api

import eu.virtusdevelops.advancedcrops.api.crop.CropStorage
import eu.virtusdevelops.advancedcrops.api.hoe.HoeStorage
import org.jetbrains.annotations.ApiStatus

interface AdvancedCropsApi {

    companion object {

        private var implementation: AdvancedCropsApi? = null

        private var enabled = false

        /**
         * Retrieves the version of the API.
         *
         * @return The version as an integer.
         */
        @JvmStatic
        fun getVersion(): Int = 1

        /**
         * Provides an instance of the AdvancedCropsApi if the API is enabled.
         *
         * @return The instance of AdvancedCropsApi.
         * @throws NullPointerException if the API is not enabled.
         * @since 1
         */
        @JvmStatic
        fun get(): AdvancedCropsApi {
            if(enabled) return implementation!!
            throw NullPointerException("AdvancedCropsApi is not enabled!")
        }

        /**
         * Checks whether the AdvancedCropsApi is enabled.
         *
         * @return true if the API is enabled, false otherwise.
         * @since 1
         */
        @JvmStatic
        fun isEnabled(): Boolean = enabled


        /**
         * This is an internal method. Do not use it.
         *
         * Loads the specified AdvancedCropsApi instance to enable the API.
         *
         * @param advancedCropsApi The instance of AdvancedCropsApi to be loaded.
         * @throws IllegalStateException If the API is already enabled.
         */
        @ApiStatus.Internal
        fun load(advancedCropsApi: AdvancedCropsApi) {
            if(enabled) throw IllegalStateException("AdvancedCropsApi is already enabled!")
            implementation = advancedCropsApi
            enabled = true
        }

    }


    /**
     * Retrieves the CropStorage instance associated with the API.
     *
     * @return An instance of CropStorage.
     */
    fun getCropStorage(): CropStorage

    /**
     * Retrieves the HoeStorage instance associated with the API.
     *
     * @return An instance of HoeStorage.
     */
    fun getHoeStorage(): HoeStorage

    /**
     * Retrieves the CropManager instance associated with the API.
     *
     * @return An instance of CropManager, which provides functionality for managing crops within the system.
     */
    fun getCropManager(): CropManager
}