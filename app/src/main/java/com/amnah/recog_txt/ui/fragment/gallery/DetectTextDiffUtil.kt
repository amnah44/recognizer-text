package com.amnah.recog_txt.ui.fragment.gallery

import androidx.recyclerview.widget.DiffUtil
import com.amnah.recog_txt.data.model.InternalStoragePhoto

class DetectTextDiffUtil(
    val oldList: List<InternalStoragePhoto>,
    val newList: List<InternalStoragePhoto>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].bmp == newList[newItemPosition].bmp)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true


}