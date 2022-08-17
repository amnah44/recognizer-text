package com.amnah.recog_txt.data.repository

import androidx.lifecycle.LiveData
import com.amnah.recog_txt.data.database.ItemDao
import com.amnah.recog_txt.data.model.Item

class ItemRepository(private val itemDao: ItemDao) {

    val readAllData: LiveData<List<Item>> = itemDao.readAllData()

    suspend fun addItem(item: Item){
        itemDao.addItem(item)
    }
}