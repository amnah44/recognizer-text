package com.amnah.recog_txt.ui.fragment

import com.amnah.recog_txt.data.model.InternalStoragePhoto

interface DetectTextActionListener {
    fun onDeleteClickListener(internalStoragePhoto: InternalStoragePhoto)
}