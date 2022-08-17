package com.amnah.recog_txt.ui.fragment.gallery.adapter

import com.amnah.recog_txt.data.model.Item

interface GalleryActionListener {
    fun onDeleteClickListener(item: Item)
}