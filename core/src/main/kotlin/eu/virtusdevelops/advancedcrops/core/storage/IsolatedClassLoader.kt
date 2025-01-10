package eu.virtusdevelops.advancedcrops.core.storage

import java.net.URL
import java.net.URLClassLoader

/**
 * A custom implementation of URLClassLoader that isolates class loading by using
 * the system class loader's parent as its parent class loader.
 *
 * This class is designed to provide an isolated class loading environment, which can be
 * useful when there is a need to avoid conflicts between classes loaded by different
 * class loaders, or to load classes in a more controlled manner.
 *
 * @param urls The array of URLs from which to load classes and resources.
 */
class IsolatedClassLoader(urls: Array<URL>) : URLClassLoader(urls, ClassLoader.getSystemClassLoader().parent) {
}