package eu.virtusdevelops.advancedcrops.core.storage

import eu.virtusdevelops.advancedcrops.api.chunk.ChunkCoordinates
import eu.virtusdevelops.advancedcrops.api.chunk.ChunkPosition
import eu.virtusdevelops.advancedcrops.api.item.StorableItem


class ChunkData<T : StorableItem>(
    val chunkCoordinates : ChunkCoordinates,
    val items : HashMap<ChunkPosition, T> = HashMap()) {


    val removedItems : HashMap<ChunkPosition, T> = HashMap() // will be used by CRUD operations to know which ones to actually delete
    val addedItems : HashMap<ChunkPosition, T> = HashMap()

    fun addItem(item: T){
        if(removedItems[item.getPosition()] != null){
            removedItems.remove(item.getPosition())
        }
        addedItems[item.getPosition()] = item
        items[item.getPosition()] = item
    }

    fun removeItem(item: T): Boolean{
        var removed = false
        if(items[item.getPosition()] != null){
            if(items[item.getPosition()] == item){
                // remove
                removedItems[item.getPosition()] = item
                items.remove(item.getPosition())
                removed = true
            }
        }
        // check added items
        if(addedItems[item.getPosition()] != null){
            if(addedItems[item.getPosition()] == item){
                addedItems.remove(item.getPosition())
                removed = true
            }
        }


        return removed
    }

    fun getItem(chunkPosition: ChunkPosition): T?{
        return items[chunkPosition]
    }
}