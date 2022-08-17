package com.amnah.recog_txt.ui.fragment.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amnah.recog_txt.data.DetectText
import com.amnah.recog_txt.data.model.InternalStoragePhoto
import com.amnah.recog_txt.databinding.ItemPhotoBinding
import com.amnah.recog_txt.ui.fragment.DetectTextActionListener

class InternalStoragePhotoAdapter(
    private var list: List<InternalStoragePhoto>,
    val listener: DetectTextActionListener,
) : RecyclerView.Adapter<InternalStoragePhotoAdapter.PhotoViewHolder>() {
    val detectText = DetectText()

    inner class PhotoViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view: ItemPhotoBinding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentPosition = list[position]

        holder.binding.apply {
            photo.setImageBitmap(currentPosition.bmp)
            aspectRatio(currentPosition, root, photo)

            deleteBtn.setOnClickListener {
                listener.onDeleteClickListener(currentPosition)
            }
        }
        detectText.detectTextFromImage(currentPosition.bmp, holder.binding)
    }

    override fun getItemCount() = list.size

    fun setData(newList: List<InternalStoragePhoto>) {
        val diffResult = DiffUtil.calculateDiff(DetectTextDiffUtil(list, newList))
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private fun aspectRatio(
        currentPosition: InternalStoragePhoto,
        root: ConstraintLayout,
        photo: ImageView
    ) = ConstraintSet().apply {
        val aspectRatio = currentPosition.bmp.width.toFloat() / currentPosition.bmp.height.toFloat()

        clone(root)
        setDimensionRatio(photo.id, aspectRatio.toString())
        applyTo(root)
    }

}