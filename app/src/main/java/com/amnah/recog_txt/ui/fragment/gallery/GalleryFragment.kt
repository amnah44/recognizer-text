package com.amnah.recog_txt.ui.fragment.gallery

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amnah.recog_txt.data.model.InternalStoragePhoto
import com.amnah.recog_txt.data.model.Item
import com.amnah.recog_txt.databinding.FragmentGalleryBinding
import com.amnah.recog_txt.ui.fragment.base.BaseFragment
import com.amnah.recog_txt.ui.fragment.camera.CameraViewModel
import com.amnah.recog_txt.ui.fragment.gallery.adapter.GalleryActionListener
import com.amnah.recog_txt.ui.fragment.gallery.adapter.GalleryAdapter
import com.amnah.recog_txt.util.Constants.TAG_GALLERY_FRAGMENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : BaseFragment<FragmentGalleryBinding>(), GalleryActionListener {

    override val LOG_TAG: String
        get() = TAG_GALLERY_FRAGMENT
    override val bindingInflater: (LayoutInflater) -> FragmentGalleryBinding =
        FragmentGalleryBinding::inflate

    private lateinit var _binding: FragmentGalleryBinding
    private lateinit var _viewModel: CameraViewModel
    private lateinit var galleryAdapter: GalleryAdapter
    var photos = listOf<InternalStoragePhoto>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        _viewModel = ViewModelProvider(this)[CameraViewModel::class.java]

        galleryAdapter = GalleryAdapter(emptyList<Item>(),this)
        _binding.loadPhotoRecyclerView.apply {
            adapter = galleryAdapter
        }

        _viewModel.readAllData.observe(viewLifecycleOwner) { items ->
            Log.i("llllllllllllllllllll", items.toString())
            galleryAdapter.setData(items)

        }


//        loadPhotosFromInternalStorageIntoRecyclerView()

    }


    override fun onDeleteClickListener(item: Item) {
//        deletePhotoFromInternalStorage(item.id.toString())
    }
//
//    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
//        lifecycleScope.launch {
//            photos = loadPhotoFromInternalStorage()
//        }
//    }
//
//    private fun deletePhotoFromInternalStorage(filename: String): Boolean {
//        return try {
//            requireActivity().deleteFile(filename)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            false
//        }
//    }
//
//    private suspend fun loadPhotoFromInternalStorage(): List<InternalStoragePhoto> {
//        return withContext(Dispatchers.IO) {
//            val file = requireActivity().filesDir.listFiles()
//            file?.filter {
//                it.canRead() && it.isFile && it.name.endsWith(".jpg")
//            }?.map {
//                val bytes = it.readBytes()
//                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                InternalStoragePhoto(it.name, bmp)
//            } ?: listOf()
//        }
//    }

}