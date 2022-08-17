package com.amnah.recog_txt.ui.fragment.gallery.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amnah.recog_txt.data.model.InternalStoragePhoto
import com.amnah.recog_txt.data.model.Item
import com.amnah.recog_txt.databinding.ItemPhotoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GalleryAdapter(
    private var listItem: List<Item>,
    val listener: GalleryActionListener,
) : RecyclerView.Adapter<GalleryAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view: ItemPhotoBinding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentPosition = listItem[position]

        holder.binding.apply {
//            photo.setImageBitmap(currentPosition.uid)
            textRecognizer.text = currentPosition.text

            deleteBtn.setOnClickListener {
                listener.onDeleteClickListener(currentPosition)
            }
        }


    }

    override fun getItemCount() = listItem.size

    fun setData(newList: List<Item>) {
        this.listItem = newList
        notifyDataSetChanged()
//        val resultDiffUtil = DiffUtil.calculateDiff(GalleryDiffUtil(listItem, newList))
//        listItem = newList
//        resultDiffUtil.dispatchUpdatesTo(this)
    }
//        holder.binding.apply {
//            photo.setImageBitmap(currentPosition.bmp)
//            aspectRatio(currentPosition, root, photo)
//
//            deleteBtn.setOnClickListener {
//                listener.onDeleteClickListener(currentPosition)
//            }
//        }
//        detectText.detectTextFromImage(currentPosition.bmp, holder.binding)


//    fun setData(newList: List<InternalStoragePhoto>) {
//        val diffResult = DiffUtil.calculateDiff(GalleryDiffUtil(listItem, newList))
//        listItem = newList
//        diffResult.dispatchUpdatesTo(this)
//    }
//
//    private fun aspectRatio(
//        currentPosition: InternalStoragePhoto,
//        root: ConstraintLayout,
//        photo: ImageView
//    ) = ConstraintSet().apply {
//        val aspectRatio = currentPosition.bmp.width.toFloat() / currentPosition.bmp.height.toFloat()
//
//        clone(root)
//        setDimensionRatio(photo.id, aspectRatio.toString())
//        applyTo(root)
//    }

}