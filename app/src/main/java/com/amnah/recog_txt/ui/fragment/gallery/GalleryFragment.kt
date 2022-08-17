package com.amnah.recog_txt.ui.fragment.gallery

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amnah.recog_txt.data.model.InternalStoragePhoto
import com.amnah.recog_txt.databinding.FragmentGalleryBinding
import com.amnah.recog_txt.ui.fragment.DetectTextActionListener
import com.amnah.recog_txt.ui.fragment.base.BaseFragment
import com.amnah.recog_txt.util.Constants.TAG_GALLERY_FRAGMENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : BaseFragment<FragmentGalleryBinding>(), DetectTextActionListener {

    override val LOG_TAG: String
        get() = TAG_GALLERY_FRAGMENT
    override val bindingInflater: (LayoutInflater) -> FragmentGalleryBinding =
        FragmentGalleryBinding::inflate

    private lateinit var _binding: FragmentGalleryBinding
    private lateinit var internalStoragePhotoAdapter: InternalStoragePhotoAdapter
    var photos = listOf<InternalStoragePhoto>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)

        internalStoragePhotoAdapter = InternalStoragePhotoAdapter(photos, this)

        getRecyclerData()

        loadPhotosFromInternalStorageIntoRecyclerView()

    }

    private fun getRecyclerData() {
        _binding.loadPhotoRecyclerView.apply {
            adapter = internalStoragePhotoAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = false
            }
        }
    }

    override fun onDeleteClickListener(internalStoragePhoto: InternalStoragePhoto) {
        deletePhotoFromInternalStorage(internalStoragePhoto.name)
    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            photos = loadPhotoFromInternalStorage()
            internalStoragePhotoAdapter.setData(photos.toList())
        }
    }

    private fun deletePhotoFromInternalStorage(filename: String): Boolean {
        return try {
            requireActivity().deleteFile(filename)

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private suspend fun loadPhotoFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val file = requireActivity().filesDir.listFiles()
            file?.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }

}