package com.amnah.recog_txt.ui.fragment.camera

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.amnah.recog_txt.data.database.ItemDatabase
import com.amnah.recog_txt.data.model.Item
import com.amnah.recog_txt.data.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Item>>
    private  val repository: ItemRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).userDao()
        repository = ItemRepository(itemDao)
        readAllData = repository.readAllData
    }


    fun addItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }
}