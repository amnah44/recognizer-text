package com.amnah.recog_txt.ui.fragment.gallery.adapter

import androidx.recyclerview.widget.DiffUtil
import com.amnah.recog_txt.data.model.InternalStoragePhoto
import com.amnah.recog_txt.data.model.Item

class GalleryDiffUtil(
    private val oldList: List<Item>,
    private val newList: List<Item>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true


}